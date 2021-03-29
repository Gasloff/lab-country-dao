package com.luxoft.springdb.lab1.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.luxoft.springdb.lab1.model.Country;

public class CountryDao {
	private static final String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like :name";
	private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "select * from country where code_name = '";

	public static final String[][] COUNTRY_INIT_DATA = { { "Australia", "AU" },
			{ "Canada", "CA" }, { "France", "FR" }, { "Hong Kong", "HK" },
			{ "Iceland", "IC" }, { "Japan", "JP" }, { "Nepal", "NP" },
			{ "Russian Federation", "RU" }, { "Sweden", "SE" },
			{ "Switzerland", "CH" }, { "United Kingdom", "GB" },
			{ "United States", "US" } };

	private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

	private final NamedParameterJdbcOperations namedJdbc;
	private final JdbcOperations jdbc;

	public CountryDao( NamedParameterJdbcOperations namedJdbc) {
		this.namedJdbc = namedJdbc;
		this.jdbc = namedJdbc.getJdbcOperations();
	}

	public List<Country> getCountryList() {
		return jdbc.query("select * from country", COUNTRY_ROW_MAPPER);
	}

	public List<Country> getCountryListStartWith(String name) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
				"name", name + "%");
		return namedJdbc.query(GET_COUNTRIES_BY_NAME_SQL,
				sqlParameterSource, COUNTRY_ROW_MAPPER);
	}

	public void updateCountryName(String codeName, String newCountryName) {
		Map<String, Object> params = new HashMap<>();
		params.put("codeName", codeName);
		params.put("newCountryName", newCountryName);
		namedJdbc.update("update country SET name=:newCountryName where code_name=:codeName", params);
	}

	public Country getCountryByCodeName(String codeName) {
		String sql = GET_COUNTRY_BY_CODE_NAME_SQL + codeName + "'";
//		System.out.println(sql);

		return jdbc.query(sql, COUNTRY_ROW_MAPPER).get(0);
	}

	public Country getCountryByName(String name)
			throws CountryNotFoundException {
		Map<String, Object> params = Collections.singletonMap("name", name);
		List<Country> countryList = namedJdbc.query("select * from country where name = :name",
				params, COUNTRY_ROW_MAPPER);
		if (countryList.isEmpty()) {
			throw new CountryNotFoundException();
		}
		return countryList.get(0);
	}
}
