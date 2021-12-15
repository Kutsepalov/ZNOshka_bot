package com.softserve.bot.controller;

import com.softserve.bot.dto.BranchDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/branches")
@AllArgsConstructor
public class BranchController {
    private BranchDTO branchDTO;

    @GetMapping
    public ResponseEntity<List<String>> findBranches() {
        try {
            List<String> list = branchDTO.getBranches();
            return new ResponseEntity<List<String>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findBranchById(@PathVariable  String id) {
        try {
             String branch = branchDTO.getBranchesById(id);
            return new ResponseEntity<String>(branch, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

}
