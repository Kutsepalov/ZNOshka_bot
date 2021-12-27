package com.softserve.bot.model.dto;

import com.softserve.bot.model.Specialty;


public class BranchDto {
    private String nameAndCode;

    public String getNameAndCode() {
        return nameAndCode;
    }

    public BranchDto(Specialty specialty) {
        this.nameAndCode = specialty.getCode()+"-"+specialty.getName();
    }
}
