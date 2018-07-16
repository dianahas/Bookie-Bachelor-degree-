package com.ubb.licenta.reservation.service;

import com.ubb.licenta.reservation.entity.ReservationEntity;
import com.ubb.licenta.reservation.resource.BookReservationRequestInfo;
import com.ubb.licenta.reservation.resource.ReservationDetailsResponse;
import com.ubb.licenta.reservation.resource.ReservationSearchResult;

import java.time.LocalDate;
import java.util.Map;

public interface ReservationService {
    ReservationDetailsResponse bookReservation( BookReservationRequestInfo bookReservationRequestInfo );

    ReservationSearchResult fetchReservationsByUser( String userId );

    Map<String, ReservationEntity> findActiveReservationsByHotel( LocalDate inDate, LocalDate outDate, String hotelCode );

    ReservationDetailsResponse cancelReservation( String reservationId );
}
