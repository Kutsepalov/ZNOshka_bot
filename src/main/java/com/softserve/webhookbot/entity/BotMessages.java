package com.softserve.webhookbot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BotMessages {

    @JsonProperty("start")
    private String start = "";
    @JsonProperty("rules")
    private String rules = "";
    @JsonProperty("contacts")
    private String contacts = "";
}
