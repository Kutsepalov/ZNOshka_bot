package com.softserve.bot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private long id;
    @NotNull
    @Column(updatable = false)
    private Timestamp firstActive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Request> requests = new ArrayList<>();
}