package com.tasanah.springsecurity.auth;

import com.tasanah.springsecurity.constant.SecurityConstants;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

  private SecurityContextService authenticationService;

  public AuthenticationFilter(SecurityContextService jwtAuthService) {
    this.authenticationService = jwtAuthService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    String authenticationHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER_KEY);
    String apiSecretHeader = request.getHeader(SecurityConstants.API_SECRET_HEADER_KEY);
    if (authenticationHeader != null && authenticationHeader.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
      // User token
      SecurityContextHolder
        .getContext()
        .setAuthentication(
          authenticationService.decodeUserToken(
            // Remove header prefix of "Bearer"
            authenticationHeader.replace(SecurityConstants.BEARER_TOKEN_PREFIX, "").trim()
          )
        );
    } else if (apiSecretHeader != null) {
      // Service key
      SecurityContextHolder
        .getContext()
        .setAuthentication(authenticationService.decodeServiceApiSecret(apiSecretHeader));
    }

    filterChain.doFilter(request, response);
  }
}
