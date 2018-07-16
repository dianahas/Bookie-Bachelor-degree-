package com.ubb.licenta.reservation.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActualGuestInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
