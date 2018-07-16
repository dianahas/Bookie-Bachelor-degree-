package com.ubb.licenta.hotelsearch;

import com.ubb.licenta.amadeusintegration.info.hotel.HotelPropertyResponse;
import com.ubb.licenta.amadeusintegration.info.hotel.HotelSearchResponse;
import com.ubb.licenta.amadeusintegration.info.room.HotelRoom;
import com.ubb.licenta.commons.AmadeusCallsHelper;
import com.ubb.licenta.commons.Amount;
import com.ubb.licenta.hotel.HotelPropertyInfo;
import com.ubb.licenta.hotel.hotelpicture.HotelPictureEntity;
import com.ubb.licenta.hotel.hotelpicture.HotelPictureRepository;
import com.ubb.licenta.rating.ReviewsRatingInfo;
import com.ubb.licenta.rating.service.ReviewsRatingService;
import com.ubb.licenta.reservation.entity.ReservationEntity;
import com.ubb.licenta.reservation.service.ReservationService;
import com.ubb.licenta.roomdetails.RoomDetailsHelper;
import com.ubb.licenta.roomdetails.room.RoomDetailsInfo;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.ubb.licenta.constants.ApplicationConstants.*;

@Getter
@Component
public class HotelSearchRepository {
    private static final Logger log = LoggerFactory.getLogger( HotelSearchRepository.class );

    @Autowired
    private RoomDetailsHelper roomDetailsHelper;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReviewsRatingService reviewsRatingService;

    @Autowired
    private HotelPictureRepository hotelPictureRepository;

    @Autowired
    private AmadeusCallsHelper amadeusCallsHelper;

    @Cacheable("hotelSearch")
    public LinkedHashMap<String, HotelPropertyInfo> getAllHotelsByCircle( HotelSearchRequestCriteria hotelSearchRequestCriteria ) {
        HotelSearchResponse hotelSearchResponse = amadeusCallsHelper.getHotelGeosearchByCircleAllRooms( hotelSearchRequestCriteria );

        int picturePosition = 0;

        //determine and keep only the hotels which fit the given search criteria
        LinkedHashMap<String, HotelPropertyInfo> suitableHotelsPerLocation = new LinkedHashMap<>();

        if ( hotelSearchResponse != null && !CollectionUtils.isEmpty( hotelSearchResponse.getResults() ) ) {
            for ( HotelPropertyResponse hotelPropertyResponse : hotelSearchResponse.getResults() ) {
                HotelPropertyInfo processedHotel = processHotelProperty( hotelPropertyResponse,
                        hotelSearchRequestCriteria.getInDate(), hotelSearchRequestCriteria.getOutDate(),
                        hotelSearchRequestCriteria.getPersons(), getPictureUrl( picturePosition ) );

                if ( processedHotel.getTotalPriceAppropriateRoom() != null ) {
                    suitableHotelsPerLocation.put( processedHotel.getPropertyCode(), processedHotel );
                    picturePosition++; //Todo pentru cand o sa am mai multe poze
                }
            }
        }
//        importPictures();
        return suitableHotelsPerLocation;
    }

    private void importPictures() {
        try {

            FileReader input = new FileReader( "hotelPictures.txt" );
            BufferedReader bufRead = new BufferedReader( input );
            String picture;

            Iterable<HotelPictureEntity> hotelPictureEntityIterable = new ArrayList<>();

            while ( ( picture = bufRead.readLine() ) != null ) {
                HotelPictureEntity hotelPictureEntity = new HotelPictureEntity( picture );
                ( (ArrayList<HotelPictureEntity>) hotelPictureEntityIterable ).add( hotelPictureEntity );
            }

            hotelPictureRepository.save( hotelPictureEntityIterable );
        } catch ( Exception e ) {
            log.error( "file not found" );
        }
    }

