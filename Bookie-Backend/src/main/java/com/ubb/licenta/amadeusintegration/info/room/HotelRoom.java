package com.ubb.licenta.amadeusintegration.info.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import com.ubb.licenta.amadeusintegration.info.rate.PeriodRate;
import com.ubb.licenta.commons.Amount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * HotelRoom
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelRoom {
    @SerializedName("booking_code")
    private String bookingCode;

    @SerializedName("room_type_code")
    private String roomTypeCode;

    @SerializedName("rate_plan_code")
    private String ratePlanCode;

    @SerializedName("total_amount")
    private Amount totalAmount;

    @SerializedName("rates")
    private List<PeriodRate> rates;

    @SerializedName("descriptions")
    private List<String> descriptions;

    @SerializedName("room_type_info")
    private RoomInfo roomTypeInfo;

    @SerializedName("rate_type_code")
    private String rateTypeCode;
}

