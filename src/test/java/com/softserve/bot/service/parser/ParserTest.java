package com.softserve.bot.service.parser;

import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private Parser parser = new Parser();

    @Test
    @DisplayName("Check Parser.checkSubject method with first letter in upper case in argument")
    void checkSubject1() {
        var result = parser.checkSubject("Біологія");

        assertEquals(Subject.BIOLOGY, result);
    }

    @Test
    @DisplayName("Check Parser.checkSubject method with argument in lower case in argument")
    void checkSubject2() {
        var result = parser.checkSubject("біологія");

        assertEquals(Subject.BIOLOGY, result);
    }

    @Test
    @DisplayName("Check Parser.checkSubject method with argument in upper case in argument")
    void checkSubject3() {
        Parser parser = new Parser();

        var result = parser.checkSubject("БІОЛОГІЯ");

        assertEquals(Subject.BIOLOGY, result);
    }

    @Test
    @DisplayName("Check Parser.checkSubject method with empty string argument, must throw RuntimeException")
    void checkSubject4() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            parser.checkSubject("");
        }, "RuntimeException was expected");

        assertEquals("There is no data to parse", thrown.getMessage());
    }

    @Test
    @DisplayName("Add true specialty and empty specialty to specialtyIdToName map and check map size assert size = 1")
    void checkForEmptySubjectInSpecialtyTest1() {
        SpecialtyToSubject sts = new SpecialtyToSubject();
        Specialty specialty = new Specialty();
        specialty.setFirst(Subject.UKRAINIAN);
        specialty.getSecond().add(Subject.BIOLOGY);
        specialty.getThird().add(Subject.ENGLISH);
        sts.getSpecialtyIdToName().put("01", specialty);

        Specialty specialty1 = new Specialty();
        sts.getSpecialtyIdToName().put("02", specialty1);
        parser.checkForEmptySubjectInSpecialty(sts);

        var result = sts;

        assertEquals(1, result.getSpecialtyIdToName().size());
    }

    @Test
    @DisplayName("Add 2 specialty to specialtyIdToName map and check map size assert size = 2")
    void checkForEmptySubjectInSpecialtyTest2() {
        SpecialtyToSubject sts = new SpecialtyToSubject();
        Specialty specialty = new Specialty();
        specialty.setFirst(Subject.UKRAINIAN);
        specialty.getSecond().add(Subject.BIOLOGY);
        specialty.getThird().add(Subject.ENGLISH);
        sts.getSpecialtyIdToName().put("01", specialty);

        Specialty specialty1 = new Specialty();
        specialty1.setFirst(Subject.LITERATURE);
        specialty1.getSecond().add(Subject.PHYSICS);
        specialty1.getThird().add(Subject.HISTORY);
        sts.getSpecialtyIdToName().put("02", specialty1);
        parser.checkForEmptySubjectInSpecialty(sts);

        var result = sts;

        assertEquals(2, result.getSpecialtyIdToName().size());
    }

}