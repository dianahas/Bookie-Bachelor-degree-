package com.ubb.licenta.rating.repository;

import com.ubb.licenta.reviews.entity.ReviewEntity;
import com.ubb.licenta.rating.entity.ReviewsRatingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewsRatingRepository extends MongoRepository<ReviewsRatingEntity, String> {
    /**
     * Finds {@link ReviewsRatingEntity} by ID
     *
     * @param id user document id
     */
    ReviewsRatingEntity findOne( String id );

    /**
     * Saves the {@link ReviewEntity}
     *
     * @param entity hotel entity
     */
    <S extends ReviewsRatingEntity> S save( S entity );

    ReviewsRatingEntity findByHotelCode( String hotelCode );

    List<ReviewsRatingEntity> findAllByHotelCodeIsIn( List<String> hotelCodes );
}
