package com.egorhristoforov.eventikrestapi.repositories;

import com.egorhristoforov.eventikrestapi.models.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {
}
