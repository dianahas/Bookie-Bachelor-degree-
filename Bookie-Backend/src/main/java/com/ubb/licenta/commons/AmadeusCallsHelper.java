package com.ubb.licenta.commons;

import com.ubb.licenta.amadeusintegration.api.ApiException;
import com.ubb.licenta.amadeusintegration.api.DefaultApi;
import com.ubb.licenta.amadeusintegration.info.hotel.HotelPropertyResponse;
import com.ubb.licenta.amadeusintegration.info.hotel.HotelSearchResponse;
import com.ubb.licenta.hotelsearch.HotelSearchRequestCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.ubb.licenta.constants.ApplicationConstants.AMADEUS_API_KEY;

@Component
public class AmadeusCallsHelper {
    private static final Logger log = LoggerFactory.getLogger( AmadeusCallsHelper.class );

    public HotelSearchResponse getHotelGeosearchByCircleAllRooms( HotelSearchRequestCriteria hotelSearchRequestCriteria ) {
        DefaultApi apiInstance = new DefaultApi();
        Integer radius = hotelSearchRequestCriteria.getRadius() != null ? hotelSearchRequestCriteria.getRadius() : 10;

        HotelSearchResponse hotelSearchResponse = null;

        try {
            hotelSearchResponse = apiInstance.hotelGeosearchByCircle( AMADEUS_API_KEY, hotelSearchRequestCriteria.getLatitude(), hotelSearchRequestCriteria.getLongitude(),
                    radius, hotelSearchRequestCriteria.getInDate(), hotelSearchRequestCriteria.getOutDate(), null, null, null, null,
                    140, true, false, null );

        } catch ( ApiException e ) {
            log.info( e.getResponseBody() );
        }

        return hotelSearchResponse;
    }

    public HotelSearchResponse getHotelGeosearchByCircle( HotelSearchRequestCriteria hotelSearchRequestCriteria ) {
        DefaultApi apiInstance = new DefaultApi();
        Integer radius = hotelSearchRequestCriteria.getRadius() != null ? hotelSearchRequestCriteria.getRadius() : 2;

        HotelSearchResponse hotelSearchResponse = null;

        try {
            hotelSearchResponse = apiInstance.hotelGeosearchByCircle( AMADEUS_API_KEY, hotelSearchRequestCriteria.getLatitude(), hotelSearchRequestCriteria.getLongitude(),
                    radius, hotelSearchRequestCriteria.getInDate(), hotelSearchRequestCriteria.getOutDate(), null, null, null, null,
                    null, false, false, null );

        } catch ( ApiException e ) {
            System.out.println( e.getResponseBody() );
        }

        return hotelSearchResponse;
    }

    public HotelPropertyResponse getHotelPropertyInfoByCode( String inDate, String outDate, String hotelCode ) {
        DefaultApi apiInstance = new DefaultApi();
        HotelPropertyResponse hotelPropertyResponse = null;

        try {
            hotelPropertyResponse = apiInstance.hotelPropertyCodeSearch( AMADEUS_API_KEY, hotelCode, inDate, outDate,
                    null, null, true, null );

        } catch ( ApiException e ) {
            System.out.println( e.getResponseBody() );
        }

        return hotelPropertyResponse;
    }
}
