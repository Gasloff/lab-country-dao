/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right name only.
 */
package com.luxoft.springdb.lab1.rest;


import com.luxoft.springdb.lab1.model.Country;

/**
 * DTO that represents Account
 */
@SuppressWarnings("all")
public class CountryDto {

    private int id = -1;
    private String name;
    private String codeName;

    public CountryDto() {
    }

    public CountryDto(int id, String name, String codeName) {
        this.id = id;
        this.name = name;
        this.codeName = codeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static Country toDomainObject(CountryDto dto) {
        return new Country(dto.getId(), dto.getName(), dto.getCodeName());
    }

    public static CountryDto toDto(Country account) {
        return new CountryDto(account.getId(), account.getName(), account.getCodeName());
    }
}
