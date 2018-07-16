package com.ubb.licenta.roomrates.service;

import com.ubb.licenta.roomrates.RoomRatesSearchResponse;
import com.ubb.licenta.utils.ApplicationException;

public interface RoomRatesService {
    RoomRatesSearchResponse searchRoomRates( String hotelCode, String inDate, String outDate ) throws ApplicationException;
}
