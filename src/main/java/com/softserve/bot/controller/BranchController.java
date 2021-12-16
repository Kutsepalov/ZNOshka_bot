package com.softserve.bot.controller;

import com.softserve.bot.dto.BranchDto;
import com.softserve.bot.dto.SpecialtyDTO;
import com.softserve.bot.exception.BranchControllerException;
import com.softserve.bot.exception.SpecialtyNotFoundException;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.service.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;
import javax.ws.rs.ext.ParamConverter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final Filter filter;
    private List<BranchDto> branchDto;

    @GetMapping("/{id}/specialties")
    public ResponseEntity<List<BranchDto>> findSpecialtiesByBranchId(@PathVariable String id){
        branchDto = filter.getSpecialitiesByBranchCode(id).stream()
                .map(BranchDto::new)
                .collect(Collectors.toList());
                  return new ResponseEntity<List<BranchDto>>(branchDto, HttpStatus.OK);
    }

    @GetMapping("/{idBranch}/specialties/{idSpecialty}")
    public ResponseEntity<SpecialtyDTO> findSpecialtyByBranchIdAndSpecialtyId(@PathVariable String idBranch, @PathVariable String idSpecialty) {
        SpecialtyDTO specialtyDTO = filter.getSpecialitiesByBranchCode(idBranch)
                .stream()
                .map(SpecialtyDTO::new)
                .filter(specialty -> idSpecialty.equals(specialty.getCode()))
                .findAny()
                .orElseThrow(SpecialtyNotFoundException::new);
        return new ResponseEntity<SpecialtyDTO>(specialtyDTO, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<BranchOfKnowledge>> getBranches(){
        return new ResponseEntity<List<BranchOfKnowledge>>(filter.getBranchesOfKnowledge(), HttpStatus.OK);
    }
}
