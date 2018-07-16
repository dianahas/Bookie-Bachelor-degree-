package com.ubb.licenta.reviews.service;

import com.ubb.licenta.constants.DocumentTypeEnum;
import com.ubb.licenta.rating.ReviewsRatingInfo;
import com.ubb.licenta.rating.service.ReviewsRatingService;
import com.ubb.licenta.reviews.entity.ReviewEntity;
import com.ubb.licenta.reviews.repository.ReviewRepository;
import com.ubb.licenta.reviews.resource.FindReviewsCriteria;
import com.ubb.licenta.reviews.resource.ReviewInfo;
import com.ubb.licenta.reviews.resource.ReviewSearchResult;
import com.ubb.licenta.utils.ValidationUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewObject {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewsRatingService reviewsRatingService;

    private final static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final static MapperFacade mapper = mapperFactory.getMapperFacade();

    static {
        mapperFactory.classMap( ReviewInfo.class, ReviewEntity.class )
                .byDefault()
                .register();

        mapperFactory.classMap( ReviewEntity.class, ReviewInfo.class )
                .byDefault()
                .register();
    }

    public ReviewInfo storeReview( ReviewInfo reviewInfo ) {
        ValidationUtils.validateRequiredObject( reviewInfo, "reviewInfo" );

        ReviewEntity existingReview = reviewRepository.findByHotelCodeAndUserId( reviewInfo.getHotelCode(), reviewInfo.getUserId() );
        storeReviewsRating( reviewInfo, existingReview );

        if ( existingReview != null ) {
            existingReview.setText( reviewInfo.getText() );
            existingReview.setRating( reviewInfo.getRating() );
            existingReview.setReviewTimestamp( LocalDate.now() );

        } else {
            existingReview = mapper.map( reviewInfo, ReviewEntity.class );
            existingReview.setReviewTimestamp( LocalDate.now() );
            existingReview.setType( DocumentTypeEnum.REVIEW_TYPE.getType() );
        }

        return mapper.map( reviewRepository.save( existingReview ), ReviewInfo.class );
    }

    public ReviewSearchResult getReviews( FindReviewsCriteria findReviewsCriteria ) {
        ReviewSearchResult reviewSearchResult = new ReviewSearchResult();
        List<ReviewEntity> reviewEntities;
        Boolean successful = false;

        if ( findReviewsCriteria.getHotelCode() != null ) {
            reviewEntities = reviewRepository.findAllByHotelCode( findReviewsCriteria.getHotelCode() );
        } else if ( findReviewsCriteria.getUserId() != null ) {
            reviewEntities = reviewRepository.findAllByUserId( findReviewsCriteria.getUserId() );
        } else if ( findReviewsCriteria.getReviewIds() != null ) {
            reviewEntities = reviewRepository.findAll( findReviewsCriteria.getReviewIds() );
        } else {
            reviewEntities = reviewRepository.findAll();
        }

        List<ReviewInfo> reviewInfoList = new ArrayList<>();

        if ( reviewEntities != null ) {
            successful = true;

            for ( ReviewEntity reviewEntity : reviewEntities ) {
                ReviewInfo reviewInfo = mapper.map( reviewEntity, ReviewInfo.class );
                ReviewsRatingInfo reviewsRatingByHotelCode = reviewsRatingService.getRatingByHotelCode( reviewEntity.getHotelCode() );

                if ( reviewsRatingByHotelCode.getRating() != null ) {
                    reviewInfo.setRating( reviewsRatingByHotelCode.getRating() );
                }

                reviewInfoList.add( reviewInfo );
            }
        }

        reviewSearchResult.setReviews( reviewInfoList );
        reviewSearchResult.setSuccessful( successful );
        return reviewSearchResult;
    }

    public ReviewInfo getReviewByHotelAndUser( String hotelCode, String userId ) {
        ValidationUtils.validateRequiredObject( hotelCode, "hotelCode" );
        ValidationUtils.validateRequiredObject( userId, "userId" );

        ReviewInfo reviewInfo = mapper.map( reviewRepository.findByHotelCodeAndUserId( hotelCode, userId ), ReviewInfo.class );
        ReviewsRatingInfo reviewsRatingByHotelCode = reviewsRatingService.getRatingByHotelCode( hotelCode );
        if ( reviewsRatingByHotelCode != null && reviewInfo != null ) {
            reviewInfo.setRating( reviewsRatingByHotelCode.getRating() );
        }

        return reviewInfo;
    }

    private void storeReviewsRating( ReviewInfo reviewInfo, ReviewEntity existingReview ) {
        ReviewsRatingInfo reviewsRatingInfo = reviewsRatingService.getRatingByHotelCode( reviewInfo.getHotelCode() );

        if ( reviewsRatingInfo != null ) {
            Integer currentHotelRating = reviewsRatingInfo.getRating();
            Integer rating = currentHotelRating + reviewInfo.getRating();

            if ( existingReview != null ) {
                Integer previousUserReviewRating = existingReview.getRating();
                rating = rating - previousUserReviewRating;
            } else {
                Integer currentNrOfEntries = reviewsRatingInfo.getNrOfEntries();
                reviewsRatingInfo.setNrOfEntries( currentNrOfEntries + 1 );
            }

            reviewsRatingInfo.setRating( rating );

        } else if ( reviewInfo.getRating() != null ) {
            reviewsRatingInfo = new ReviewsRatingInfo();
            reviewsRatingInfo.setHotelCode( reviewInfo.getHotelCode() );
            reviewsRatingInfo.setRating( reviewInfo.getRating() );
            reviewsRatingInfo.setNrOfEntries( 1 );
        }

        reviewsRatingService.saveReviewRating( reviewsRatingInfo );
    }
}
