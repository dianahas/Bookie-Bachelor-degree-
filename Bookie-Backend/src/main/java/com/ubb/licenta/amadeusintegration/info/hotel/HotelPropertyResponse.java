package com.ubb.licenta.amadeusintegration.info.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import com.ubb.licenta.amadeusintegration.info.room.HotelRoom;
import com.ubb.licenta.commons.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * HotelPropertyResponse
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelPropertyResponse {
    @SerializedName("property_code")
    private String propertyCode;

    @SerializedName("property_name")
    private String propertyName;

    @SerializedName("marketing_text")
    private String marketingText;

    @SerializedName("location")
    private Geolocation location;

    @SerializedName("address")
    private Address address;

    @SerializedName("total_price")
    private Amount totalPrice; //The lowest price of a stay, from the given check in date to the given check out date.

    @SerializedName("min_daily_rate")
    private Amount minDailyRate; //The lowest price per day that the hotel offers between the given check-in and check-out dates. Extra taxes may apply to this rate.

    @SerializedName("contacts")
    private List<Contact> contacts;

    @SerializedName("amenities")
    private List<Amenity> amenities;

    @SerializedName("awards")
    private List<Award> awards;

    @SerializedName("images")
    private List<Image> images;

    @SerializedName("rooms")
    private List<HotelRoom> rooms;
}

