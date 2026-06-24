package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.repository.LocationRepository;
import com.mycompany.myapp.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Location}.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private static final Logger LOG = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Mono<Location> save(Location location) {
        LOG.debug("Request to save Location : {}", location);
        return locationRepository.save(location);
    }

    @Override
    public Mono<Location> update(Location location) {
        LOG.debug("Request to update Location : {}", location);
        return locationRepository.save(location);
    }

    @Override
    public Mono<Location> partialUpdate(Location location) {
        LOG.debug("Request to partially update Location : {}", location);

        return locationRepository
            .findById(location.getId())
            .map(existingLocation -> {
                if (location.getStreetAddress() != null) {
                    existingLocation.setStreetAddress(location.getStreetAddress());
                }
                if (location.getPostalCode() != null) {
                    existingLocation.setPostalCode(location.getPostalCode());
                }
                if (location.getCity() != null) {
                    existingLocation.setCity(location.getCity());
                }
                if (location.getStateProvince() != null) {
                    existingLocation.setStateProvince(location.getStateProvince());
                }

                return existingLocation;
            })
            .flatMap(locationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Location> findAll() {
        LOG.debug("Request to get all Locations");
        return locationRepository.findAll();
    }

    /**
     *  Get all the locations where Department is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Location> findAllWhereDepartmentIsNull() {
        LOG.debug("Request to get all locations where Department is null");
        return locationRepository.findAllWhereDepartmentIsNull();
    }

    public Mono<Long> countAll() {
        return locationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Location> findOne(Long id) {
        LOG.debug("Request to get Location : {}", id);
        return locationRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Location : {}", id);
        return locationRepository.deleteById(id);
    }
}
