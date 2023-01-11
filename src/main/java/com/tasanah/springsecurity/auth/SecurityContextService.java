package com.tasanah.springsecurity.auth;

import java.util.UUID;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface SecurityContextService {
  public UsernamePasswordAuthenticationToken decodeUserToken(String token);

  public AbstractAuthenticationToken decodeServiceApiSecret(String key);

  public long getCurrentUserId();

  public UUID getCurrentUserTokenId();

  public String getCurrentUserPhoneNumber();

  public String getCurrentToken();

  public String getDeviceId();

  public String getCurrentUserEmail();
}
