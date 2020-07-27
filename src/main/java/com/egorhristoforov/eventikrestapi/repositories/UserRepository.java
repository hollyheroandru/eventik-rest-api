package com.egorhristoforov.eventikrestapi.repositories;

import com.egorhristoforov.eventikrestapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
