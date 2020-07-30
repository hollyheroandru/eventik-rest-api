package com.egorhristoforov.eventikrestapi.controllers;

import com.egorhristoforov.eventikrestapi.dtos.responses.location.CitiesListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.location.CountriesListResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountriesListResponse>> getCountriesList() {
        List<CountriesListResponse> response = locationService.getCountries();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/countries/{id}/cities")
    public ResponseEntity<List<CitiesListResponse>> getCitiesFromCountry(@PathVariable(value = "id") Long countryId)
            throws ResourceNotFoundException {
        List<CitiesListResponse> response = locationService.getCitiesForCountry(countryId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/countries/cities/id")
    public Long getCityId(@RequestParam(value = "country-name") @NotBlank String countryName,
                          @RequestParam(value = "city-name", defaultValue = "") @NotBlank String cityName,
                          @RequestParam(value = "longitude") Double longitude,
                          @RequestParam(value = "latitude") Double latitude) {

        return locationService.getCityId(countryName, cityName, longitude, latitude);
    }
}
