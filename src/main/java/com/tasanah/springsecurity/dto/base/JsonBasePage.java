package com.tasanah.springsecurity.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pagination wrapper
 *
 * @author timpamungkas
 *
 * @param <T> data type
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Pagination wrapper for base JSON response")
public class JsonBasePage<T> {

  @Schema(description = "size of record per page", example = "20")
  private int size;

  @Schema(description = "page number (1 based)", example = "1")
  private int page;

  @Schema(description = "total pages", example = "5")
  private int totalPage;

  @Schema(description = "total records", example = "96")
  private long totalRecord;

  @Schema(description = "actual data")
  private List<T> data;
}
