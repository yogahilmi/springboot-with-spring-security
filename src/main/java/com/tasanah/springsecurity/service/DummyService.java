package com.tasanah.springsecurity.service;

import com.tasanah.springsecurity.dto.CreateDummyRequest;
import com.tasanah.springsecurity.dto.DummyResponse;

public interface DummyService {
  public DummyResponse getLatestDummy();

  public DummyResponse getDummy(Long id);

  public DummyResponse createDummy(CreateDummyRequest createDummyRequest);
}
