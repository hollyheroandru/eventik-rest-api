package com.egorhristoforov.eventikrestapi.controllers.app;

import com.egorhristoforov.eventikrestapi.dtos.responses.location.CitiesListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.location.CountriesListResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.services.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
@Api(value = "location-controller", tags = "/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get countries list")
    public ResponseEntity<List<CountriesListResponse>> getCountriesList() {
        List<CountriesListResponse> response = locationService.getCountries();

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/countries/{id}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get cities list for country")
    public ResponseEntity<List<CitiesListResponse>> getCitiesFromCountry(@PathVariable(value = "id") Long countryId)
            throws ResourceNotFoundException {
        List<CitiesListResponse> response = locationService.getCitiesForCountry(countryId);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/countries/cities/id", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get city id")
    public Long getCityId(@RequestParam(value = "country-name") @NotBlank String countryName,
                          @RequestParam(value = "city-name", defaultValue = "") @NotBlank String cityName,
                          @RequestParam(value = "longitude") Double longitude,
                          @RequestParam(value = "latitude") Double latitude) {

        return locationService.getCityId(countryName, cityName, longitude, latitude);
    }
}
