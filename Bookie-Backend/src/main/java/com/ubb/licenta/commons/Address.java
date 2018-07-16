package com.ubb.licenta.commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * An object to describe a postal address.
 */
@Getter
@Setter
@ApiModel(description = "An object to describe a postal address.")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String line1;
    private String city;
    private String region;
    private String postal_code;
    private String country;
}

