package com.softserve.bot.dto;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.service.DataProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
@AllArgsConstructor
@Component
public class BranchDTO {
    private DataProcessor dataProcessor;
    private List<String> branches;

    private void setBranches(){
        for (BranchOfKnowledge branch : dataProcessor.createBranches()) {
            branches.add(""+branch.getCode()+"-"+branch.getTitle());
        }
    }

    public List<String> getBranches() throws Exception {
        setBranches();
        if(branches.isEmpty()){
            throw new Exception();
        }
        return branches;
    }

    public String getBranchesById(String id) throws Exception {
        for (BranchOfKnowledge branch : dataProcessor.createBranches()) {
            if (branch.getCode().equals(id)){
                return branch.getTitle();
            }
        }
        throw new Exception();
    }
}
