package com.egorhristoforov.eventikrestapi.services.impl;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventUpdateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventsListResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.models.*;
import com.egorhristoforov.eventikrestapi.repositories.*;
import com.egorhristoforov.eventikrestapi.services.AdminService;
import com.egorhristoforov.eventikrestapi.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    LocationService locationService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    private List<UsersListResponse> makeUsersListResponse(List<User> users) {
        return users
                .stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .map(UsersListResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsersListResponse> getUsersListByEmailPatternOrAll(String emailPattern) throws ResourceNotFoundException {
        if(emailPattern != null) {
            return makeUsersListResponse(userRepository.findUsersByEmailPattern(emailPattern + "%"));
        }
        return makeUsersListResponse(userRepository.findAll());
    }

    @Override
    public void deleteUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public AdminCityResponse getCityById(Long cityId) throws ResourceNotFoundException {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        return new AdminCityResponse(city);
    }

    @Override
    public AdminCityResponse createCity(AdminCityCreateRequest request) throws BadRequestException, ResourceNotFoundException {
        City city = cityRepository
                .findByBothOfNamesIgnoreCase(request.getEnName(), request.getRuName())
                .orElse(new City());

        if(city.isValid()) {
            throw new BadRequestException("The city already exists");
        }

        Country country = countryRepository.findById(request.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

        city.setEnName(request.getEnName());
        city.setRuName(request.getRuName());
        city.setCountry(country);

        cityRepository.save(city);

        return new AdminCityResponse(city);
    }

    @Override
    public AdminUserProfileResponse getUserById(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new AdminUserProfileResponse(user, getUserRoles(user.getRoles()));
    }

    @Override
    public AdminUserProfileResponse createUser(AdminUserCreateRequest user) throws ResourceNotFoundException, BadRequestException {
        User createdUser = userRepository.findByEmail(user.getEmail())
                .orElse(new User());

        if (createdUser.isActivated()) {
            throw new BadRequestException("Email already taken");
        }

        createdUser.setName(user.getName());
        createdUser.setSurname(user.getSurname());
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        createdUser.setIsActivated(true);
        createdUser.setRoles(new HashSet<>());
        setUserRoles(user.getRolesIds(), createdUser);

        userRepository.save(createdUser);

        return new AdminUserProfileResponse(createdUser, getUserRoles(createdUser.getRoles()));
    }

    @Override
    public AdminCountriesListResponse createCountry(AdminCountryCreateRequest request) throws BadRequestException {
        Country country = countryRepository
                .findByBothOfNamesIgnoreCase(request.getEnName(), request.getRuName())
                .orElse(new Country());

        if (country.getEnName() != null && country.getRuName() != null) {
            throw new BadRequestException("The country already exists");
        }

        country.setEnName(request.getEnName());
        country.setRuName(request.getRuName());

        countryRepository.save(country);

        return new AdminCountriesListResponse(country);
    }

    @Override
    public AdminCountriesListResponse updateCountry(Long countryId, AdminCountryUpdateRequest request) throws ResourceNotFoundException {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

        country.setEnName(request.getEnName() == null ? country.getEnName() : request.getEnName());
        country.setRuName(request.getRuName() == null ? country.getRuName() : request.getRuName());
        country.setAddedByUser(request.isAddedByUser() == country.isAddedByUser() ? country.isAddedByUser() : request.isAddedByUser());

        countryRepository.save(country);

        return new AdminCountriesListResponse(country);
    }

    @Override
    public AdminCountriesListResponse getCountryById(Long countryId) throws ResourceNotFoundException {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));
        return new AdminCountriesListResponse(country);
    }

    private List<EventsListResponse> makeEventsListResponse(Collection<Event> collection) {
        return collection
                .stream()
                .sorted(Comparator.comparing(Event::getCreatedDate))
                .map(event -> new EventsListResponse(event.getId(), event.getLongitude(), event.getLatitude(),
                        event.getApartment(), event.getTitle(), event.getDate(), event.getLastModifiedDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventsListResponse> getEventsList(Long cityId) throws ResourceNotFoundException {
        if (cityId != null) {
            City eventCity = cityRepository.findById(cityId)
                    .orElseThrow(() -> new ResourceNotFoundException("Provided city not found"));

            return makeEventsListResponse(eventCity.getEvents());
        }
        return makeEventsListResponse(eventRepository.findAll());
    }


    @Override
    public List<AdminCountriesListResponse> getCountriesList() {
        return countryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Country::getId))
                .map(AdminCountriesListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AdminCitiesListResponse> getCitiesListForCountryByCountryId(Long countryId) throws ResourceNotFoundException {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

        return country
                .getCities()
                .stream()
                .sorted(Comparator.comparing(City::getId))
                .map((city) -> new AdminCitiesListResponse(city.getId(), city.getEnName(), city.getRuName(),
                        city.getLongitude(), city.getLatitude(), city.isAddedByUser(), city.getCreatedDate(), city.getLastModifiedDate()))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteCountryById(Long countryId) throws ResourceNotFoundException {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("County not found"));
        countryRepository.delete(country);
    }

    private void setUserRoles(Long [] rolesIds, User user) throws ResourceNotFoundException {
        user.getRoles().clear();
        for(Long roleId : rolesIds) {
            user.getRoles().add(userRoleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found")));
        }
    }

    @Override
    public AdminUserProfileResponse updateUserProfileById(Long id, AdminUserUpdateRequest request)
            throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(request.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        }

        user.setName(request.getName() == null ? user.getName() : request.getName());
        user.setSurname(request.getSurname() == null ? user.getSurname() : request.getSurname());
        user.setEmail(request.getEmail() == null ? user.getEmail() : request.getEmail());

        if(request.getRolesIds() != null && request.getRolesIds().length != 0) {
                setUserRoles(request.getRolesIds(), user);
        }

        userRepository.save(user);
        return new AdminUserProfileResponse(user, getUserRoles(user.getRoles()));
    }

    @Override
    public AdminEventResponse createEvent(AdminEventCreateRequest request)
            throws ResourceNotFoundException {

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        City eventCity = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("Provided city not found"));

        Event createdEvent = new Event();
        createdEvent.setCity(eventCity);
        createdEvent.setApartment(request.getApartment());
        createdEvent.setTitle(request.getTitle());
        createdEvent.setDescription(request.getDescription());
        createdEvent.setDate(request.getDate());
        createdEvent.setCreatedDate(new Date());
        //createdEvent.setModifiedDate(new Date());
        createdEvent.setLongitude(request.getLongitude());
        createdEvent.setLatitude(request.getLatitude());
        createdEvent.setRegistrationRequired(request.isRegistrationRequired());
        createdEvent.setOwner(owner);

        eventRepository.save(createdEvent);

        return new AdminEventResponse(createdEvent, eventRepository.countOfVisitors(createdEvent.getId()));
    }

    @Override
    public  AdminEventResponse getEventById(Long eventId)
            throws  ResourceNotFoundException{
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return new AdminEventResponse(event, eventRepository.countOfVisitors(event.getId()));
    }

    @Override
    public void deleteEventById(Long eventId) throws ResourceNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        eventRepository.delete(event);
    }

    @Override
    public AdminEventResponse updateEvent(Long eventId, AdminEventUpdateRequest request)
            throws ResourceNotFoundException {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        event.setLongitude(request.getLongitude() == null ? event.getLongitude() : request.getLongitude());
        event.setLatitude(request.getLatitude() == null ? event.getLatitude() : request.getLatitude());
        event.setApartment(request.getApartment() == null ? event.getApartment() : request.getApartment());
        event.setTitle(request.getTitle() == null ? event.getTitle() : request.getTitle());
        event.setDescription(request.getDescription() == null ? event.getDescription() : request.getDescription());
        event.setDate(request.getDate() == null ? event.getDate() : request.getDate());
        event.setRegistrationRequired(request.isRegistrationRequired() == null ? event.isRegistrationRequired() : request.isRegistrationRequired());
        event.setOwner(request.getOwnerId() == null ? event.getOwner() :
                userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        event.setCity(request.getCityId() == null ? event.getCity() :
                cityRepository.findById(request.getCityId())
                        .orElseThrow(() -> new ResourceNotFoundException("City not found")));
        //event.setModifiedDate(new Date());

        eventRepository.save(event);

        return new AdminEventResponse(event, eventRepository.countOfVisitors(event.getId()));
    }

    private List<UserRolesResponse> getUserRoles(Collection<UserRole> roles) {
        return roles
                .stream()
                .sorted(Comparator.comparing(UserRole::getId))
                .map(UserRolesResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<UserRolesResponse> getRoles() throws ResourceNotFoundException {
        return getUserRoles(userRoleRepository.findAll());
    }
}
