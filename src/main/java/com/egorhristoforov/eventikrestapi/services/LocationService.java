package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.responses.CitiesListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.CountriesListResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;

import java.util.List;

public interface LocationService {
    List<CountriesListResponse> getCountries();

    List<CitiesListResponse> getCitiesForCountry(Long id)
            throws ResourceNotFoundException;

    Long getCityId(String countryName, String cityName, Double longitude, Double latitude);
}
