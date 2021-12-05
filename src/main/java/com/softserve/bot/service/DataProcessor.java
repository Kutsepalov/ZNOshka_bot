package com.softserve.bot.service;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.service.parser.Parser;
import com.softserve.bot.service.parser.SpecialtyToSubject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DataProcessor {

    public static List<BranchOfKnowledge> createBranches() throws IOException {
        SpecialtyToSubject sts = new Parser().doParse();
        List<BranchOfKnowledge> branchesOfKnowledge = new ArrayList<>();
        List<String> ciphers = new ArrayList<>(sts.getDomainIdToName().keySet());
        ciphers.forEach(x -> {
            BranchOfKnowledge branchOfKnowledge = new BranchOfKnowledge();
            branchOfKnowledge.setTitle(sts.getDomainIdToName().get(x));
            branchOfKnowledge.setCode(x);
            List<Specialty> specialties = new ArrayList<>();
            sts.getDomainIdToSpecialtyId().get(x)
                    .forEach(y -> specialties.add(sts.getSpecialtyIdToName().get(y)));
            branchOfKnowledge.setSpecialties(specialties);
            branchesOfKnowledge.add(branchOfKnowledge);
        });
        return branchesOfKnowledge;
    }

    public static List<Specialty> createSpecialties() throws IOException {
        SpecialtyToSubject sts = new Parser().doParse();
        List<Specialty> specialtyList = new ArrayList<>();
        List<String> codes = new ArrayList<>(sts.getSpecialtyIdToName().keySet());
        codes.forEach(x -> {
            Specialty specialty = new Specialty();
            specialty.setCode(x);
            specialty.setName(sts.getSpecialtyIdToName().get(x).getName());
            specialty.setFirst(sts.getSpecialtyIdToName().get(x).getFirst());
            specialty.setSecond(sts.getSpecialtyIdToName().get(x).getSecond());
            specialty.setThird(sts.getSpecialtyIdToName().get(x).getThird());
            specialtyList.add(specialty);
        });
        return specialtyList;
    }


    public static List<List<Specialty>> getSpecialtiesByBranchTitle(String title) throws IOException {
        return createBranches().stream()
                .filter(branch -> branch.getTitle().equals(title))
                .map(BranchOfKnowledge::getSpecialties)
                .collect(Collectors.toList());
    }

    public static List<List<Specialty>> getSpecialtiesByBranchCode(String code) throws IOException {
        return createBranches().stream()
                .filter(branch -> branch.getCode().equals(code))
                .map(BranchOfKnowledge::getSpecialties)
                .collect(Collectors.toList());
    }

    public static List<String> getSpecialtyNames() throws IOException {
        return createSpecialties().stream()
                .map(x -> x.getCode() + "-" + x.getName())
                .collect(Collectors.toList());
    }

    public static Map<String, List<String>> getBranchesWithSpec() throws IOException {
        return createBranches().stream()
                .collect(Collectors.toMap(
                        p -> p.getCode() + "-" + p.getTitle(),
                        p -> p.getSpecialties().stream()
                                .map(x -> x.getCode() + "-" + x.getName())
                                .collect(Collectors.toList()),
                        (l1, l2) -> l1,
                        LinkedHashMap::new
                ));
    }

}
