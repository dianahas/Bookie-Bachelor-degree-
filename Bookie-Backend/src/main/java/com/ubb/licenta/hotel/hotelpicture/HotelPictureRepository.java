package com.ubb.licenta.hotel.hotelpicture;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HotelPictureRepository extends MongoRepository<HotelPictureEntity, String> {
    /**
     * Finds {@link HotelPictureEntity} by ID
     *
     * @param id user document id
     */
    HotelPictureEntity findOne( String id );

    List<HotelPictureEntity> findAll();

    <S extends HotelPictureEntity> S save( S entity );

    <S extends HotelPictureEntity> List<S> save( Iterable<S> entites );
}
