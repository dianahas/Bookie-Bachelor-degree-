package com.ubb.licenta.utils;

public class Utils {
    public static boolean isNumeric( String str ) {
        if ( str != null )
            return str.matches( "-?\\d+(\\.\\d+)?" );  //match a number with optional '-' and decimal.
        return false;
    }
}
