package com.ubb.licenta.roomdetails.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubb.licenta.commons.Amount;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDetailsInfo {
    private String bookingCode;
    private String roomTypeCode;
    private String rateTypeCode;
    private Amount totalPrice;
    private List<String> descriptions;
    private RoomTypeInfo roomTypeInfo;

    private Integer nrOfGuests;
    private BigDecimal roomDimension;
}
