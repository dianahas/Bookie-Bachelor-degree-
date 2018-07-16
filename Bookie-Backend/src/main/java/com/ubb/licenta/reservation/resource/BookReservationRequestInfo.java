package com.ubb.licenta.reservation.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookReservationRequestInfo {
    private String hotelCode;
    private String bookingCode;
    private String userId;
    private String inDate;
    private String outDate;
    private ActualGuestInfo guestInfo;
    private Integer persons;
    private String address;
}
