package com.ubb.licenta.hotelsearch.service;

import com.ubb.licenta.hotelsearch.HotelSearchRequestCriteria;
import com.ubb.licenta.hotelsearch.HotelSearchUIResponse;
import com.ubb.licenta.hotelsearch.SortHotelsCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelSearchServiceImpl implements HotelSearchService {

    @Autowired
    private HotelSearchObject hotelSearchObject;

    @Override
    public HotelSearchUIResponse searchHotels( HotelSearchRequestCriteria hotelSearchRequestCriteria ) {
        return hotelSearchObject.searchHotels( hotelSearchRequestCriteria );
    }

    @Override
    public HotelSearchUIResponse searchByHotel( HotelSearchRequestCriteria hotelSearchRequestCriteria ) {
        return hotelSearchObject.searchByHotel( hotelSearchRequestCriteria );
    }

    @Override
    public HotelSearchUIResponse sortHotels( SortHotelsCriteria sortHotelsCriteria ) {
        return hotelSearchObject.sortHotels(sortHotelsCriteria);

    }


}
