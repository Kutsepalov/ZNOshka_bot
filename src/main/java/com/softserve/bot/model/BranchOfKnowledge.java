package com.softserve.bot.model;

import java.util.List;

public class BranchOfKnowledge {
    private String title;
    private String code;
    private List<Specialty> specialties;
    private TypeOfBranch typeOfBranch;

    public BranchOfKnowledge(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public BranchOfKnowledge() {
    }

    public TypeOfBranch getTypeOfBranch() {
        return typeOfBranch;
    }

    public void setTypeOfBranch(TypeOfBranch typeOfBranch) {
        this.typeOfBranch = typeOfBranch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    @Override
    public String toString() {
        return code + "-" + title;
    }
}
