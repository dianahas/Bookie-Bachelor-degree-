package com.ubb.licenta.roomdetails;

import com.ubb.licenta.amadeusintegration.info.room.HotelRoom;
import com.ubb.licenta.amadeusintegration.info.room.RoomInfo;
import com.ubb.licenta.commons.BedTypeEnum;
import com.ubb.licenta.roomdetails.room.RoomDetailsInfo;
import com.ubb.licenta.roomdetails.room.RoomTypeInfo;
import com.ubb.licenta.utils.Utils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RoomDetailsHelper {

    private static final Logger log = LoggerFactory.getLogger( RoomDetailsHelper.class );

    private final static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final static MapperFacade mapper = mapperFactory.getMapperFacade();

    static {
        mapperFactory.classMap( RoomInfo.class, RoomTypeInfo.class )
                .byDefault()
                .register();

    }

    public RoomDetailsInfo processRoom( HotelRoom hotelRoom ) {
        RoomDetailsInfo roomDetailsInfo = new RoomDetailsInfo();

        roomDetailsInfo.setBookingCode( hotelRoom.getBookingCode() );
        roomDetailsInfo.setRoomTypeCode( hotelRoom.getRoomTypeCode() );
        roomDetailsInfo.setRateTypeCode( hotelRoom.getRateTypeCode() );
        roomDetailsInfo.setTotalPrice( hotelRoom.getTotalAmount() );
        roomDetailsInfo.setDescriptions( hotelRoom.getDescriptions() );

        processRoomType( hotelRoom, roomDetailsInfo );

        if ( roomDetailsInfo.getRoomTypeInfo().getPersons() == null )
            processRoomTypeCode( roomDetailsInfo.getRoomTypeCode(), roomDetailsInfo );

        processRoomDimension( roomDetailsInfo );
        return roomDetailsInfo;
    }

    private void processRoomDimension( RoomDetailsInfo roomDetailsInfo ) {
        String full = "";
        for ( String s : roomDetailsInfo.getDescriptions() ) {
            full = full.concat( s ).concat( " " );
        }
        full = full.substring( 0, full.length() - 1 );
        full = full.toLowerCase();

        String[] words = full.split( "[\\s\\-\\.\\'\\?\\,\\_\\@]+" );
        Integer numberOfWords = words.length;

        for ( int position = 0; position < numberOfWords; position++ ) {
            //completez dimensiunea camerei, daca exista
            if ( position + 1 < numberOfWords &&
                    ( ( words[position].equals( "sq" ) && words[position + 1].equals( "ft" ) ) || words[position].equals( "sqm" ) ) ) {

                if ( Utils.isNumeric( words[position - 1] ) ) {
                    if ( position - 1 > 0 ) {
                        BigDecimal roomDim = new BigDecimal( String.valueOf( words[position - 1] ) );

                        if ( words[position].equals( "sqm" ) ) {
                            roomDim = roomDim.multiply( new BigDecimal( 10.7639 ) ); // from sql to sq ft
                        }

                        roomDetailsInfo.setRoomDimension( roomDim );
                    }
                }
            }
        }
    }

    private void processRoomType( HotelRoom hotelRoom, RoomDetailsInfo roomDetailsInfo ) {
        RoomInfo roomInfo = hotelRoom.getRoomTypeInfo();
        RoomTypeInfo roomTypeInfo = mapper.map( roomInfo, RoomTypeInfo.class );

        if ( roomInfo.getBedType() != null && Utils.isNumeric( roomInfo.getNumberOfBeds() ) ) {
            Integer nrOfPersons = BedTypeEnum.getPersonsFromIdentifierOrType( null, roomInfo.getBedType().toLowerCase() );
            if ( nrOfPersons != null ) {
                roomDetailsInfo.setNrOfGuests( nrOfPersons * Integer.valueOf( roomInfo.getNumberOfBeds() ) );
                roomTypeInfo.setPersons( nrOfPersons * Integer.valueOf( roomInfo.getNumberOfBeds() ) );
            }
        }

        roomDetailsInfo.setRoomTypeInfo( roomTypeInfo );
    }

    private void processRoomTypeCode( String roomTypeCode, RoomDetailsInfo roomDetailsInfo ) {
        //verificare ca este format din 3 litere iubiciim pi lopster :D
        if ( roomTypeCode.length() == 3 ) {
            Character pos1 = roomTypeCode.charAt( 1 );
            Character pos2 = roomTypeCode.charAt( 2 );

            BedTypeEnum bedTypeEnum = BedTypeEnum.getFromIdentifierOrType( pos2, null );
            if ( bedTypeEnum != null && Utils.isNumeric( String.valueOf( pos1 ) ) ) {
                Integer nrOfPersons = bedTypeEnum.getPersons();

                Integer nrOfBeds = Integer.parseInt( String.valueOf( pos1 ) );
                roomDetailsInfo.getRoomTypeInfo().setPersons( nrOfPersons * nrOfBeds );
                roomDetailsInfo.setNrOfGuests( nrOfPersons * nrOfBeds );

            } else {
                processRoomDescriptions( roomDetailsInfo );
                log.info( "cannot extract integer from " + pos1 + " room code is: " + roomTypeCode + " descriptions are: " + roomDetailsInfo.getDescriptions() );
            }
        }
    }

    private void processRoomDescriptions( RoomDetailsInfo roomDetailsInfo ) {
        String full = "";
        Integer nrOfBeds;
        for ( String s : roomDetailsInfo.getDescriptions() ) {
            full = full.concat( s ).concat( " " );
        }
        full = full.substring( 0, full.length() - 1 );
        full = full.toLowerCase();

        String[] words = full.split( "[\\s\\-\\.\\'\\?\\,\\_\\@]+" );
        Integer numberOfWords = words.length;

        for ( int position = 0; position < numberOfWords; position++ ) {
            try {
                if ( words[position].contains( "bed" ) ) {

                    if ( position - 2 > 0 ) {

                        String nrBedsString = words[position - 2];

                        if ( Utils.isNumeric( nrBedsString ) || intReprOfString( nrBedsString ) != -1 ) {

                            BedTypeEnum bedTypeEnum = BedTypeEnum.getFromIdentifierOrType( null, words[position - 1] );

                            if ( bedTypeEnum != null ) {
                                Integer nrOfPersons = bedTypeEnum.getPersons();

                                if ( Utils.isNumeric( nrBedsString ) ) {
                                    nrOfBeds = Integer.parseInt( String.valueOf( words[position - 2] ) );
                                } else nrOfBeds = intReprOfString( nrBedsString );

                                if ( roomDetailsInfo.getRoomTypeInfo() != null ) {
                                    roomDetailsInfo.getRoomTypeInfo().setPersons( nrOfPersons * nrOfBeds );
                                    roomDetailsInfo.setNrOfGuests( nrOfPersons * nrOfBeds );

                                    if ( roomDetailsInfo.getRoomTypeInfo().getNumberOfBeds() == null ) {
                                        roomDetailsInfo.getRoomTypeInfo().setNumberOfBeds( String.valueOf( nrOfBeds ) );
                                    }

                                    if ( roomDetailsInfo.getRoomTypeInfo().getBedType() == null ) {
                                        roomDetailsInfo.getRoomTypeInfo().setBedType( bedTypeEnum.getBedType() );
                                    }
                                }
                            }

                            break;
                        }
                    }
                }
            } catch ( Exception e ) {
                log.info( e.getMessage() );
            }
        }
    }

    private static Integer intReprOfString( String value ) {
        switch ( value ) {
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            default:
                return -1;
        }
    }
}
