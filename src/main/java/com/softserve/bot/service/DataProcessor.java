package com.softserve.bot.service;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.service.parser.Parser;
import com.softserve.bot.service.parser.SpecialtyToSubject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataProcessor {
    private Parser parser;

    public List<BranchOfKnowledge> createBranches() {
        SpecialtyToSubject sts = parser.doParse();
        List<BranchOfKnowledge> branchesOfKnowledge = new ArrayList<>();
        List<String> ciphers = new ArrayList<>(sts.getDomainIdToName().keySet());
        ciphers.forEach(x -> {
            BranchOfKnowledge branchOfKnowledge = new BranchOfKnowledge();
            branchOfKnowledge.setTitle(sts.getDomainIdToName().get(x));
            branchOfKnowledge.setTypeOfBranch(sts.getDomainIdToType().get(x));
            branchOfKnowledge.setCode(x);
            List<Specialty> specialties = new ArrayList<>();
            sts.getDomainIdToSpecialtyId().get(x)
                    .forEach(y -> specialties.add(sts.getSpecialtyIdToName().get(y)));
            branchOfKnowledge.setSpecialties(specialties);
            branchesOfKnowledge.add(branchOfKnowledge);
        });
        return branchesOfKnowledge;
    }

}
