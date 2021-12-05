package com.softserve.bot.service;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static Map<String, List<Specialty>> getFiltered(EnumSet<Subject> subjects) {
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

                if (specialtyMatches(specialty, subjects)) {
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

    static boolean specialtyMatches(Specialty specialty, EnumSet<Subject> userSubjects) {
        return firstMatches(specialty, userSubjects) && otherMatch(specialty, userSubjects);
    }

    static boolean firstMatches(Specialty specialty, EnumSet<Subject> userSubjects) {
        return userSubjects.contains(specialty.getFirst()) || userSubjects.contains(Subject.LITERATURE);
    }

    static boolean otherMatch(Specialty specialty, EnumSet<Subject> userSubjects) {
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
}