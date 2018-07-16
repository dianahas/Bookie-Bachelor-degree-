package com.ubb.licenta.hotel.hotelpicture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "pictures")
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelPictureEntity {
    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "picture_url")
    private String pictureUrl;

    public HotelPictureEntity( String picture ) {
        this.pictureUrl = picture;
    }
}
