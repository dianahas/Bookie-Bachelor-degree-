package com.ubb.licenta.constants;

import java.math.BigDecimal;

public interface ApplicationConstants {
    String REGISTRATION_EMAIL_SUBJECT = "Welcome to Bookie!";
    String RESERVATION_CONFIRMATION_EMAIL_SUBJECT = "Your reservation confirmation. Thanks for booking with Bookie, have a cookie:D";
    String AMADEUS_API_KEY = "fhWLchPcxnVPACk8wWUgHeL3cahhQBvf";

    String DEFAULT_RADIUS_HOTEL_SEARCH = "15";
    String DEFAULT_RADIUS_SEARCH_BY_HOTEL = "2";

    BigDecimal DEFAULT_REVIEWS_RATING = new BigDecimal( 0 );

    Integer HOTELS_PER_PAGE = 10;
}
