package com.ubb.licenta.reviews.entity;

import com.ubb.licenta.configuration.DocumentBaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "reviews")
public class ReviewEntity extends DocumentBaseEntity {

    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "user_id")
    private String userId;

    @Field(value = "hotel_code")
    private String hotelCode;

    @Field(value = "hotel_name")
    private String hotelName;

    @Field(value = "text")
    private String text;

    @Field(value = "rating")
    private Integer rating;

    @Field(value = "review_timestamp")
    private LocalDate reviewTimestamp;
}
