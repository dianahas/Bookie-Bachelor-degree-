package com.ubb.licenta.user.repository;

import com.ubb.licenta.user.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    /**
     * Finds {@link UserEntity} by ID
     *
     * @param id user document id
     */
    UserEntity findOne(String id);

    UserEntity findByUsername(String username);

    /**
     * Saves the {@link UserEntity}
     *
     * @param entity user entity
     */
    <S extends UserEntity> S save(S entity);

    List<UserEntity> findAll();

    List<UserEntity> findAllByIsActiveEquals(Boolean isActive);

    List<UserEntity> findAll( Iterable<String> userIds );
}
