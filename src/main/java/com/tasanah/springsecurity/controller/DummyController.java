package com.tasanah.springsecurity.controller;

import com.tasanah.springsecurity.dto.CreateDummyRequest;
import com.tasanah.springsecurity.dto.DummyResponse;
import com.tasanah.springsecurity.service.DummyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Dummy", description = "The dummy controller")
public class DummyController {

  @Autowired
  private DummyService dummyService;

  @GetMapping("/dummy")
  @Operation(summary = "Get latest dummy value", operationId = "Hai")
  public DummyResponse getLatestDummy() {
    log.info("invoking get on /dummy route");
    return dummyService.getLatestDummy();
  }

  @PostMapping("/dummy")
  @Operation(summary = "Create a new dummy", operationId = "Hai")
  public DummyResponse createDummy(@Valid @RequestBody CreateDummyRequest createDummyRequest) {
    log.info("Invoking post on /dummy route");
    return dummyService.createDummy(createDummyRequest);
  }

  @GetMapping("/dummy/{id}")
  @Operation(summary = "Get dummy for provided id value", operationId = "Hai")
  public DummyResponse getDummy(@PathVariable Long id) {
    log.info("Invoking get on /dummy/{id} route");
    return dummyService.getDummy(id);
  }
}
