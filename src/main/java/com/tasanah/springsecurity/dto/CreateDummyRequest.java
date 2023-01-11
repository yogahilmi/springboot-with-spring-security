package com.tasanah.springsecurity.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDummyRequest {

  @NotBlank
  private String code;

  @NotBlank
  private String name;

  @NotNull
  private String category;

  @NotNull
  private String description;
}
