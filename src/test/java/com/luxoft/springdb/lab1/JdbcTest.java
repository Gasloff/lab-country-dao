package com.luxoft.springdb.lab1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.luxoft.springdb.lab1.repository.CountryRepository;
import com.luxoft.springdb.lab1.model.Country;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Sql({"/test-data.sql"})
public class JdbcTest {

    @Autowired
    private CountryRepository countryRepository;

    private final List<Country> expectedCountryList = new ArrayList<>();
    private final List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    private final Country countryWithChangedName = new Country(1, "Russia", "RU");
    private final Country expectedCountryByName = new Country(2, "France", "FR");

    @Before
    public void setUp() {
        initExpectedCountryLists();
    }


    @Test
    @DirtiesContext
    public void testCountryList() {
        List<Country> countryList = countryRepository.findAll();
        assertNotNull(countryList);
        assertEquals(expectedCountryList.size(), countryList.size());
        for (int i = 0; i < expectedCountryList.size(); i++) {
            assertEquals(expectedCountryList.get(i), countryList.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testCountryListStartsWithA() {
        List<Country> countryList = countryRepository.findCountryByNameStartingWith("A");
        assertNotNull(countryList);
        assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
            assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testCountryByName() {
        assertEquals(expectedCountryByName, countryRepository.findCountryByName("France"));
    }

    @Test
    @DirtiesContext
    public void testCountryChange() {
        countryRepository.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, countryRepository.findCountryByCodeName("RU"));
    }

    private void initExpectedCountryLists() {
        for (int i = 0; i < CountryRepository.COUNTRY_INIT_DATA.length; i++) {
            String[] countryInitData = CountryRepository.COUNTRY_INIT_DATA[i];
            Country country = new Country(i, countryInitData[0], countryInitData[1]);
            expectedCountryList.add(country);
            if (country.getName().startsWith("A")) {
                expectedCountryListStartsWithA.add(country);
            }
        }
    }
}