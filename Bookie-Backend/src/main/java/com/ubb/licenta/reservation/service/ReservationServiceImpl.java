package com.ubb.licenta.reservation.service;

import com.ubb.licenta.reservation.entity.ReservationEntity;
import com.ubb.licenta.reservation.resource.BookReservationRequestInfo;
import com.ubb.licenta.reservation.resource.ReservationDetailsResponse;
import com.ubb.licenta.reservation.resource.ReservationSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationObject reservationObject;

    @Override
    public ReservationDetailsResponse bookReservation( BookReservationRequestInfo bookReservationRequestInfo ) {
        return reservationObject.bookReservation( bookReservationRequestInfo );
    }

    @Override
    public ReservationSearchResult fetchReservationsByUser( String userId ) {
        return reservationObject.fetchReservationsByUser( userId );
    }

    @Override
    public Map<String, ReservationEntity> findActiveReservationsByHotel( LocalDate inDate, LocalDate outDate, String hotelCode ) {
        return reservationObject.findActiveReservationsByHotel( inDate, outDate, hotelCode );
    }

    @Override
    public ReservationDetailsResponse cancelReservation( String reservationId ) {
        return reservationObject.cancelReservation( reservationId );
    }
}
