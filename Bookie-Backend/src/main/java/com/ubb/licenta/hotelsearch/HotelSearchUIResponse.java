package com.ubb.licenta.hotelsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelSearchUIResponse {
    private List<HotelUIInfoResponse> results;
    private Boolean successful;
    private Integer totalNrOfHotels;
    private Integer totalNrOfPages;
    private Integer currentPage;
    private Boolean isSort;
}
