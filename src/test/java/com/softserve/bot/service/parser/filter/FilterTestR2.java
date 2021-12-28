package com.softserve.bot.service.parser.filter;

import com.softserve.bot.service.DataProcessor;
import com.softserve.bot.service.Filter;
import com.softserve.bot.service.parser.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilterTestR2 {
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




    @Test
    @DisplayName("getBranchesOfKnowledgeByType1")
    void getBranchesOfKnowledgeByType1() {
    }
    @Test
    @DisplayName("getBranchesOfKnowledgeByType2")
    void getBranchesOfKnowledgeByType2() {
    }
    @Test
    @DisplayName("getBranchesOfKnowledgeByType3")
    void getBranchesOfKnowledgeByType3() {
    }
    @Test
    @DisplayName("getBranchesOfKnowledgeByType4")
    void getBranchesOfKnowledgeByType4() {
    }
    @Test
    @DisplayName("getBranchesOfKnowledgeByType5")
    void getBranchesOfKnowledgeByType5() {
    }

    @Test
    @DisplayName("getSpecialitiesByBranchCode1")
    void getSpecialitiesByBranchCode1() {
    }
    @Test
    @DisplayName("getSpecialitiesByBranchCode2")
    void getSpecialitiesByBranchCode2() {
    }
    @Test
    @DisplayName("getSpecialitiesByBranchCode3")
    void getSpecialitiesByBranchCode3() {
    }
    @Test
    @DisplayName("getSpecialitiesByBranchCode4")
    void getSpecialitiesByBranchCode4() {
    }
    @Test
    @DisplayName("getSpecialitiesByBranchCode5")
    void getSpecialitiesByBranchCode5() {
    }


    @Test
    @DisplayName("getSpecialtyByName6")
    void getSpecialtyByName6() {
    }

    @Test
    @DisplayName("getBranchesByName1")
    void getBranchesByName1() {
    }
    @Test
    @DisplayName("getBranchesByName2")
    void getBranchesByName2() {
    }
    @Test
    @DisplayName("getBranchesByName3")
    void getBranchesByName3() {
    }
    @Test
    @DisplayName("getBranchesByName4")
    void getBranchesByName4() {
    }
    @Test
    @DisplayName("getBranchesByName5")
    void getBranchesByName5() {
    }

    @Test
    @DisplayName("getBranchOfKnowledgeName1")
    void getBranchOfKnowledgeName1() {
    }
    @Test
    @DisplayName("getBranchOfKnowledgeName2")
    void getBranchOfKnowledgeName2() {
    }
    @Test
    @DisplayName("getBranchOfKnowledgeName3")
    void getBranchOfKnowledgeName3() {
    }
    @Test
    @DisplayName("getBranchOfKnowledgeName4")
    void getBranchOfKnowledgeName4() {
    }
    @Test
    @DisplayName("getBranchOfKnowledgeName5")
    void getBranchOfKnowledgeName5() {
    }
}