package com.ubb.licenta.rating.service;

import com.ubb.licenta.rating.ReviewsRatingInfo;

import java.util.List;

public interface ReviewsRatingService {
    ReviewsRatingInfo saveReviewRating( ReviewsRatingInfo reviewsRatingInfo );
    List<ReviewsRatingInfo> getReviewsRatingByHotelCodes( List<String> hotelCodes );
    ReviewsRatingInfo getRatingByHotelCode( String hotelCode );
}
