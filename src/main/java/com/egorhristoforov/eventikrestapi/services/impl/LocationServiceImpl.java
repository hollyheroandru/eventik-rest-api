package com.egorhristoforov.eventikrestapi.services.impl;

import com.egorhristoforov.eventikrestapi.dtos.responses.CitiesListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.CountriesListResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.models.City;
import com.egorhristoforov.eventikrestapi.models.Country;
import com.egorhristoforov.eventikrestapi.repositories.CityRepository;
import com.egorhristoforov.eventikrestapi.repositories.CountryRepository;
import com.egorhristoforov.eventikrestapi.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Value("${eventik.app.email}")
    private String appEmailAddress;

    @Autowired
    private JavaMailSender javaMailSender;

    private void sendEmail(String email, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);
    }

    public List<CountriesListResponse> getCountries() {
        return countryRepository.findAll()
                .stream()
                .map((country) -> new CountriesListResponse(country.getId(), country.getEnName(), country.getRuName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CitiesListResponse> getCitiesForCountry(Long id)
            throws ResourceNotFoundException {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

        return country.getCities()
                .stream()
                .map((city) -> new CitiesListResponse(city.getId(), city.getEnName(), city.getRuName(), city.getLongitude(), city.getLatitude()))
                .collect(Collectors.toList());
    }

    public Long getCityId(String countryName, String cityName, Double longitude, Double latitude) {
        Optional<City> existedCity = cityRepository.findByOneOfNamesIgnoreCase(cityName);

        if (existedCity.isPresent()) {
            return existedCity.get().getId();
        }

        Optional<Country> existedCountry = countryRepository.findByOneOfNamesIgnoreCase(countryName);

        City createdCity = new City();
        createdCity.setEnName(cityName);
        createdCity.setRuName(cityName);
        createdCity.setLatitude(latitude);
        createdCity.setLongitude(longitude);
        createdCity.setAddedByUser(true);

        if (existedCountry.isPresent()) {
            createdCity.setCountry(existedCountry.get());

            cityRepository.save(createdCity);

            sendEmail(appEmailAddress, "Добавлен новый город",
                    "Пользователь добавил новый город в базу данных.\n" +
                            "id: " + createdCity.getId() + "\n" +
                            "name: " + cityName + "\n" +
                            "country: " + existedCountry.get().getRuName() + "\n" +
                            "longitude: " + longitude + "\n" +
                            "latitude: " + latitude);
        } else {
            Country createdCountry = new Country();
            createdCountry.setEnName(countryName);
            createdCountry.setRuName(countryName);
            createdCountry.setAddedByUser(true);

            countryRepository.save(createdCountry);

            createdCity.setCountry(createdCountry);

            cityRepository.save(createdCity);

            sendEmail(appEmailAddress, "Добавлены новая страна и город",
                    "Пользователь добавил новую страну и город в базу данных.\n" +
                            "Страна:\n" +
                            "id: " + createdCountry.getId() + "\n" +
                            "name: " + countryName + "\n" +
                            "Город:\n" +
                            "id: " + createdCity.getId() + "\n" +
                            "name: " + cityName + "\n" +
                            "longitude: " + longitude + "\n" +
                            "latitude: " + latitude);
        }

        return createdCity.getId();
    }
}
