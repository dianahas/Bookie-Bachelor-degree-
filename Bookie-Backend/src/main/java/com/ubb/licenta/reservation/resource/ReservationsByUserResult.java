package com.ubb.licenta.reservation.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationsByUserResult {
    private List<ReservationDetailsResponse> upcoming;
    private List<ReservationDetailsResponse> past;
    private List<ReservationDetailsResponse> cancelled;
}
