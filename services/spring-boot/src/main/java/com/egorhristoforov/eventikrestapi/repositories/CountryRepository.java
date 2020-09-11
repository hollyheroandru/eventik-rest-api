package com.egorhristoforov.eventikrestapi.repositories;

import com.egorhristoforov.eventikrestapi.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByRuNameIgnoreCase(String ruName);
    Optional<Country> findByEnNameIgnoreCase(String enName);

    @Query(value = "SELECT * FROM countries c WHERE LOWER(c.en_name) = LOWER(:name) OR LOWER(c.ru_name) = LOWER(:name)", nativeQuery = true)
    Optional<Country> findByOneOfNamesIgnoreCase(@Param(value = "name") String name);
}
