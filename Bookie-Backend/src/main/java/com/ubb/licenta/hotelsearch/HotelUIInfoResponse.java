package com.ubb.licenta.hotelsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubb.licenta.commons.Address;
import com.ubb.licenta.commons.Amount;
import com.ubb.licenta.commons.Geolocation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelUIInfoResponse {
    private String propertyCode;
    private String propertyName;
    private Geolocation location;
    private Address address;
    private Amount totalPrice;
    private BigDecimal rating;
    private BigDecimal maxRoomDimension;
    private String pictureUrl;
}
