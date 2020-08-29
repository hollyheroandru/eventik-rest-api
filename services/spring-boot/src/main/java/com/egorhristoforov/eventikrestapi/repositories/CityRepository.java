package com.egorhristoforov.eventikrestapi.repositories;

import com.egorhristoforov.eventikrestapi.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "SELECT * FROM cities c WHERE LOWER(c.en_name) = LOWER(:name) OR LOWER(c.ru_name) = LOWER(:name)", nativeQuery = true)
    Optional<City> findByOneOfNamesIgnoreCase(@Param(value = "name") String name);
}
