package com.ubb.licenta.constants;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String REGISTER_URL = "/user/register";
    public static final String LOGIN_URL = "/user/login";
    public static final String HOTEL_SEARCH_URL = "/hotelsearch/**";
    public static final String ROOM_RATES_URL = "/roomrates/**";
}
