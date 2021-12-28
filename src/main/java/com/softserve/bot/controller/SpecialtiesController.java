package com.softserve.bot.controller;

import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import com.softserve.bot.service.Filter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping(
    value = "/specialties",
    produces = "application/json"
)
@AllArgsConstructor
public class SpecialtiesController {
    private final Filter filter;

    @GetMapping
    public Map<String, List<Specialty>> findBySubjects(
            @RequestParam(value = "code")
            String[] subjects
    ) {
        Set<Subject> set = Stream.of(subjects)
                .map(s -> Subject.valueOf(s.toUpperCase()))
                .collect(Collectors.toSet());
        return filter.getFiltered(set);
    }
}
