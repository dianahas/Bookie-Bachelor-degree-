package com.ubb.licenta.reviews.service;

import com.ubb.licenta.reviews.resource.FindReviewsCriteria;
import com.ubb.licenta.reviews.resource.ReviewInfo;
import com.ubb.licenta.reviews.resource.ReviewSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewObject reviewObject;

    @Override
    public ReviewInfo storeReview( ReviewInfo reviewInfo ) {
        return reviewObject.storeReview( reviewInfo );
    }

    @Override
    public ReviewSearchResult getReviews( FindReviewsCriteria findReviewsCriteria ) {
        return reviewObject.getReviews( findReviewsCriteria );
    }

    @Override
    public ReviewInfo getReviewByHotelAndUser( String hotelCode, String userId ) {
        return reviewObject.getReviewByHotelAndUser( hotelCode, userId );
    }
}
