package com.softserve.bot.service;

import com.softserve.bot.model.Subject;
import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * @author Maksym Bohachov
 * @see Specialty
 * @see BranchOfKnowledge
 * @see DataProcessor
 */
@Component
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
                var current = specialty.getSubjects();

                // Check for matching first subject
                if(subjects.stream().anyMatch(s -> s.equals(Subject.UKRAINIAN)) && current.stream().anyMatch(s -> s.equals(Subject.LITERATURE))) {
                    continue;
                }

                // Check for matching foreign language if such exists
                if (isThereForeign.test(current) && !isThereForeign.test(subjects)) {
                    continue;
                }

                // Filters by whether subjects are equal, matching first or foreign language
                var count = subjects.stream()
                        .filter(
                                subject1 -> current.stream().anyMatch(subject2 -> matches.test(subject1, subject2)) ||
                                        current.stream().anyMatch(subject2 -> firstMatches.test(subject1, subject2)) ||
                                        current.stream().anyMatch(subject2 -> foreignMatches.test(subject1, subject2))
                        ).count();

                if (count > 3) {
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

//    private static BiPredicate<EnumSet<Subject>, EnumSet<Subject>> firstMatches = (specialtySubjects, userSubjects) ->
//            userSubjects.stream().anyMatch(
//                    s -> s.getPriority() > 1 && specialtySubjects.stream().allMatch(v -> v.getPriority() <= s.getPriority())
//            );

    static Predicate<EnumSet<Subject>> isThereForeign = subjects -> subjects.stream().anyMatch(s -> s.getPriority() == 1);

    static BiPredicate<Subject, Subject> firstMatches =
            (userSubject, specialtySubject) ->
                    specialtySubject.getPriority() > 1 && userSubject.getPriority() >= specialtySubject.getPriority();
    static BiPredicate<Subject, Subject> foreignMatches =
            (userSubject, specialtySubject) ->
                    userSubject.getPriority() == 1 && userSubject.getPriority() == specialtySubject.getPriority();
    static BiPredicate<Subject, Subject> matches = (t, u) -> t == u;
}
