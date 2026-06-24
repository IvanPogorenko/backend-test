package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.repository.CountryRepository;
import com.mycompany.myapp.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Country}.
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private static final Logger LOG = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Mono<Country> save(Country country) {
        LOG.debug("Request to save Country : {}", country);
        return countryRepository.save(country);
    }

    @Override
    public Mono<Country> update(Country country) {
        LOG.debug("Request to update Country : {}", country);
        return countryRepository.save(country);
    }

    @Override
    public Mono<Country> partialUpdate(Country country) {
        LOG.debug("Request to partially update Country : {}", country);

        return countryRepository
            .findById(country.getId())
            .map(existingCountry -> {
                if (country.getCountryName() != null) {
                    existingCountry.setCountryName(country.getCountryName());
                }

                return existingCountry;
            })
            .flatMap(countryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Country> findAll() {
        LOG.debug("Request to get all Countries");
        return countryRepository.findAll();
    }

    /**
     *  Get all the countries where Location is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Country> findAllWhereLocationIsNull() {
        LOG.debug("Request to get all countries where Location is null");
        return countryRepository.findAllWhereLocationIsNull();
    }

    public Mono<Long> countAll() {
        return countryRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Country> findOne(Long id) {
        LOG.debug("Request to get Country : {}", id);
        return countryRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Country : {}", id);
        return countryRepository.deleteById(id);
    }
}
