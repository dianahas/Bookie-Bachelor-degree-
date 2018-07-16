package com.ubb.licenta.roomdetails.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomTypeInfo {
    private String roomType;
    private String bedType;
    private String numberOfBeds;
    private Integer persons;
}
