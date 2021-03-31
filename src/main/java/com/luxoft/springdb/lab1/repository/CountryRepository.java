package com.luxoft.springdb.lab1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxoft.springdb.lab1.model.Country;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

	String[][] COUNTRY_INIT_DATA = { { "Australia", "AU" },
			{ "Canada", "CA" }, { "France", "FR" }, { "Hong Kong", "HK" },
			{ "Iceland", "IC" }, { "Japan", "JP" }, { "Nepal", "NP" },
			{ "Russian Federation", "RU" }, { "Sweden", "SE" },
			{ "Switzerland", "CH" }, { "United Kingdom", "GB" },
			{ "United States", "US" } };

	List<Country> findAll();

	List<Country> findCountryByNameStartingWith(String name);

	@Modifying
	@Transactional
	@Query("update Country c set c.name = :name where c.codeName = :code")
	void updateCountryName(@Param(value = "code") String codeName, @Param(value = "name") String newCountryName);

	Country findCountryByCodeName(String codeName);

	Country findCountryByName(String name);

	Country findCountryById(int id);
}
