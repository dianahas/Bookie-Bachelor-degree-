package com.ubb.licenta.reservation.service;

import com.ubb.licenta.commons.ReservationStatusEnum;
import com.ubb.licenta.constants.DocumentTypeEnum;
import com.ubb.licenta.hotel.HotelPropertyInfo;
import com.ubb.licenta.hotelsearch.HotelSearchRepository;
import com.ubb.licenta.reservation.entity.ReservationEntity;
import com.ubb.licenta.reservation.repository.ReservationRepository;
import com.ubb.licenta.reservation.resource.BookReservationRequestInfo;
import com.ubb.licenta.reservation.resource.ReservationDetailsResponse;
import com.ubb.licenta.reservation.resource.ReservationSearchResult;
import com.ubb.licenta.reservation.resource.ReservationsByUserResult;
import com.ubb.licenta.roomdetails.room.RoomDetailsInfo;
import com.ubb.licenta.utils.ValidationUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReservationObject {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelSearchRepository hotelSearchRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final static MapperFacade mapper = mapperFactory.getMapperFacade();

    static {
        mapperFactory.classMap( ReservationEntity.class, ReservationDetailsResponse.class )
                .byDefault()
                .register();
    }

    public ReservationDetailsResponse bookReservation( BookReservationRequestInfo bookReservationRequestInfo ) {
        HotelPropertyInfo hotelPropertyInfo = hotelSearchRepository.getHotelByCode( bookReservationRequestInfo.getInDate(),
                bookReservationRequestInfo.getOutDate(), bookReservationRequestInfo.getHotelCode(), bookReservationRequestInfo.getPersons() );
        ValidationUtils.validateRequiredObject( hotelPropertyInfo, "hotelPropertyInfo", "No hotel was found with that code" );

        RoomDetailsInfo roomDetailsInfo = hotelPropertyInfo.getRooms().get( bookReservationRequestInfo.getBookingCode() );
        ValidationUtils.validateRequiredObject( roomDetailsInfo, "roomDetailsInfo", "No room was found with that code" );

        ReservationEntity reservationEntity = getReservationEntityFromBookReservationRequestInfo( bookReservationRequestInfo );

        reservationEntity.setHotelName( hotelPropertyInfo.getPropertyName() );
        reservationEntity.setRoomDetails( roomDetailsInfo );
        reservationEntity.setPrice( new BigDecimal( roomDetailsInfo.getTotalPrice().getAmount() ) );
        reservationEntity.setReservationTimestamp( LocalDate.now() );
        reservationEntity.setType( DocumentTypeEnum.RESERVATION_TYPE.getType() );

        ReservationEntity savedReservation = reservationRepository.save( reservationEntity );

//        if ( savedReservation != null ) {
//            sendSimpleMessage( bookReservationRequestInfo.getGuestInfo().getEmail(),
//                    RESERVATION_CONFIRMATION_EMAIL_SUBJECT, createReservationConfirmationText( savedReservation ) );
//        }

        ReservationDetailsResponse reservationDetailsResponse = mapper.map( reservationRepository.save( reservationEntity ),
                ReservationDetailsResponse.class );
        reservationDetailsResponse.setStatus( ReservationStatusEnum.ACTIVE.getType() );
        reservationDetailsResponse.setPictureUrl( hotelSearchRepository.getPictureUrl( 0 ) );

        return reservationDetailsResponse;
    }

    public Map<String, ReservationEntity> findActiveReservationsByHotel( LocalDate inDate, LocalDate outDate, String hotelCode ) {
        Map<String, ReservationEntity> reservationEntityMap = new HashMap<>();

        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where( "inDate" ).gt( outDate ),
                        Criteria.where( "outDate" ).lt( inDate )
                ).and( "hotelCode" ).is( hotelCode )
        );

        List<ReservationEntity> reservationEntityList = mongoTemplate.find( query, ReservationEntity.class, "reservations" );

        if ( !CollectionUtils.isEmpty( reservationEntityList ) ) {
            for ( ReservationEntity reservationEntity : reservationEntityList ) {
                reservationEntityMap.put( reservationEntity.getBookingCode(), reservationEntity );
            }
        }

        return reservationEntityMap;
    }

    public ReservationDetailsResponse cancelReservation( String reservationId ) {
        ReservationEntity reservationEntity = reservationRepository.findOne( reservationId );
        reservationEntity.setIsCancelled( true );

        ReservationDetailsResponse reservationDetailsResponse = mapper.map( reservationRepository.save( reservationEntity ),
                ReservationDetailsResponse.class );
        reservationDetailsResponse.setStatus( ReservationStatusEnum.CANCELLED.getType() );
        reservationDetailsResponse.setPictureUrl( hotelSearchRepository.getPictureUrl( 0 ) );

        return reservationDetailsResponse;
    }

    public ReservationSearchResult fetchReservationsByUser( String userId ) {
        List<ReservationEntity> reservationEntities = reservationRepository.findAllByUserId( userId );
        ReservationSearchResult reservationSearchResult = new ReservationSearchResult();
        ReservationsByUserResult reservationsByUserResult = new ReservationsByUserResult( new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
        Boolean successful = false;

        if ( !CollectionUtils.isEmpty( reservationEntities ) ) {
            successful = true;

            for ( ReservationEntity reservationEntity : reservationEntities ) {
                ReservationDetailsResponse reservationDetailsResponse = mapper.map( reservationEntity, ReservationDetailsResponse.class );
                reservationDetailsResponse.setPictureUrl( hotelSearchRepository.getPictureUrl( 0 ) );

                if ( reservationEntity.getIsCancelled() != null && reservationEntity.getIsCancelled() ) {
                    reservationDetailsResponse.setStatus( ReservationStatusEnum.CANCELLED.getType() );
                    reservationsByUserResult.getCancelled().add( reservationDetailsResponse );
                } else if ( reservationEntity.getOutDate().equals( LocalDate.now() ) ||
                        reservationEntity.getOutDate().isBefore( LocalDate.now() ) ) {
                    reservationDetailsResponse.setStatus( ReservationStatusEnum.PAST.getType() );
                    reservationsByUserResult.getPast().add( reservationDetailsResponse );
                } else {
                    reservationDetailsResponse.setStatus( ReservationStatusEnum.ACTIVE.getType() );
                    reservationsByUserResult.getUpcoming().add( reservationDetailsResponse );
                }
            }
        }

        reservationSearchResult.setReservationsByUserResult( reservationsByUserResult );
        reservationSearchResult.setSuccessful( successful );

        return reservationSearchResult;
    }

    private ReservationEntity getReservationEntityFromBookReservationRequestInfo( BookReservationRequestInfo bookReservationRequestInfo ) {
        ReservationEntity reservationEntity = new ReservationEntity();

        reservationEntity.setBookingCode( bookReservationRequestInfo.getBookingCode() );
        reservationEntity.setHotelCode( bookReservationRequestInfo.getHotelCode() );
        reservationEntity.setUserId( bookReservationRequestInfo.getUserId() );
        reservationEntity.setInDate( LocalDate.parse( bookReservationRequestInfo.getInDate() ) );
        reservationEntity.setOutDate( LocalDate.parse( bookReservationRequestInfo.getOutDate() ) );
        reservationEntity.setGuestInfo( bookReservationRequestInfo.getGuestInfo() );
        reservationEntity.setPersons( bookReservationRequestInfo.getPersons() );
        reservationEntity.setAddress( bookReservationRequestInfo.getAddress() );

        return reservationEntity;
    }

    private String createReservationConfirmationText( ReservationEntity reservationEntity ) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append( "Hello " )
                .append( reservationEntity.getGuestInfo().getFirstName() )
                .append( " " )
                .append( reservationEntity.getGuestInfo().getLastName() )
                .append( ". You booked a room at hotel " )
                .append( reservationEntity.getHotelName() )
                .append( " from " )
                .append( reservationEntity.getInDate() )
                .append( " until " )
                .append( reservationEntity.getOutDate() )
                .append( " at a price of " )
                .append( reservationEntity.getPrice() );

        return String.valueOf( stringBuilder );
    }

    private void sendSimpleMessage( String to, String subject, String text ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo( to );
        message.setSubject( subject );
        message.setText( text );
        emailSender.send( message );
    }

}