package com.softserve.bot.dto;

import com.softserve.bot.model.Specialty;

public class SpecialtyDTO {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public SpecialtyDTO(Specialty specialty) {
        this.name = specialty.getName();
        this.code = specialty.getCode();
    }
}
