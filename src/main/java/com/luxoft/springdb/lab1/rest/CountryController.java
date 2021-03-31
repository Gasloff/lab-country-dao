package com.luxoft.springdb.lab1.rest;

import com.luxoft.springdb.lab1.exception.CountryNotFoundException;
import com.luxoft.springdb.lab1.model.Country;
import com.luxoft.springdb.lab1.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryController {

    private final CountryRepository repository;

    @Autowired
    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("api/all")
    public List<CountryDto> getAll() {
        return repository.findAll()
                .stream()
                .map(CountryDto::toDto)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("api/find/id")
    public CountryDto getById(@RequestParam int id) {
        Country country = repository.findCountryById(id);
        if (country == null)
            throw new CountryNotFoundException("Country with id = " + id + " not found");

        return CountryDto.toDto(country);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/save")
    public CountryDto save(@RequestBody CountryDto toSave) {
        Country newCountry = CountryDto.toDomainObject(toSave);
        return CountryDto.toDto(repository.save(newCountry));
    }

    @PutMapping("api/update/id")
    public CountryDto updateById(@RequestParam int id, @RequestParam String name) {
        Country country = repository.findCountryById(id);
        if (country == null)
            throw new CountryNotFoundException("Country with id = " + id + " not found");

        country.setName(name);
        repository.save(country);
        return CountryDto.toDto(country);
    }

    @DeleteMapping("api/delete/id")
    public CountryDto deleteById(@RequestParam int id) {
        Country country = repository.findCountryById(id);
        if (country == null)
            throw new CountryNotFoundException("Country with id = " + id + " not found");

        repository.delete(country);
        return CountryDto.toDto(country);
    }
}
