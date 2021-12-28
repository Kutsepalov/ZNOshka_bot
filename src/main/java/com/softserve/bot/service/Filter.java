package com.softserve.bot.service;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import com.softserve.bot.model.TypeOfBranch;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Maksym Bohachov
 * @updated_by Roman Grabovetskiy
 * @tested_by Roman Grabovetskiy,Igor Gladush
 * @see Specialty
 * @see BranchOfKnowledge
 * @see DataProcessor
 */
@Component
public class Filter {
    /**
     * Basic list of branches
     */
    private List<BranchOfKnowledge> branches;
    private DataProcessor dataProcessor;

    public Filter(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    @PostConstruct
    private void fillBranches() {
        branches = dataProcessor.createBranches();
    }

    /**
     * Filters branches and specialties in relation to input set of subjects
     *
     * @param subjects set of subjects chosen by user
     * @return filtered map branch - specialties, that fits input data
     */
    public Map<String, List<Specialty>> getFiltered(Set<Subject> subjects) {
        Map<String, List<Specialty>> result = new HashMap<>();

        if (subjects.stream().noneMatch(s -> s.getPriority() > 1)) {
            return result;
        }

        Set<Map.Entry<String, List<Specialty>>> base = getAll().entrySet();

        for (Map.Entry<String, List<Specialty>> entry : base) {
            String branchName = entry.getKey();

            List<Specialty> input = entry.getValue();

            List<Specialty> output = new ArrayList<>();

            for (Specialty specialty : input) {

                if (specialty != null && specialtyMatches(specialty, subjects)) {
                    output.add(specialty);
                }
            }

            if (!output.isEmpty()) {
                result.put(branchName, output);
            }
        }

        return result;
    }

    public Map<String, List<Specialty>> getAll() {
        Map<String, List<Specialty>> result = new HashMap<>();

        for (BranchOfKnowledge branch : branches) {
            result.put(branch.toString(), branch.getSpecialties());
        }

        return result;
    }

    private boolean specialtyMatches(Specialty specialty, Set<Subject> userSubjects) {
        return firstMatches(specialty, userSubjects) && otherMatch(specialty, userSubjects);
    }

    private boolean firstMatches(Specialty specialty, Set<Subject> userSubjects) {
        return userSubjects.contains(specialty.getFirst()) || userSubjects.contains(Subject.LITERATURE);
    }

    private boolean otherMatch(Specialty specialty, Set<Subject> userSubjects) {
        return specialty.getSecond()
                .stream()
                .anyMatch(v -> userSubjects
                        .stream()
                        .anyMatch(k -> v == k || v.getPriority() == 1 && k.getPriority() == 1)) &&
                specialty.getThird()
                        .stream()
                        .anyMatch(v -> userSubjects
                                .stream()
                                .anyMatch(k -> v == k || v.getPriority() == 1 && k.getPriority() == 1));
    }

    public List<BranchOfKnowledge> getBranchesOfKnowledgeByType(String branchType) {
        final TypeOfBranch typeOfBranch;
        if (branchType.equalsIgnoreCase(TypeOfBranch.HUMANITIES.getDescription())) {
            typeOfBranch = TypeOfBranch.HUMANITIES;
        } else {
            typeOfBranch = TypeOfBranch.TECHNICAL;
        }
        return branches.stream()
                .filter(branchOfKnowledge -> branchOfKnowledge.getTypeOfBranch().equals(typeOfBranch))
                .collect(Collectors.toList());
    }

    public List<Specialty> getSpecialitiesByBranchCode(String branchCode) {
        return branches.stream()
                .filter(branch -> branch.getCode().equals(branchCode))
                .map(BranchOfKnowledge::getSpecialties)
                .findAny().get();//TODO - it's not good to use get without isPresent()
    }

    public Specialty getSpecialtyByName(String specialtyCode) {
        return branches.stream()
                .flatMap(branch -> branch.getSpecialties().stream())
                .filter(specialty -> specialty != null && specialty.getCode().equalsIgnoreCase(specialtyCode))
                .findAny().get();//TODO - it's not good to use get without isPresent()
    }

    public List<BranchOfKnowledge> getBranchesByName(Map<String, List<Specialty>> filteredSpecialty) {
        return branches.stream()
                .filter(branch -> filteredSpecialty.containsKey(branch.toString()))
                .collect(Collectors.toList());
    }

    public String getBranchOfKnowledgeName(String branchName) {
        return branches.stream()
                .filter(branch -> branch.getCode().equals(branchName))
                .map(BranchOfKnowledge::getTitle)
                .findAny()
                .get();//TODO - it's not good to use get without isPresent()
    }

    public List<BranchOfKnowledge> getBranchesOfKnowledge() {
        return branches;
    }
}