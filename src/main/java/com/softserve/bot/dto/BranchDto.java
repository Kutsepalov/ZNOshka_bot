package com.softserve.bot.dto;

import com.softserve.bot.model.Specialty;
import org.springframework.stereotype.Component;


public class BranchDto {
    private String nameAndCode;

    public String getNameAndCode() {
        return nameAndCode;
    }

    public BranchDto(Specialty specialty) {
        this.nameAndCode = specialty.getCode()+"-"+specialty.getName();
    }
}
