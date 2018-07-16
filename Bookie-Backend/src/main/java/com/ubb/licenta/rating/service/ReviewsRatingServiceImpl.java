package com.ubb.licenta.rating.service;

import com.ubb.licenta.rating.ReviewsRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsRatingServiceImpl implements ReviewsRatingService {

    @Autowired
    private ReviewsRatingObject reviewsRatingObject;

    @Override
    public ReviewsRatingInfo saveReviewRating( ReviewsRatingInfo reviewsRatingInfo ) {
        return reviewsRatingObject.saveReviewRating( reviewsRatingInfo );
    }

    @Override
    public List<ReviewsRatingInfo> getReviewsRatingByHotelCodes( List<String> hotelCodes ) {
        return reviewsRatingObject.getReviewsRatingByHotelCodes( hotelCodes );
    }

    @Override
    public ReviewsRatingInfo getRatingByHotelCode( String hotelCode ) {
        return reviewsRatingObject.getReviewsRatingByHotelCode( hotelCode );
    }
}
