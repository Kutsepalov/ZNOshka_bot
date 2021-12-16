package com.softserve.bot.controller;

import com.softserve.bot.dto.BranchDto;
import com.softserve.bot.service.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
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
}
