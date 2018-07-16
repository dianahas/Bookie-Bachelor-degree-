package com.ubb.licenta.amadeusintegration.info.rate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubb.licenta.commons.Amount;
import lombok.Getter;
import lombok.Setter;

/**
 * Rate
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {
    private String type;
    private Amount price;
}

