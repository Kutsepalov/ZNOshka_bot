package com.softserve.bot.dto;

import com.softserve.bot.model.Subject;

public class SubjectDTO {
    private String name;

    public String getName() {
        return name;
    }

    public SubjectDTO(Subject subject) {
        this.name = subject.getName();
    }
}
