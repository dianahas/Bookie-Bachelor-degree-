package com.ubb.licenta.roomrates.service;

import com.ubb.licenta.roomrates.RoomRatesSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomRatesServiceImpl implements RoomRatesService {

    @Autowired
    private RoomRatesObject roomRatesObject;

    @Override
    public RoomRatesSearchResponse searchRoomRates( String hotelCode, String inDate, String outDate ) {
        return roomRatesObject.searchRoomRates( hotelCode, inDate, outDate );
    }
}
