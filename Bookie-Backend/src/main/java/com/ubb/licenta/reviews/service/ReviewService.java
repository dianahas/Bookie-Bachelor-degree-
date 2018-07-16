package com.ubb.licenta.reviews.service;

import com.ubb.licenta.reviews.resource.FindReviewsCriteria;
import com.ubb.licenta.reviews.resource.ReviewInfo;
import com.ubb.licenta.reviews.resource.ReviewSearchResult;

public interface ReviewService {
    ReviewInfo storeReview( ReviewInfo reviewInfo );

    ReviewSearchResult getReviews( FindReviewsCriteria findReviewsCriteria );

    ReviewInfo getReviewByHotelAndUser( String hotelCode, String userId );
}
