package com.softserve.bot.controller;

import com.softserve.bot.exception.SubjectRestrictionException;
import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import com.softserve.bot.model.dto.SubjectDto;
import com.softserve.bot.service.Filter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping(
        value = "/subjects",
        produces = "application/json"
)
@AllArgsConstructor
public class SubjectController {
    private final Filter filter;


    @GetMapping
    public List<SubjectDto> getAllSubjects() {
        return Stream.of(Subject.values())
                .filter(x -> x != Subject.FOREIGN)
                .map(SubjectDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/choice")
    public Map<String, List<Specialty>> findBySubjects(@RequestParam(value = "subj") String[] subjects) {
        if (subjects.length < 3 || subjects.length > 5) {
            throw new SubjectRestrictionException();
        }
        Set<Subject> set = Stream.of(subjects)
                .map(s -> Subject.valueOf(s.toUpperCase()))
                .collect(Collectors.toSet());
        return filter.getFiltered(set);
    }
}
