package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComicRepository extends JpaRepository<Comic, UUID> {
}