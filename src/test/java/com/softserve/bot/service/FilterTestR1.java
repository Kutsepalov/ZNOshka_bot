package com.softserve.bot.service;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import com.softserve.bot.service.parser.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FilterTestR1 {
    private static DataProcessor dp;
    private static Parser parser=new Parser();

    private final Filter filter= new Filter(dp);
    @BeforeAll
    public static void preInit()
    {

        parser.setCNBranches("src/main/resources/csvs/codenamebranches.csv");
        parser.setCNSpecialties("src/main/resources/csvs/codenamespecialties.csv");
        parser.setLinksToSpecialties("src/main/resources/csvs/linkstospecialties.csv");
        parser.setLinksToUniversities("src/main/resources/csvs/linkstouniversities.csv");
        dp = new DataProcessor(parser);
    }


    FilterTestR1() {
    }

    @Test
    @DisplayName("getFiltered1")
    void getFiltered1() {
        Set<Subject> set = Set.of(Subject.BIOLOGY,Subject.MATH_PROFILE ,Subject.CHEMISTRY,Subject.GEOGRAPHY,Subject.PHYSICS );
        var branches = filter.getFiltered(set);
        Map<String, List<Specialty>> result = new HashMap<>();
        assertEquals(result, branches);
    }
    @Test
    @DisplayName("getFiltered2")
    void getFiltered2throws()  {

        Set<Subject> set = Set.of(Subject.CREATIVE_COMPETITION,Subject.MATH_PROFILE ,Subject.HISTORY,Subject.FOREIGN,Subject.PHYSICS );
        var branches = filter.getFiltered(set);
        Map<String, List<Specialty>> result = new HashMap<>();

        assertEquals(result, branches);

    }
    @Test
    @DisplayName("getFiltered3")
    void getFiltered3()throws NullPointerException {
        Set<Subject> set = Set.of(Subject.LITERATURE,Subject.MATH_PROFILE ,Subject.UKRAINIAN,Subject.FOREIGN,Subject.PHYSICS );

        assertThrows(NullPointerException.class, () -> { filter.getFiltered(set); });


    }
    @Test
    @DisplayName("getFiltered4")
    void getFiltered4() {
      /*  Set<Subject> set = Set.of(Subject.UKRAINIAN,Subject.MATH_PROFILE ,Subject.BIOLOGY,Subject.PHYSICS,Subject.CHEMISTRY );
        var branches = filter.getFiltered(set);
        var result = filter.getFiltered(set);
        assertEquals(result, branches);*/
    }

    @Test
    @DisplayName("getFiltered5")
    void getFiltered5()throws NullPointerException {
        Set<Subject> set = Set.of(Subject.MATH_STANDARD,Subject.MATH_PROFILE ,Subject.PHYSICS,Subject.FOREIGN,Subject.UKRAINIAN );
        assertThrows(NullPointerException.class, () -> { filter.getFiltered(set); });
    }

    @Test
    @DisplayName("getAll1")
    void getAll1() {
        /*var branchess = filter.getAll();

        Map<String, List<Specialty>> result = new HashMap<>();
         List<BranchOfKnowledge> branches=dp.createBranches();
        for (BranchOfKnowledge branch : branches) {
            result.put(branch.toString(), branch.getSpecialties());
        }
        assertEquals(result, branchess);*/
    }
    @Test
    @DisplayName("getAll2")
    void getAll2() {

    }
    @Test
    @DisplayName("getAll3")
    void getAll3() {

    }
    @Test
    @DisplayName("getAll4")
    void getAll4() {

    }
    @Test
    @DisplayName("getAll5")
    void getAll5() {

    }

}