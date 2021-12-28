package com.softserve.bot.model;

public enum TypeOfBranch {
    TECHNICAL("Технічні"),
    HUMANITIES("Гуманітарні");

    private final String description;
    private  String code;

    TypeOfBranch(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
