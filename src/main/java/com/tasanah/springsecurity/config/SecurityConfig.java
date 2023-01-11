package com.tasanah.springsecurity.config;

import com.tasanah.springsecurity.auth.AuthenticationFilter;
import com.tasanah.springsecurity.auth.SecurityContextService;
import com.tasanah.springsecurity.constant.SecurityConstants.AuthenticationClaim;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http,SecurityContextService authenticationService) throws Exception {
    http
      .csrf()
      .disable()
      // make sure we use stateless session; session won't be used to store user's
      // state.
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      // handle an authorized attempts (if we happen to have any other custom
      // response)
      .exceptionHandling()
      .authenticationEntryPoint((request, response, authError) ->
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
      )
      .and()
      // Add a filter to validate the tokens with every request (service will take
      // care of that)
      // CLOSED, authentication is handled from apigee via bearer. This implementation is also
      // from bearer with JWT format and can cause conflict.
            .addFilterAfter(
      // This is where we hook our auth filter
              new AuthenticationFilter(authenticationService),
              UsernamePasswordAuthenticationFilter.class
            )
      // Authorization requests config
      .authorizeRequests()
      // Enabled swagger end points
      .antMatchers("/api-docs", "/api-docs/**", "/swagger*/**", "/webjars/**")
      .permitAll()
      // Allow actuator APIs for health check and monitoring
      .antMatchers("/actuator/**")
      .permitAll()
      // Allow OPTIONS call for preflight requests
      .antMatchers(HttpMethod.OPTIONS)
      .permitAll()
      // Allow internal endpoints for service users only !
      .antMatchers("/**")
      .hasAuthority(AuthenticationClaim.SYSTEM_SERVICE.toString())
      // All others should be public
      .anyRequest()
      .permitAll();

    return http.build();
  }
}
