package com.ubb.licenta.commons;

import lombok.Getter;

@Getter
public enum BedTypeEnum {
    SINGLE( 'S', "single", 1 ),
    TWIN( 'T', "twin", 1 ),
    DOUBLE( 'D', "double", 2 ),
    QUEEN( 'Q', "queen", 2 ),
    KING( 'K', "king", 2 );

    private Character identifier;
    private String bedType;
    private Integer persons;

    BedTypeEnum( Character identifier, String bedType, Integer persons ) {
        this.identifier = identifier;
        this.bedType = bedType;
        this.persons = persons;
    }

    public static BedTypeEnum getFromIdentifierOrType( Character identifier, String bedType ) {
        BedTypeEnum returnedBedType = null;

        for (BedTypeEnum bedTypeEnum : values()) {
            if ( bedTypeEnum.identifier == identifier || bedTypeEnum.bedType.equals( bedType ) ) {
                returnedBedType = bedTypeEnum;
                break;
            }
        }

        return returnedBedType;
    }

    public static Integer getPersonsFromIdentifierOrType( Character identifier, String bedType ) {
        Integer persons = null;

        for ( BedTypeEnum bedTypeEnum : values() ) {
            if ( bedTypeEnum.identifier == identifier || bedTypeEnum.bedType.equals( bedType ) ) {
                persons = bedTypeEnum.persons;
                break;
            }
        }

        return persons;
    }
}
