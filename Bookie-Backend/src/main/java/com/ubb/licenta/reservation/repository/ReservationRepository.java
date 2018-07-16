package com.ubb.licenta.reservation.repository;

import com.ubb.licenta.reservation.entity.ReservationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservationRepository extends MongoRepository<ReservationEntity, String> {
    /**
     * Finds {@link ReservationEntity} by ID
     *
     * @param id user document id
     */
    ReservationEntity findOne( String id );

    /**
     * Saves the {@link ReservationEntity}
     *
     * @param entity user entity
     */
    <S extends ReservationEntity> S save( S entity );

    List<ReservationEntity> findAll();

    List<ReservationEntity> findAllByUserId( String userId );

    List<ReservationEntity> findAll( Iterable<String> userIds );

    List<ReservationEntity> findAllByHotelCode( String hotelCode );
}
