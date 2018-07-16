package com.ubb.licenta.commons;

public enum ReservationStatusEnum {
    ACTIVE( (byte) 1 ),
    CANCELLED( (byte) 2 ),
    PAST( (byte) 3 );
    private byte type;

    ReservationStatusEnum( byte type ) {
        this.type = type;
    }

    public static ReservationStatusEnum getFromType( Byte type ) {
        ReservationStatusEnum returnedType = null;

        for ( ReservationStatusEnum reservationStatusEnum : values() ) {
            if ( reservationStatusEnum.type == type ) {
                returnedType = reservationStatusEnum;
                break;
            }
        }

        return returnedType;
    }

    public byte getType() {
        return type;
    }
}
