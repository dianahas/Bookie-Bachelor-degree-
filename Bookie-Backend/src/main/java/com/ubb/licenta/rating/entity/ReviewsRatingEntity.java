package com.ubb.licenta.rating.entity;

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
@Document(collection = "rating")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewsRatingEntity extends DocumentBaseEntity {
    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "hotel_code")
    private String hotelCode;

    @Field(value = "rating")
    private Integer rating;

    @Field(value = "nr_of_entries")
    private Integer nrOfEntries;
}
