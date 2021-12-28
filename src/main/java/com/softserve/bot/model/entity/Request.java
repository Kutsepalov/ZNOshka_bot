package com.softserve.bot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Request {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private long setSubjects;
    @NotNull
    private Timestamp datetime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void setUser(User user) {
        this.user = user;
        if(user.getRequests() == null) {
            user.setRequests(new ArrayList<>());
        }
        user.getRequests().add(this);
    }
}