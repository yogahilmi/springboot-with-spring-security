package com.tasanah.springsecurity.constant;

public abstract class SecurityConstants {

  public enum AuthenticationClaim {
    REGISTRATION,
    LOGIN,
    RESET_PIN,
    EKYC,
    SYSTEM_SERVICE,
  }

  public enum Platform {
    IOS,
    ANDROID,
    WEB,
  }

  public enum TokenType {
    REFRESH,
    ACCESS,
    TEMP,
  }

  public static final String TOKEN_TYPE_CLAIM_KEY = "type";
  public static final String TOKEN_ROLES_CLAIM_KEY = "roles";
  public static final String TOKEN_USER_ID_CLAIM_KEY = "userId";
  public static final String TOKEN_EMAIL_CLAIM_KEY = "email";
  public static final String TOKEN_DEVICE_ID_CLAIM_KEY = "deviceId";

  public static final String BEARER_TOKEN_PREFIX = "Bearer";
  public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
  public static final String API_SECRET_HEADER_KEY = "api-secret";
}
