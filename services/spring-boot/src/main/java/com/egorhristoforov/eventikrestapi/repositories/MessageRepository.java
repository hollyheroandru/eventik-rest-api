package com.egorhristoforov.eventikrestapi.repositories;

import com.egorhristoforov.eventikrestapi.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
