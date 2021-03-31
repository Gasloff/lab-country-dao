package com.luxoft.springdb.lab1;

import com.luxoft.springdb.lab1.model.Country;
import com.luxoft.springdb.lab1.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CountryApplication {

    @Autowired
    private CountryRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(CountryApplication.class);
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < CountryRepository.COUNTRY_INIT_DATA.length; i++) {
            String[] countryInitData = CountryRepository.COUNTRY_INIT_DATA[i];
            Country country = new Country(i, countryInitData[0], countryInitData[1]);
            repository.save(country);
        }
    }
}