    public Map<String, HotelPropertyInfo> searchByHotel( HotelSearchRequestCriteria hotelSearchRequestCriteria ) {
        HotelSearchResponse hotelSearchResponse = amadeusCallsHelper.getHotelGeosearchByCircle( hotelSearchRequestCriteria );

        BigDecimal minDifLat = BigDecimal.valueOf( Double.MAX_VALUE );
        BigDecimal minDifLong = BigDecimal.valueOf( Double.MAX_VALUE );
        String hotelName = null;
        String hotelCode = null;

        Map<String, HotelPropertyInfo> hotel = new HashMap<>();

        //TODo : mai multe teste, deocamdata merge ok, trebuie depistate cazurile in care nu merge compararea de string.uri
        if ( hotelSearchResponse != null && !CollectionUtils.isEmpty( hotelSearchResponse.getResults() ) ) {
            for ( HotelPropertyResponse hotelPropertyResponse : hotelSearchResponse.getResults() ) {
                BigDecimal latitudeDifference = hotelPropertyResponse.getLocation().getLatitude()
                        .subtract( hotelSearchRequestCriteria.getLatitude() ).abs();
                BigDecimal longitudeDifference = hotelPropertyResponse.getLocation().getLongitude()
                        .subtract( hotelSearchRequestCriteria.getLongitude() ).abs();

                if ( latitudeDifference.compareTo( minDifLat ) < 0 && longitudeDifference.compareTo( minDifLong ) < 0 ) {
                    minDifLat = latitudeDifference;
                    minDifLong = longitudeDifference;
                    hotelCode = hotelPropertyResponse.getPropertyCode();
                    hotelName = hotelPropertyResponse.getPropertyName();
                }
            }
        }

        if ( hotelCode != null && hotelName != null ) {
            hotelName = hotelName.toLowerCase();
            String searchedHotel = hotelSearchRequestCriteria.getFullAddress().toLowerCase();
            String[] foundHotelName = hotelName.split( " " );

            for ( String s : foundHotelName ) {
                if ( !searchedHotel.contains( s ) )
                    return hotel;
            }

            HotelPropertyResponse hotelPropertyResponse = amadeusCallsHelper.getHotelPropertyInfoByCode( hotelSearchRequestCriteria.getInDate(),
                    hotelSearchRequestCriteria.getOutDate(), hotelCode );

            if ( hotelPropertyResponse != null ) {
                hotel.put( hotelPropertyResponse.getPropertyCode(), processHotelProperty( hotelPropertyResponse,
                        hotelSearchRequestCriteria.getInDate(), hotelSearchRequestCriteria.getOutDate(),
                        hotelSearchRequestCriteria.getPersons(), getPictureUrl( 0 ) ) );
            }
        }

        return hotel;
    }

    public HotelPropertyInfo getHotelByCode( String inDate, String outDate, String hotelPropertyCode, Integer persons ) {
        HotelPropertyResponse hotelPropertyResponse = amadeusCallsHelper.getHotelPropertyInfoByCode( inDate, outDate, hotelPropertyCode );

        return processHotelProperty( hotelPropertyResponse, inDate, outDate, persons, getPictureUrl( 0 ) );
    }

    /**
     * This method truncates the list of all suitable hotels at a location, leaving only the ones required
     * for the current page
     */
    public LinkedHashMap<String, HotelPropertyInfo> paginateForHotelSearch( Integer currentPage,
                                                                            LinkedHashMap<String, HotelPropertyInfo> allHotelsPerLocation ) {

        LinkedHashMap<String, HotelPropertyInfo> hotelsForCurrentPage = new LinkedHashMap<>();

        if ( !CollectionUtils.isEmpty( allHotelsPerLocation ) ) {
            Integer totalNrOfHotels = allHotelsPerLocation.size();
            Integer startIndex = HOTELS_PER_PAGE * ( currentPage - 1 );   //startIndex begins at 0
            Integer endIndex = startIndex + HOTELS_PER_PAGE;
            List<String> propertyCodes = new ArrayList<>( allHotelsPerLocation.keySet() );

            if ( startIndex < totalNrOfHotels ) {
                if ( endIndex > totalNrOfHotels ) {
                    endIndex = totalNrOfHotels;
                }
            }

            for ( String propertyCode : propertyCodes ) {
                if ( propertyCodes.indexOf( propertyCode ) >= startIndex && propertyCodes.indexOf( propertyCode ) < endIndex ) {
                    hotelsForCurrentPage.put( propertyCode, allHotelsPerLocation.get( propertyCode ) );
                }
            }
        }

        return hotelsForCurrentPage;
    }

    private HotelPropertyInfo processHotelProperty( HotelPropertyResponse hotelPropertyResponse, String inDate, String outDate,
                                                    Integer persons, String picturePath ) {
        HotelPropertyInfo hotelPropertyInfo = new HotelPropertyInfo();

        hotelPropertyInfo.setPropertyCode( hotelPropertyResponse.getPropertyCode() );
        hotelPropertyInfo.setPropertyName( hotelPropertyResponse.getPropertyName() );
        hotelPropertyInfo.setAddress( hotelPropertyResponse.getAddress() );
        hotelPropertyInfo.setLocation( hotelPropertyResponse.getLocation() );
        hotelPropertyInfo.setContacts( hotelPropertyResponse.getContacts() );
        hotelPropertyInfo.setAmenities( hotelPropertyResponse.getAmenities() );
        hotelPropertyInfo.setAwards( hotelPropertyResponse.getAwards() );
        hotelPropertyInfo.setPictureUrl( picturePath );

        //determine rating
        processHotelRating( hotelPropertyResponse.getPropertyCode(), hotelPropertyInfo );

        //determine appropriate rooms
        processHotelRooms( hotelPropertyResponse, hotelPropertyInfo, persons );

        processReservations( hotelPropertyInfo, LocalDate.parse( inDate ), LocalDate.parse( outDate ) );

        return hotelPropertyInfo;
    }

