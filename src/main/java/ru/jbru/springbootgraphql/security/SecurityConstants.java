package ru.jbru.springbootgraphql.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/v1/auth/**";
    public static final String SECRET = "SecretText";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long ACCESS_EXPIRATION_TIME = 7_200_000; //60min
    public static final long REFRESH_EXPIRATION_TIME = 7_200_000; //60min
}

