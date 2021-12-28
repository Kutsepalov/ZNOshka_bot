package com.softserve.bot.service;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.service.parser.Parser;
import com.softserve.bot.service.parser.SpecialtyToSubject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
        ciphers.forEach(cipher -> {
            BranchOfKnowledge branchOfKnowledge = new BranchOfKnowledge();
            branchOfKnowledge.setTitle(sts.getDomainIdToName().get(cipher));
            branchOfKnowledge.setTypeOfBranch(sts.getDomainIdToType().get(cipher));
            branchOfKnowledge.setCode(cipher);
            List<Specialty> specialties = new ArrayList<>();
            sts.getDomainIdToSpecialtyId().get(cipher)
                    .forEach(y -> specialties.add(sts.getSpecialtyIdToName().get(y)));
            branchOfKnowledge.setSpecialties(specialties);
            branchesOfKnowledge.add(branchOfKnowledge);
        });
        return branchesOfKnowledge;
    }

}
