package com.softserve.bot.service.repository;

import com.softserve.webhookbot.enumeration.Subject;

import java.util.ArrayList;
import java.util.List;

public class Specialty {
    private String name;
    private String code;
    private Subject first;
    private List<Subject> second;
    private List<Subject> third;

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

    public Subject getFirst() {
        return first;
    }

    public void setFirst(Subject first) {
        this.first = first;
    }

    public List<Subject> getSecond() {
        return second;
    }

    public void setSecond(List<Subject> second) {
        this.second = second;
    }

    public List<Subject> getThird() {
        return third;
    }

    public void setThird(List<Subject> third) {
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
                "name='" + name + '\'' +
                ", first='" + first + '\'' +
                ", second=" + second +
                ", third=" + third +
                '}';
    }
}
