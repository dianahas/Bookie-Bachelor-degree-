package com.ubb.licenta.roomrates.service;

import com.ubb.licenta.amadeusintegration.info.hotel.HotelPropertyResponse;
import com.ubb.licenta.amadeusintegration.info.room.HotelRoom;
import com.ubb.licenta.commons.AmadeusCallsHelper;
import com.ubb.licenta.hotelsearch.HotelUIInfoResponse;
import com.ubb.licenta.reviews.resource.FindReviewsCriteria;
import com.ubb.licenta.reviews.resource.ReviewInfo;
import com.ubb.licenta.reviews.service.ReviewService;
import com.ubb.licenta.roomdetails.RoomDetailsHelper;
import com.ubb.licenta.roomdetails.room.RoomDetailsInfo;
import com.ubb.licenta.roomrates.RoomRatesSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomRatesObject {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AmadeusCallsHelper amadeusCallsHelper;

    @Autowired
    private RoomDetailsHelper roomDetailsHelper;


    public RoomRatesSearchResponse searchRoomRates( String hotelCode, String inDate, String outDate ) {

        RoomRatesSearchResponse roomRatesSearchResponse = new RoomRatesSearchResponse();
        List<RoomDetailsInfo> roomDetailsInfos = new ArrayList<>();
        HotelPropertyResponse hotelPropertyResponse = amadeusCallsHelper.getHotelPropertyInfoByCode( inDate, outDate, hotelCode );

        HotelUIInfoResponse hotelUIInfoResponse = new HotelUIInfoResponse();
        Boolean successful = false;

        if ( hotelPropertyResponse != null ) {
            hotelUIInfoResponse.setPropertyCode( hotelPropertyResponse.getPropertyCode() );
            hotelUIInfoResponse.setPropertyName( hotelPropertyResponse.getPropertyName() );
            hotelUIInfoResponse.setAddress( hotelPropertyResponse.getAddress() );
            hotelUIInfoResponse.setLocation( hotelPropertyResponse.getLocation() );

            roomRatesSearchResponse.setAmenities( hotelPropertyResponse.getAmenities() );

            FindReviewsCriteria findReviewsCriteria = new FindReviewsCriteria();
            findReviewsCriteria.setHotelCode( hotelPropertyResponse.getPropertyCode() );

            List<ReviewInfo> reviewInfoList = reviewService.getReviews( findReviewsCriteria ).getReviews();
            roomRatesSearchResponse.setReviews( reviewInfoList.stream().collect( Collectors.groupingBy( ReviewInfo::getRating ) ) );

            for ( HotelRoom room : hotelPropertyResponse.getRooms() ) {
                RoomDetailsInfo roomDetailsInfo = roomDetailsHelper.processRoom( room );

                if ( roomDetailsInfo.getNrOfGuests() != null ) {
                    roomDetailsInfos.add( roomDetailsInfo );
                }
            }

            Comparator roomsComparator = Comparator.comparing( RoomDetailsInfo::getNrOfGuests );
            roomDetailsInfos.sort( roomsComparator );

            successful = true;
        }

        roomRatesSearchResponse.setSuccessful( successful );
        roomRatesSearchResponse.setHotelUIInfoResponse( hotelUIInfoResponse );
        roomRatesSearchResponse.setRoomDetailsInfos( roomDetailsInfos );

        return roomRatesSearchResponse;
    }
}
