package com.ubb.licenta.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubb.licenta.commons.*;
import com.ubb.licenta.roomdetails.room.RoomDetailsInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelPropertyInfo {
    private String propertyCode;
    private String propertyName;
    private Geolocation location;
    private Address address;
    private Amount totalPriceAppropriateRoom;
    private List<Contact> contacts;
    private List<Amenity> amenities;
    private List<Award> awards;
    private Map<String, RoomDetailsInfo> rooms;
    private BigDecimal rating;
    private BigDecimal maxRoomDimension;
    private String pictureUrl;
}