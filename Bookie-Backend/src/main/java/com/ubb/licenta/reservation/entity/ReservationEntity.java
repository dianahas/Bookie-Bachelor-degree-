package com.ubb.licenta.reservation.entity;

import com.ubb.licenta.configuration.DocumentBaseEntity;
import com.ubb.licenta.reservation.resource.ActualGuestInfo;
import com.ubb.licenta.roomdetails.room.RoomDetailsInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "reservations")
public class ReservationEntity extends DocumentBaseEntity {
    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "userId")
    private String userId;

    @Field(value = "bookingCode")
    private String bookingCode; //room identifier

    @Field(value = "roomDetails")
    private RoomDetailsInfo roomDetails;

    @Field(value = "hotelCode")
    private String hotelCode;

    @Field(value = "hotelName")
    private String hotelName;

    @Field(value = "inDate")
    private LocalDate inDate;

    @Field(value = "outDate")
    private LocalDate outDate;

    @Field(value = "price")
    private BigDecimal price;

    @Field(value = "isCancelled")
    private Boolean isCancelled;

    @Field(value = "guestInfo")
    private ActualGuestInfo guestInfo;

    @Field(value = "guests")
    private Integer persons;

    @Field(value = "address")
    private String address;

    @Field(value = "picturePath")
    private String picturePath;

    @Field(value = "reservationTimestamp")
    private LocalDate reservationTimestamp;
}
