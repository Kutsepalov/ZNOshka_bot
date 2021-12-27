package com.softserve.bot.controller;

import com.softserve.bot.model.dto.BranchDto;
import com.softserve.bot.model.dto.SpecialtyDto;
import com.softserve.bot.exception.SpecialtyNotFoundException;
import com.softserve.bot.exception.TypeBranchException;
import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.service.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final Filter filter;
    private List<BranchDto> branchDto;
    private List<String> typeBranchList;

    @GetMapping("/{id}/specialties")
    public ResponseEntity<List<BranchDto>> findSpecialtiesByBranchId(@PathVariable String id){
        branchDto = filter.getSpecialitiesByBranchCode(id).stream()
                .map(BranchDto::new)
                .collect(Collectors.toList());
                  return new ResponseEntity<List<BranchDto>>(branchDto, HttpStatus.OK);
    }

    @GetMapping("/{idBranch}/specialties/{idSpecialty}")
    public ResponseEntity<SpecialtyDto> findSpecialtyByBranchIdAndSpecialtyId(@PathVariable String idBranch, @PathVariable String idSpecialty) {
        SpecialtyDto specialtyDTO = filter.getSpecialitiesByBranchCode(idBranch)
                .stream()
                .map(SpecialtyDto::new)
                .filter(specialty -> idSpecialty.equals(specialty.getCode()))
                .findAny()
                .orElseThrow(SpecialtyNotFoundException::new);
        return new ResponseEntity<SpecialtyDto>(specialtyDTO, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<BranchOfKnowledge>> getBranches(){
        return new ResponseEntity<List<BranchOfKnowledge>>(filter.getBranchesOfKnowledge(), HttpStatus.OK);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<String>> getBranchesDividedOnType(@PathVariable String type) {
        if (type.equalsIgnoreCase("hum")) {
            typeBranchList = filter.getBranchesOfKnowledgeByType("Гуманітарні").stream()
                    .map(BranchOfKnowledge::toString)
                    .collect(Collectors.toList());
        } else if (type.equalsIgnoreCase("tech")) {
            typeBranchList = filter.getBranchesOfKnowledgeByType("Технічні").stream()
                    .map(BranchOfKnowledge::toString)
                    .collect(Collectors.toList());
        } else {
            throw new TypeBranchException();
        }
        return new ResponseEntity<>(typeBranchList, HttpStatus.OK);
    }
}
