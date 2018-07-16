package com.ubb.licenta.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ubb.licenta.configuration.DocumentBaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity extends DocumentBaseEntity {
    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "username")
    private String username;

    @Field(value = "password")
    private String password;

    @Field(value = "firstName")
    private String firstName;

    @Field(value = "lastName")
    private String lastName;

    @Field(value = "email")
    private String email;

    @Field(value = "phone_number")
    private String phoneNumber;

    @Field(value = "isAdmin")
    private Boolean isAdmin;

    @Field(value = "isActive")
    private Boolean isActive;

    @Field(value = "create_timestamp")
    private LocalDateTime createTimestamp;

    @Field(value = "last_login_timestamp")
    private LocalDateTime lastLoginTimestamp;
}
