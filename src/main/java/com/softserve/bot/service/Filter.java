package com.softserve.bot.service;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Maksym Bohachov
 * @see Specialty
 * @see BranchOfKnowledge
 * @see DataProcessor
 */
public class Filter {
    /**
     * Basic list of branches
     */
    private static List<BranchOfKnowledge> branches;

    private Filter() {

    }

    static {
        try {
            branches = DataProcessor.createBranches();
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    /**
     * Filters branches and specialties in relation to input set of subjects
     *
     * @param subjects set of subjects chosen by user
     * @return filtered map branch - specialties, that fits input data
     */
    public static Map<String, List<Specialty>> getFiltered(Set<Subject> subjects) {
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

    public static Map<String, List<Specialty>> getAll() {
        Map<String, List<Specialty>> result = new HashMap<>();

        for (BranchOfKnowledge branch : branches) {
            result.put(branch.toString(), branch.getSpecialties());
        }

        return result;
    }

    static boolean specialtyMatches(Specialty specialty, Set<Subject> userSubjects) {
        return firstMatches(specialty, userSubjects) && otherMatch(specialty, userSubjects);
    }

    static boolean firstMatches(Specialty specialty, Set<Subject> userSubjects) {
        return userSubjects.contains(specialty.getFirst()) || userSubjects.contains(Subject.LITERATURE);
    }

    static boolean otherMatch(Specialty specialty, Set<Subject> userSubjects) {
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

    public static List<BranchOfKnowledge> getBranchesOfKnowledgeByType(String branchType){
        if(branchType.equalsIgnoreCase("Гуманітарні")){
            return new ArrayList<>( branches.subList(0,branches.size() / 2));
        }
        else{
            return branches.subList(branches.size() / 2,branches.size());
        }
    }

    public  static List<Specialty> getSpecialitiesByBranchName(String branchName){
      return  branches.stream()
                .filter(branch -> branch.getTitle().substring(0,4).equals(branchName))
                .map(branch -> branch.getSpecialties())
                .findAny().get();
    }

    public static Specialty getSpecialtyByName(String specialtyCode){
        return  branches.stream()
                .flatMap(branch -> branch.getSpecialties().stream())
                .filter(specialty -> specialty != null && specialty.getCode().equalsIgnoreCase(specialtyCode))
                .findAny().get();
    }

    public static List<BranchOfKnowledge> getBranchesByName(Map<String,List<Specialty>> filteredSpecialty){
        return branches.stream()
                .filter(branch -> filteredSpecialty.containsKey(branch.getTitle()))
                .collect(Collectors.toList());
    }

}