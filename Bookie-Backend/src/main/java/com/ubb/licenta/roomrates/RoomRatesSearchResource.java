package com.ubb.licenta.roomrates;

import com.ubb.licenta.roomrates.service.RoomRatesService;
import com.ubb.licenta.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/roomrates")
public class RoomRatesSearchResource {
    private static final Logger log = LoggerFactory.getLogger( RoomRatesSearchResource.class );

    @Autowired
    private RoomRatesService roomRatesService;

    @GetMapping()
    public ResponseEntity<?> processRoomRates( @RequestParam(value = "hotelPropertyId") String hotelCode,
                                               @RequestParam(value = "inDate") String inDate,
                                               @RequestParam(value = "outDate") String outDate ) {

        ResponseEntity<?> roomRatesSearchResultResponse;

        try {
            ValidationUtils.validateRequiredObject( hotelCode, "persons" );
            ValidationUtils.validateRequiredObject( inDate, "inDate" );
            ValidationUtils.validateRequiredObject( outDate, "outDate" );

            roomRatesSearchResultResponse = new ResponseEntity<>( roomRatesService.searchRoomRates( hotelCode, inDate, outDate ), HttpStatus.OK );

        } catch ( Throwable t ) {
            log.error( "Couldn't perform an availability search for a hotel with hotelId = {}, error: ", hotelCode, t );
            roomRatesSearchResultResponse = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );

        }

        return roomRatesSearchResultResponse;
    }
}
