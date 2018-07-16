package com.ubb.licenta.hotelsearch.service;

import com.ubb.licenta.hotelsearch.HotelSearchRequestCriteria;
import com.ubb.licenta.hotelsearch.HotelSearchUIResponse;
import com.ubb.licenta.hotelsearch.SortHotelsCriteria;

public interface HotelSearchService {
    HotelSearchUIResponse searchHotels( HotelSearchRequestCriteria hotelSearchRequestCriteria );

    HotelSearchUIResponse searchByHotel( HotelSearchRequestCriteria hotelSearchRequestCriteria );

    HotelSearchUIResponse sortHotels( SortHotelsCriteria sortHotelsCriteria );
}
