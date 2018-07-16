package com.ubb.licenta.configuration;

import java.io.Serializable;

public class DocumentBaseEntity implements Serializable {
    private String type;

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }
}