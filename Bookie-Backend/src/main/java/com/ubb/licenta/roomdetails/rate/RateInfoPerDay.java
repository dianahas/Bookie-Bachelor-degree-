package com.ubb.licenta.roomdetails.rate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RateInfoPerDay {
    private LocalDate startDay;
    private LocalDate endDate;
    private BigDecimal pricePerDay;
    private String currencyCode;
}
