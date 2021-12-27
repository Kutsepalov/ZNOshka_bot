package com.softserve.bot.model.dto;

import com.softserve.bot.model.Subject;

public class SubjectDto {
    private String name;

    public String getName() {
        return name;
    }

    public SubjectDto(Subject subject) {
        this.name = subject.getName();
    }
}
