package com.softserve.bot.service.repository;

import java.util.ArrayList;
import java.util.List;

public class Specialty {
    private String name;
    private String code;
    private String first;
    private List<String> second;
    private List<String> third;

    public Specialty() {
        second = new ArrayList<>();
        third = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public List<String> getSecond() {
        return second;
    }

    public void setSecond(List<String> second) {
        this.second = second;
    }

    public List<String> getThird() {
        return third;
    }

    public void setThird(List<String> third) {
        this.third = third;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

        @Override
    public String toString() {
        return "Specialty{" +
                "name='"+ code + "-" + name + '\'' +
                ", first='" + first + '\'' +
                ", second=" + second +
                ", third=" + third +
                '}';
    }


}
