package com.ubb.licenta.reviews.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewInfo {
    private String id;
    private String hotelCode;
    private String hotelName;
    private String userId;
    private String text;
    private Integer rating;
}
