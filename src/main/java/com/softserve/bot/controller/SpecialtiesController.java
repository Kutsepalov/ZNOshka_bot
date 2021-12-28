package com.softserve.bot.controller;

import com.softserve.bot.exception.TypeBranchException;
import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import com.softserve.bot.service.Filter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private List<BranchOfKnowledge> branchList;
    private List<Specialty> specialties;
    private Map<String,List<BranchOfKnowledge>> mapOfBranches;

    @GetMapping
    public ResponseEntity<Map<String, List<BranchOfKnowledge>>> getAllSpecialty(){
        mapOfBranches.put("Гуманітарні",filter.getBranchesOfKnowledgeByType("Гуманітарні"));
        mapOfBranches.put("Технічні",filter.getBranchesOfKnowledgeByType("Технічні"));
        return new ResponseEntity<>(mapOfBranches, HttpStatus.OK);
}

    @GetMapping("/direction")
    public ResponseEntity<List<BranchOfKnowledge>> getBranchesDividedOnType(@RequestParam(value = "type") String type) {
        if (type.equalsIgnoreCase("hum")) {
            branchList = filter.getBranchesOfKnowledgeByType("Гуманітарні");
        } else if (type.equalsIgnoreCase("tech")) {
            branchList = filter.getBranchesOfKnowledgeByType("Технічні");
        } else {
            throw new TypeBranchException();
        }
        return new ResponseEntity<>(branchList, HttpStatus.OK);
    }

    @GetMapping("/branch")
    public ResponseEntity<List<Specialty>> findSpecialtiesByBranchId(@RequestParam(value = "code") String branchCode) {
        specialties = filter.getSpecialitiesByBranchCode(branchCode);
        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }

    @GetMapping("/branch/specialty")
    public ResponseEntity<Specialty> findSpecialtiesBySpecialtyId(@RequestParam(value = "code") String specialtyCode) {
    Specialty specialty =  filter.getSpecialtyByName(specialtyCode);
        return new ResponseEntity<>(specialty, HttpStatus.OK);
    }

}



