package com.ubb.licenta.constants;

public enum DocumentTypeEnum {
    USER_TYPE( "user" ),
    RESERVATION_TYPE( "res" ),
    REVIEW_TYPE( "review" ),
    REVIEW_RATING_TYPE( "review-rating" );

    private String type;

    DocumentTypeEnum( String type ) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
