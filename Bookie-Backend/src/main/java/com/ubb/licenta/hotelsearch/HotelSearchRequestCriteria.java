package com.ubb.licenta.hotelsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelSearchRequestCriteria {
    private Boolean searchByHotel;
    private String inDate;
    private String outDate;
    private Integer persons;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer radius;
    private String fullAddress;

    private Boolean initialSearch;
    private Integer startIndex;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelSearchRequestCriteria that = (HotelSearchRequestCriteria) o;
        return Objects.equals(persons, that.persons) &&
                Objects.equals(inDate, that.inDate) &&
                Objects.equals(outDate, that.outDate) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(radius, that.radius) &&
                Objects.equals(fullAddress, that.fullAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(persons, inDate, outDate, latitude, longitude, radius, fullAddress);
    }

    @Override
    public String toString() {
        return "HotelSearchRequestCriteria{" +
                " persons=" + persons +
                ", inDate='" + inDate + '\'' +
                ", outDate='" + outDate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", radius=" + radius +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}
