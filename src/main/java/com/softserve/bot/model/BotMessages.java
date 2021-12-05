package com.softserve.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BotMessages {

    @JsonProperty("start")
    private String start;
    @JsonProperty("rules")
    private String rules;
    @JsonProperty("contacts")
    private String contacts;
    @JsonProperty("show-menu")
    private String showMenu;
    @JsonProperty("all-subjects")
    private String allSubjects;
    @JsonProperty("not-enough")
    private String notEnoughAlert;
    @JsonProperty("too-many")
    private String tooManyAlert;
    @JsonProperty("find-all")
    private String findAll;
    @JsonProperty("remove-all")
    private String removeAll;
    @JsonProperty("error")
    private String error;
    @JsonProperty("tick")
    private String tickMark;
    @JsonProperty("find")
    private String findMark;
    @JsonProperty("out-of-limit")
    private String outOfLimitMark;
    @JsonProperty("separator")
    private String separator;
    @JsonProperty("search")
    private String searchData;
    @JsonProperty("delete")
    private String deleteData;
    @JsonProperty("menu")
    private String menuData;
    @JsonProperty("choose-subject")
    private String chooseSubjectMenu;
    @JsonProperty("show-specialties")
    private String showSpecialtiesMenu;
    @JsonProperty("rules-menu")
    private String rulesMenu;
    @JsonProperty("contacts-menu")
    private String contactsMenu;
}
