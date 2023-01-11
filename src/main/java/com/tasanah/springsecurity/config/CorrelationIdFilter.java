package com.tasanah.springsecurity.config;

import com.tasanah.springsecurity.constant.LoggingConstants;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    // Get correlation ID (or generate if not present)
    final String requestCorrelationId = extractOrGenerateCorrelationId(request);

    // Set logging MDC
    MDC.put(LoggingConstants.CORRELATION_ID_LOG_VAR_NAME, requestCorrelationId);

    // Set Request context
    RequestContextHolder
      .currentRequestAttributes()
      .setAttribute(
        LoggingConstants.CORRELATION_ID_LOG_VAR_NAME,
        requestCorrelationId,
        LoggingConstants.CORRELATION_ID_SCOPE
      );

    // Carry on using existing spring filter chain
    filterChain.doFilter(request, response);
  }

  /**
   * Get correction ID from current request or generate new correlation ID
   *
   * @param {@link HttpServletRequest} to extract correlation ID from
   *
   * @return {@link String} of the correction ID
   */
  private String extractOrGenerateCorrelationId(final HttpServletRequest request) {
    String headerCorrelationId = request.getHeader(LoggingConstants.CORRELATION_ID_HEADER_NAME);

    // Check if header correlation ID is null or empty string
    return StringUtils.hasText(headerCorrelationId) ? headerCorrelationId : UUID.randomUUID().toString();
  }
}
