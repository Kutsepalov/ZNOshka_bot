package com.softserve.webhookbot;

import com.softserve.webhookbot.enumeration.Subject;

import java.util.EnumSet;

public class Specialty {
    private String code;
    private String name;

    private EnumSet<Subject> subjects;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setSpecialties(EnumSet<Subject> subjects) {
        this.subjects = subjects;
    }

    public EnumSet<Subject> getSubjects() {
        return subjects;
    }

}
