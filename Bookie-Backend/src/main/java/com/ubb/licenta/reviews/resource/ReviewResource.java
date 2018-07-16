package com.ubb.licenta.reviews.resource;

import com.ubb.licenta.reviews.service.ReviewService;
import com.ubb.licenta.utils.ValidationUtils;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewResource {
    private static final Logger log = LoggerFactory.getLogger( ReviewResource.class );

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> storeReview( @ApiParam(value = "Review info object.") @Valid @RequestBody ReviewInfo reviewInfo ) {
        ResponseEntity<?> response;

        try {
            log.info( "*** Performing processStoreReview" );

            ValidationUtils.validateRequiredObject( reviewInfo.getHotelCode(), "reviewInfo.hotelCode" );
            ValidationUtils.validateRequiredObject( reviewInfo.getUserId(), "reviewInfo.userId" );
            ValidationUtils.validateRequiredObject( reviewInfo.getText(), "reviewInfo.text" );

            ReviewInfo reviewInfoResponse = reviewService.storeReview( reviewInfo );
            response = new ResponseEntity<>( reviewInfoResponse, HttpStatus.OK );

        } catch ( Exception e ) {
            log.error( "Error when trying to store a review : {}.", reviewInfo.toString(), e );
            response = new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReviews( @ModelAttribute @Valid() FindReviewsCriteria findReviewsCriteria ) {
        ResponseEntity<?> response;

        try {
            log.info( "*** Performing getReviews" );

            ReviewSearchResult reviews = reviewService.getReviews( findReviewsCriteria );
            response = new ResponseEntity<>( reviews, HttpStatus.OK );

            log.info( "*** Successfully performed getReviews" );

        } catch ( Exception e ) {
            log.error( "Error when trying to fetch reviews ", e );
            response = new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReview( @RequestParam(value = "hotelCode") String hotelCode,
                                        @RequestParam(value = "userId", required = false) String userId ) {
        ResponseEntity<?> response;

        try {
            log.info( "*** Performing getReviews" );

            ValidationUtils.validateRequiredObject( hotelCode, "hotelCode" );
            ValidationUtils.validateRequiredObject( userId, "userId" );

            ReviewInfo review = reviewService.getReviewByHotelAndUser( hotelCode, userId );
            response = new ResponseEntity<>( review, HttpStatus.OK );

            log.info( "*** Successfully performed getReviews" );

        } catch ( Exception e ) {
            log.error( "Error when trying to fetch review ", e );
            response = new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }
}
