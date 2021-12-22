package com.softserve.bot.service.parser.filter;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.service.DataProcessor;
import com.softserve.bot.service.Filter;
import com.softserve.bot.service.parser.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilterTestR3 {
    private static DataProcessor dp;
    private static Parser parser=new Parser();

    private Filter filter= new Filter(dp);
    @BeforeAll
    public static void preInit()
    {

        parser.setCNBranches("src/main/resources/csvs/codenamebranches.csv");
        parser.setCNSpecialties("src/main/resources/csvs/codenamespecialties.csv");
        parser.setLinksToSpecialties("src/main/resources/csvs/linkstospecialties.csv");
        parser.setLinksToUniversities("src/main/resources/csvs/linkstouniversities.csv");
        dp = new DataProcessor(parser);
    }


    FilterTestR3() {
    }

    @Test
    @DisplayName("Check Filter.getBranchOfKnowledgeName with Біологія")
    void getBranchOfKnowledgeName1() {
        filter = new Filter(dp);
        List<BranchOfKnowledge> branches = dp.createBranches();

        String result = filter.getBranchOfKnowledgeName("Біологія");

        assertEquals("Біологія", result);
    }

    @Test
    @DisplayName("Check Filter.getBranchOfKnowledgeName with Природничі науки")
    void getBranchOfKnowledgeName2() {
        filter = new Filter(dp);
        List<BranchOfKnowledge> branches = dp.createBranches();

        String result = filter.getBranchOfKnowledgeName("Природничі науки");

        assertEquals("Природничі науки", result);
    }

    @Test
    @DisplayName("Check Filter.getBranchOfKnowledgeName with Автоматизація та приладобудування")
    void getBranchOfKnowledgeName3() {
        filter = new Filter(dp);
        List<BranchOfKnowledge> branches = dp.createBranches();

        String result = filter.getBranchOfKnowledgeName("Автоматизація та приладобудування");

        assertEquals("Автоматизація та приладобудування", result);
    }

    @Test
    @DisplayName("Check Filter.getBranchOfKnowledgeName with Аграрні науки та продовольство")
    void getBranchOfKnowledgeName4() {
        filter = new Filter(dp);
        List<BranchOfKnowledge> branches = dp.createBranches();

        String result = filter.getBranchOfKnowledgeName("Аграрні науки та продовольство");

        assertEquals("Аграрні науки та продовольство", result);
    }

    @Test
    @DisplayName("Check Filter.getBranchOfKnowledgeName with Транспорт")
    void getBranchOfKnowledgeName5() {
        filter = new Filter(dp);
        List<BranchOfKnowledge> branches = dp.createBranches();

        String result = filter.getBranchOfKnowledgeName("Транспорт");

        assertEquals("Транспорт", result);
    }
}