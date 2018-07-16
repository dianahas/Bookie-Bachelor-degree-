package com.ubb.licenta.reservation.resource;

import com.ubb.licenta.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationResource {
    private static final Logger log = LoggerFactory.getLogger( ReservationResource.class );

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/book")
    public ResponseEntity<?> processBookReservation( @RequestBody BookReservationRequestInfo bookReservationRequestInfo ) {
        ResponseEntity<?> bookReservationResponse;

        try {
            ReservationDetailsResponse reservationDetailsResponse = reservationService.bookReservation( bookReservationRequestInfo );
            bookReservationResponse = new ResponseEntity<>( reservationDetailsResponse, HttpStatus.OK );

        } catch ( Throwable t ) {
            log.error( "Couldn't perform book for room with roomCode = {}, error: ", bookReservationRequestInfo.getBookingCode(), t );
            bookReservationResponse = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return bookReservationResponse;
    }

    @PostMapping(value = "/cancel")
    public ResponseEntity<?> cancelReservation( @RequestBody String reservationId ) {
        ResponseEntity<?> cancelReservationResponse;

        try {
            cancelReservationResponse = new ResponseEntity<>( reservationService.cancelReservation( reservationId ), HttpStatus.OK );

        } catch ( Throwable t ) {
            log.error( "Couldn't cancel the reservation with id = {}, error: ", reservationId, t );
            cancelReservationResponse = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return cancelReservationResponse;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> processFetchReservationsByUser( @RequestParam(value = "userId") String userId ) {
        ResponseEntity<?> fetchReservationsByUserResponse;

        try {
            ReservationSearchResult reservations = reservationService.fetchReservationsByUser( userId );
            fetchReservationsByUserResponse = new ResponseEntity<>( reservations, HttpStatus.OK );

        } catch ( Throwable t ) {
            log.error( "Couldn't fetch reservations for user with id = {}, error: ", userId, t );
            fetchReservationsByUserResponse = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return fetchReservationsByUserResponse;
    }
}
