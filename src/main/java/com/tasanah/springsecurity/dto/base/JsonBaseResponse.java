package com.tasanah.springsecurity.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper class for JSON element on response body. Use your own class as
 * generic type replacement.
 *
 * @author timpamungkas
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Base response (JSON)")
public class JsonBaseResponse<T> {

  /**
   * <code>header</code> section
   */
  @Schema(description = "header (metadata)")
  private JsonBaseHeader header;

  /**
   * Actual data
   */
  @Schema(description = "actual data")
  private T data;

  /**
   * Return no data, should be called if your API returns non 2xx response code
   *
   * @param startTime processing start time millis
   * @param errors
   */
  public JsonBaseResponse(long startTime, JsonBaseError... errors) {
    this(startTime, Arrays.asList(errors));
  }

  /**
   * Return no data, should be called if your API returns non 2xx response code
   *
   * @param startTime processing start time millis
   * @param errors
   */
  public JsonBaseResponse(long startTime, List<JsonBaseError> errors) {
    this();
    var jsonBaseHeader = new JsonBaseHeader();
    jsonBaseHeader.setSuccess(false);
    jsonBaseHeader.setErrors(errors);
    jsonBaseHeader.setProcessTime(System.currentTimeMillis() - startTime);
    this.setHeader(jsonBaseHeader);
  }

  /**
   *
   * @param data      data to be returned
   * @param startTime processing start time millis
   */
  public JsonBaseResponse(long startTime, T data) {
    this();
    var jsonBaseHeader = new JsonBaseHeader();
    jsonBaseHeader.setSuccess(true);
    jsonBaseHeader.setErrors(new ArrayList<>());
    jsonBaseHeader.setProcessTime(System.currentTimeMillis() - startTime);
    this.setHeader(jsonBaseHeader);

    this.setData(data);
  }
}