    private void processHotelRating( String hotelCode, HotelPropertyInfo hotelPropertyInfo ) {
        ReviewsRatingInfo reviewsRatingInfo = reviewsRatingService.getRatingByHotelCode( hotelCode );
        BigDecimal rating;

        if ( reviewsRatingInfo != null ) {
            rating = new BigDecimal( reviewsRatingInfo.getRating() / reviewsRatingInfo.getNrOfEntries() );
        } else {
            rating = DEFAULT_REVIEWS_RATING;
        }

        hotelPropertyInfo.setRating( rating );
    }

    private void processHotelRooms( HotelPropertyResponse hotelPropertyResponse, HotelPropertyInfo hotelPropertyInfo, Integer persons ) {
        Map<String, RoomDetailsInfo> roomDetailsInfos = new HashMap<>();
        BigDecimal minPrice = BigDecimal.valueOf( Double.MAX_VALUE );
        String currency = "";
        Amount totalPrice = new Amount();

        BigDecimal maxRoomDimension = new BigDecimal( -1 ); //some low value, nu exista camere cu dimensiune negativa

        for ( HotelRoom room : hotelPropertyResponse.getRooms() ) {
            RoomDetailsInfo roomDetailsInfo = roomDetailsHelper.processRoom( room );
            roomDetailsInfos.put( roomDetailsInfo.getBookingCode(), roomDetailsInfo );

            //minPrice se calculeaza doar daca am gasit macar o camera potrivita pentru nr meu de oaspeti
            if ( roomDetailsInfo.getNrOfGuests() != null
                    && roomDetailsInfo.getNrOfGuests().equals( persons ) ) {
                //get dimension of the room
                if ( roomDetailsInfo.getRoomDimension() != null &&
                        maxRoomDimension.compareTo( roomDetailsInfo.getRoomDimension() ) < 0 ) {
                    maxRoomDimension = roomDetailsInfo.getRoomDimension();
                }

                BigDecimal priceForRoom = new BigDecimal( roomDetailsInfo.getTotalPrice().getAmount() );
                if ( minPrice.compareTo( priceForRoom ) > 0 ) {
                    minPrice = priceForRoom;
                    currency = roomDetailsInfo.getTotalPrice().getCurrency();
                }
            }
        }
        if ( !currency.equals( "" ) && !minPrice.equals( BigDecimal.valueOf( Double.MAX_VALUE ) ) ) {
            totalPrice.setCurrency( currency );
            totalPrice.setAmount( String.valueOf( minPrice ) );
            hotelPropertyInfo.setTotalPriceAppropriateRoom( totalPrice );
        }

        hotelPropertyInfo.setMaxRoomDimension( maxRoomDimension );
        hotelPropertyInfo.setRooms( roomDetailsInfos );
    }

    private void processReservations( HotelPropertyInfo hotelPropertyInfo, LocalDate inDate, LocalDate outDate ) {
        Map<String, RoomDetailsInfo> rooms = hotelPropertyInfo.getRooms();
        Map<String, ReservationEntity> activeReservationsByHotel = reservationService.findActiveReservationsByHotel( inDate, outDate, hotelPropertyInfo.getPropertyCode() );

        for ( String bookingCode : activeReservationsByHotel.keySet() ) {
//            ReservationEntity reservationEntity = activeReservationsByHotel.get( bookingCode );
            RoomDetailsInfo roomDetailsInfo = rooms.get( bookingCode );

            //daca camera este rezervata local, scoasa din lista camerelor disponibile
            if ( roomDetailsInfo != null ) {
                rooms.remove( bookingCode );

//                //daca camera este libera local, adaugata la camerele disponibile
//            } else if ( reservationEntity.getStatus() != ReservationStatusEnum.ACTIVE.getType() && roomDetailsInfo == null ) {
//                rooms.put( bookingCode, reservationEntity.getRoomDetails() );
//            }

            }
        }
    }

    public String getPictureUrl( Integer index ) {
        List<HotelPictureEntity> hotelPictureEntities = hotelPictureRepository.findAll();
        Collections.shuffle( hotelPictureEntities );

        String pictureUrl;
        if ( CollectionUtils.isEmpty( hotelPictureEntities ) ) {
            pictureUrl = "https://i.imgur.com/ZREFhPZ.jpg";
        } else {
            pictureUrl = hotelPictureEntities.get( index ).getPictureUrl();
        }

        return pictureUrl;
    }
}
