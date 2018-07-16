package com.ubb.licenta.reviews.repository;

import com.ubb.licenta.reviews.entity.ReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<ReviewEntity, String> {
    /**
     * Finds {@link ReviewEntity} by ID
     *
     * @param id hotel document id
     */
    ReviewEntity findOne( String id );

    /**
     * Saves the {@link ReviewEntity}
     *
     * @param entity hotel entity
     */
    <S extends ReviewEntity> S save( S entity );

    ReviewEntity findByHotelCodeAndUserId( String hotelCode, String userId );

    List<ReviewEntity> findAll();

    List<ReviewEntity> findAll( Iterable<String> reviewIds );

    List<ReviewEntity> findAllByHotelCode( String hotelCode );

    List<ReviewEntity> findAllByUserId( String userId );
}
