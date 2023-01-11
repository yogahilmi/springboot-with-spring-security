package com.tasanah.springsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DummyResponse {

  private Long id;
  private String code;
  private String name;
  private String category;
  private String description;
}
