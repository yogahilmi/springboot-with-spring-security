package com.tasanah.springsecurity.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tasanah.springsecurity.repository.DummyRepository;
import com.tasanah.springsecurity.service.impl.DummyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "unit-test")
class DummyServiceTest {

  @InjectMocks
  private DummyServiceImpl dummyService;

  @Mock
  private DummyRepository mockDummyRepository;

  @Test
  public void test_no_db_record_exists() {
    assertThrows(ResponseStatusException.class, () -> dummyService.getLatestDummy());
  }
}
