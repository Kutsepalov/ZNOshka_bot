package com.softserve.bot.service;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Subject;
import com.softserve.bot.model.TypeOfBranch;
import com.softserve.bot.service.parser.Parser;
import lombok.Setter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.annotation.PrepareTestInstance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Component
class DataProcessorTest {
    private static Parser parser;
    private static DataProcessor  dp;

    List<BranchOfKnowledge> branches = dp.createBranches();
    @BeforeAll
    public static void preInit()
    {
        parser = new Parser();
        parser.setCNBranches("src/main/resources/csvs/codenamebranches.csv");
        parser.setCNSpecialties("src/main/resources/csvs/codenamespecialties.csv");
        parser.setLinksToSpecialties("src/main/resources/csvs/linkstospecialties.csv");
        parser.setLinksToUniversities("src/main/resources/csvs/linkstouniversities.csv");
        dp = new DataProcessor(parser);
    }

    @Test
    @DisplayName("Check code and title for a branch")
    public void test1() {
        String code = "08";
        String title = "Право";

        String expected = code + "-" + title;
        String actual = branches.get(6).toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check title for the first branch in the list")
    public void test2() {
        String expected = "Освіта\\Педагогіка";
        String actual = branches.get(0).getTitle();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check type of the branch for humanitarian")
    public void test3() {
        String expected = String.valueOf(TypeOfBranch.HUMANITIES);
        String actual = String.valueOf(branches.get(6).getTypeOfBranch());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check type of the branch for technical")
    public void test4() {
        String expected = String.valueOf(TypeOfBranch.TECHNICAL);
        String actual = String.valueOf(branches.get(12).getTypeOfBranch());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check first subject for branch with code = 08")
    public void test5() {

        Subject expected = Subject.LITERATURE;
        Subject actual = branches.get(6).getSpecialties().get(0).getFirst();


        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check set with second subjects for branch with code = 08")
    public void test7() {

        Set<Subject> expected = new HashSet<>();
        expected.add(Subject.HISTORY);
        Set<Subject> actual = branches.get(6).getSpecialties().get(0).getSecond();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check set with third subjects for branch with code = 08")
    public void test6() {

        Set<Subject> expected = new HashSet<>();
        expected.add(Subject.MATH_PROFILE);
        expected.add(Subject.FOREIGN);
        Set<Subject> actual = branches.get(6).getSpecialties().get(0).getThird();


        assertEquals(expected, actual);
    }

}