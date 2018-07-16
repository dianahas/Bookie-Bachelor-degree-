package com.ubb.licenta.reservation.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubb.licenta.roomdetails.room.RoomDetailsInfo;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDetailsResponse {
    private String id;
    private String hotelCode;
    private String hotelName;
    private String inDate;
    private String outDate;
    private BigDecimal price;
    private RoomDetailsInfo roomDetails;
    private ActualGuestInfo guestInfo;
    private Byte status;
    private Integer persons;
    private String address;
    private String pictureUrl;
}