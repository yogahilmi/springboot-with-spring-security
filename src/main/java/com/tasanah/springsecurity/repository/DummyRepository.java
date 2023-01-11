package com.tasanah.springsecurity.repository;

import com.tasanah.springsecurity.entity.Dummy;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyRepository extends CrudRepository<Dummy, Long> {
  public Optional<Dummy> findFirstByOrderByIdDesc();
}
