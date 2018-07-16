package com.ubb.licenta.hotelsearch;

import com.ubb.licenta.hotelsearch.service.HotelSearchService;
import com.ubb.licenta.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

import static com.ubb.licenta.constants.ApplicationConstants.*;

@RestController
@RequestMapping(value = "/hotelsearch")
public class HotelSearchResource {
    private static final Logger log = LoggerFactory.getLogger( HotelSearchResource.class );

    @Autowired
    private HotelSearchService hotelSearchService;

    @GetMapping
    public ResponseEntity<?> processHotelSearch( @RequestParam(value = "checkInDate") String inDate,
                                                 @RequestParam(value = "checkOutDate") String outDate,
                                                 @RequestParam(value = "persons") String persons,
                                                 @RequestParam(value = "radius", required = false) String radius,
                                                 @RequestParam(value = "isSearchByHotel") Boolean searchByHotel,
                                                 @RequestParam(value = "address", required = false) String fullAddress,
                                                 @RequestParam(value = "latitude") String latitude,
                                                 @RequestParam(value = "longitude") String longitude,
                                                 @RequestParam(value = "initialSearch") Boolean initialSearch,
                                                 @RequestParam(value = "startIndex") String startIndex ) {
        ResponseEntity<?> hotelSearchResultResponse;

        try {
            log.info( "Attempting to perform hotel search in " + fullAddress );

            if ( radius == null ) {
                radius = searchByHotel ? DEFAULT_RADIUS_SEARCH_BY_HOTEL : DEFAULT_RADIUS_HOTEL_SEARCH;
            }

            ValidationUtils.validateRequiredObject( searchByHotel, "searchByHotel" );
            ValidationUtils.validateRequiredObject( startIndex, "startIndex" );
            ValidationUtils.validateRequiredObject( persons, "persons" );
            ValidationUtils.validateRequiredObject( inDate, "inDate" );
            ValidationUtils.validateRequiredObject( outDate, "outDate" );

            HotelSearchRequestCriteria hotelSearchRequestCriteria = new HotelSearchRequestCriteria( searchByHotel, inDate,
                    outDate, Integer.valueOf( persons ), new BigDecimal( latitude ), new BigDecimal( longitude ),
                    new Integer( radius ), fullAddress, initialSearch, Integer.valueOf( startIndex ) );

            if ( !searchByHotel ) {
                hotelSearchResultResponse = new ResponseEntity<>( hotelSearchService.searchHotels( hotelSearchRequestCriteria ), HttpStatus.OK );

            } else {
                hotelSearchResultResponse = new ResponseEntity<>( hotelSearchService.searchByHotel( hotelSearchRequestCriteria ), HttpStatus.OK );
            }

            log.info( "Successfully performed hotel search in " + fullAddress );

        } catch ( Throwable t ) {
            log.error( "Couldn't perform a hotel search request, error: ", t );
            hotelSearchResultResponse = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return hotelSearchResultResponse;
    }

    @GetMapping(value = "/paginate")
    public ResponseEntity<?> processPaginateHotels( @ModelAttribute @Valid() SortHotelsCriteria sortHotelsCriteria ) {
        ResponseEntity<?> hotelSearchResultResponse;

        try {
            ValidationUtils.validateRequiredObject( sortHotelsCriteria.getStartIndex(), "sortHotelsCriteria.startIndex" );

            if ( sortHotelsCriteria.getNotSort() ) {
                HotelSearchRequestCriteria hotelSearchRequestCriteria = new HotelSearchRequestCriteria();
                hotelSearchRequestCriteria.setInitialSearch( sortHotelsCriteria.getInitial() );
                hotelSearchRequestCriteria.setStartIndex( sortHotelsCriteria.getStartIndex() );
                hotelSearchRequestCriteria.setFullAddress( sortHotelsCriteria.getAddress() );
                hotelSearchRequestCriteria.setInDate( sortHotelsCriteria.getCheckInDate() );
                hotelSearchRequestCriteria.setOutDate( sortHotelsCriteria.getCheckOutDate() );
                hotelSearchRequestCriteria.setLatitude( sortHotelsCriteria.getLatitude() );
                hotelSearchRequestCriteria.setLongitude( sortHotelsCriteria.getLongitude() );
                hotelSearchRequestCriteria.setPersons( sortHotelsCriteria.getPersons() );
                hotelSearchRequestCriteria.setSearchByHotel( false );
                hotelSearchRequestCriteria.setRadius( sortHotelsCriteria.getRadius() == null
                        ? Integer.valueOf( DEFAULT_RADIUS_HOTEL_SEARCH ) : sortHotelsCriteria.getRadius() );

                hotelSearchResultResponse = new ResponseEntity<>( hotelSearchService.searchHotels( hotelSearchRequestCriteria ), HttpStatus.OK );
            } else {
                hotelSearchResultResponse = new ResponseEntity<>( hotelSearchService.sortHotels( sortHotelsCriteria ), HttpStatus.OK );
            }

        } catch ( Throwable t ) {
            log.error( "Couldn't perform a hotel search request, error: ", t );
            hotelSearchResultResponse = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return hotelSearchResultResponse;
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<?> sortHotels( @ModelAttribute @Valid() SortHotelsCriteria sortHotelsCriteria ) {
        ResponseEntity<?> hotelSearchResultResponse;

        try {
            ValidationUtils.validateRequiredObject( sortHotelsCriteria.getAscending(), "sortHotelsCriteria.ascending" );
            ValidationUtils.validateRequiredObject( sortHotelsCriteria.getStartIndex(), "sortHotelsCriteria.startIndex" );

            hotelSearchResultResponse = new ResponseEntity<>( hotelSearchService.sortHotels( sortHotelsCriteria ), HttpStatus.OK );

        } catch ( Throwable t ) {
            log.error( "Couldn't perform a search hotels request, error: ", t );
            hotelSearchResultResponse = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return hotelSearchResultResponse;
    }
}
