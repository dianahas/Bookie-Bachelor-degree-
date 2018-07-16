package com.ubb.licenta.commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * An object to describe the coordinates of a map location
 */
@Getter
@Setter
@ApiModel(description = "An object to describe the coordinates of a map location")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geolocation {
    private BigDecimal latitude;
    private BigDecimal longitude;
    @SerializedName( "google_maps_link")
    private String googleMapsLink; //For YapQ APIs only  - a link to a google map rendering of this location.

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Geolocation geolocation = (Geolocation) o;
        return Objects.equals( this.latitude, geolocation.latitude ) &&
                Objects.equals( this.longitude, geolocation.longitude ) &&
                Objects.equals( this.googleMapsLink, geolocation.googleMapsLink );
    }
}

