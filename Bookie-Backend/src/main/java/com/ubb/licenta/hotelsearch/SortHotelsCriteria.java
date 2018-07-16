package com.ubb.licenta.hotelsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SortHotelsCriteria {
    private Boolean initial;
    private Integer startIndex;
    private Boolean ascending;
    private Boolean byPrice;
    private Boolean byRating;
    private Boolean byRoomDimension;
    private Boolean notSort;
    private String checkInDate;
    private String checkOutDate;
    private Integer persons;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer radius;
    private String address;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortHotelsCriteria that = (SortHotelsCriteria) o;
        return Objects.equals(ascending, that.ascending) &&
                Objects.equals(byPrice, that.byPrice) &&
                Objects.equals(byRating, that.byRating) &&
                Objects.equals(byRoomDimension, that.byRoomDimension) &&
                Objects.equals(notSort, that.notSort) &&
                Objects.equals(checkInDate, that.checkInDate) &&
                Objects.equals(checkOutDate, that.checkOutDate) &&
                Objects.equals(persons, that.persons) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(radius, that.radius) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ascending, byPrice, byRating, byRoomDimension, notSort, checkInDate, checkOutDate, persons,
                latitude, longitude, radius, address);
    }
}
