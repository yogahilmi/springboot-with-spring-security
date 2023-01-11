package com.tasanah.springsecurity.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper class for JSON element on response body, section <code>header</code>.
 *
 * @author timpamungkas
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
  description = "Base response (JSON). " +
  "<code>header</code> contains metadata. "
)
public class JsonBaseHeader {

  @Builder.Default
  @Schema(description = "List of errors")
  private List<JsonBaseError> errors = new ArrayList<>();

  /**
   * Process time to generate the entire response
   */
  @Schema(description = "Process time API untuk generate response (millisecond)", example = "15")
  private long processTime;

  /**
   * <code>true</code> if your API response is returning HTTP status response 2xx,
   * <code>false</code> otherwise.
   */
  @Schema(
    description = "Seharusnya <code>true</code> jika API response return HTTP status 2xx, " +
    "<code>false</code> jika tidak",
    example = "true"
  )
  private boolean success;

  public void addError(JsonBaseError error) {
    this.errors.add(error);
  }
}
