package com.ubb.licenta.roomrates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubb.licenta.commons.Amenity;
import com.ubb.licenta.hotelsearch.HotelUIInfoResponse;
import com.ubb.licenta.reviews.resource.ReviewInfo;
import com.ubb.licenta.roomdetails.room.RoomDetailsInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomRatesSearchResponse {
    private HotelUIInfoResponse hotelUIInfoResponse;
    private List<Amenity> amenities;
    private List<RoomDetailsInfo> roomDetailsInfos;
    private Map<Integer, List<ReviewInfo>> reviews;
    private Boolean successful;
}
