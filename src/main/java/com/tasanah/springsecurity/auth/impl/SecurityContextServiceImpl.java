package com.tasanah.springsecurity.auth.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tasanah.springsecurity.auth.SecurityContextService;
import com.tasanah.springsecurity.constant.SecurityConstants;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class SecurityContextServiceImpl implements SecurityContextService {

  @Value("${setting.jwt.token.issuer}")
  private String tokenIssuer;

  @Value("${setting.service.internal.key}")
  private String internalServiceSecret;

  @Value("${setting.service.internal.name}")
  private String internalServiceName;

  @Autowired
  private Algorithm tokenAlgorithm;

  @Override
  public UsernamePasswordAuthenticationToken decodeUserToken(String token) {
    UsernamePasswordAuthenticationToken tokenUser = null;
    try {
      // Decode token
      DecodedJWT jwtToken = JWT.require(tokenAlgorithm).withIssuer(tokenIssuer).build().verify(token);

      tokenUser =
        new UsernamePasswordAuthenticationToken(
          // Principal - Actual Decoded Token object
          jwtToken,
          // Password - Empty Password
          null,
          // Authorities - User Groups as authorities
          jwtToken
            .getClaims()
            .get(SecurityConstants.TOKEN_ROLES_CLAIM_KEY)
            .asList(String.class)
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList())
        );
    } catch (JWTVerificationException exception) {
      log.error("JWT error", exception);
    }

    return tokenUser;
  }

  @Override
  public AbstractAuthenticationToken decodeServiceApiSecret(String secret) {
    AbstractAuthenticationToken serviceUser = null;
    if (StringUtils.hasText(secret) && secret.equals(internalServiceSecret)) {
      serviceUser =
        new RunAsUserToken(
          secret,
          internalServiceName,
          internalServiceName,
          Arrays.asList(new SimpleGrantedAuthority(SecurityConstants.AuthenticationClaim.SYSTEM_SERVICE.toString())),
          null
        );
    }

    return serviceUser;
  }

  @Override
  public long getCurrentUserId() {
    long currentUserId = 0;
    if (getCurrentDecodedJwt() != null) {
      currentUserId = getCurrentDecodedJwt().getClaim(SecurityConstants.TOKEN_USER_ID_CLAIM_KEY).asLong();
    }

    return currentUserId;
  }

  @Override
  public String getCurrentUserPhoneNumber() {
    String currentUserPhoneNumber = "";
    if (getCurrentDecodedJwt() != null) {
      currentUserPhoneNumber = getCurrentDecodedJwt().getSubject();
    }

    return currentUserPhoneNumber;
  }

  @Override
  public UUID getCurrentUserTokenId() {
    String currentTokenId = "";
    if (getCurrentDecodedJwt() != null) {
      currentTokenId = getCurrentDecodedJwt().getId();
    }

    return UUID.fromString(currentTokenId);
  }

  @Override
  public String getCurrentToken() {
    String currentToken = "";
    if (getCurrentDecodedJwt() != null) {
      currentToken = getCurrentDecodedJwt().getToken();
    }

    return currentToken;
  }

  @Override
  public String getDeviceId() {
    String deviceId = null;
    if (getCurrentDecodedJwt() != null) {
      deviceId = getCurrentDecodedJwt().getClaim(SecurityConstants.TOKEN_DEVICE_ID_CLAIM_KEY).asString();
    }

    return deviceId;
  }

  @Override
  public String getCurrentUserEmail() {
    String email = "";
    if (getCurrentDecodedJwt() != null) {
      email = getCurrentDecodedJwt().getClaim(SecurityConstants.TOKEN_EMAIL_CLAIM_KEY).asArray(String.class)[0];
    }

    return email;
  }

  private DecodedJWT getCurrentDecodedJwt() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (principal instanceof DecodedJWT ? (DecodedJWT) principal : null);
  }
}
