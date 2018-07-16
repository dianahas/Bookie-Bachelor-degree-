package com.ubb.licenta.amadeusintegration.info.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * More detailed structured information about the room.
 */
@ApiModel(description = "More detailed structured information about the room.")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomInfo {
    @SerializedName("bed_type")
    private String bedType;

    @SerializedName("number_of_beds")
    private String numberOfBeds;

    @SerializedName("room_type")
    private String roomType;
}

