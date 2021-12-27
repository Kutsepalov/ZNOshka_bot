package com.softserve.bot.model.dto;

import com.softserve.bot.model.Specialty;

public class SpecialtyDto {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public SpecialtyDto(Specialty specialty) {
        this.name = specialty.getName();
        this.code = specialty.getCode();
    }
}
