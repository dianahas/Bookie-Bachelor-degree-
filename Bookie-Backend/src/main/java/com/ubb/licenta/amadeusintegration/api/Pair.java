package com.ubb.licenta.amadeusintegration.api;

import lombok.Getter;

@Getter
public class Pair {
    private String name = "";
    private String value = "";

    public Pair( String name, String value ) {
        setName( name );
        setValue( value );
    }

    private void setName( String name ) {
        if ( !isValidString( name ) ) return;

        this.name = name;
    }

    private void setValue( String value ) {
        if ( !isValidString( value ) ) return;

        this.value = value;
    }

    private boolean isValidString( String arg ) {
        return arg != null && !arg.trim().isEmpty();
    }
}
