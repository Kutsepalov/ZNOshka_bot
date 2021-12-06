package com.softserve.bot.service.parser;

import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Parser {
    private final SpecialtyToSubject specialtyToSubject;

    public Parser() {
        specialtyToSubject = new SpecialtyToSubject();
    }

    protected Subject checkSubject(String subjectName) {
        switch (subjectName.replace('\u00A0', ' ').trim().toLowerCase()) {
            case "українська мова":
                return Subject.UKRAINIAN;
            case "українська мова і література":
                return Subject.LITERATURE;
            case "математика":
                return Subject.MATH_PROFILE;
            case "англійська мова":
                return Subject.ENGLISH;
            case "іспанська мова":
                return Subject.SPANISH;
            case "німецька мова":
                return Subject.GERMANY;
            case "французька мова":
                return Subject.FRENCH;
            case "іноземна мова":
                return Subject.FOREIGN;
            case "біологія":
                return Subject.BIOLOGY;
            case "географія":
                return Subject.GEOGRAPHY;
            case "фізика":
                return Subject.PHYSICS;
            case "хімія":
                return Subject.CHEMISTRY;
            case "творчий конкурс":
                return Subject.CREATIVE_COMPETITION;
            case "історія україни":
                return Subject.HISTORY;
            default:
                throw new IllegalArgumentException("There is no data to parse");
        }
    }

    public SpecialtyToSubject doParse() {
        try (XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream("src/main/resources/Book1.xlsx"))) {
            XSSFSheet excelSheet = excelBook.getSheet("Sheet1");
            XSSFRow row;
            String domainName;
            String domainId = "";

            for (int i = 2; i <= excelSheet.getLastRowNum() + 1; i++) {
                if (excelSheet.getRow(i) != null) {
                    row = excelSheet.getRow(i);
                    if (domainId.equals(specialtyToSubject.getDomainIdToName().get(domainId)) || !row.getCell(0).toString().equals("")) {
                        domainId = row.getCell(0).toString();
                        domainName = row.getCell(1).toString();
                        specialtyToSubject.getDomainIdToName().put(setTrueDomainIdFormat(row, 0), domainName);
                        setDomainToSpecialty(specialtyToSubject, excelSheet, domainId, i);
                    }

                    setSpecialty(specialtyToSubject, row);
                }

            }
            checkForEmptySubjectInSpecialty(specialtyToSubject);
        } catch (SecurityException e) {
            log.error("File \"Book1.xlsx\" read access denied");
            System.exit(1);
        } catch (FileNotFoundException e) {
            log.error("File \"Book1.xlsx\" not found");
            System.exit(1);
        } catch (IOException e) {
            log.error("Error reading file: " + e.getMessage());
            System.exit(1);
        }
        return specialtyToSubject;
    }

    protected void checkForEmptySubjectInSpecialty(SpecialtyToSubject sts){
        List<String> badKeys = new ArrayList<>();
        for (String key : sts.getSpecialtyIdToName().keySet()){
            if(sts.getSpecialtyIdToName().get(key).getFirst() == null && sts.getSpecialtyIdToName().get(key).getSecond().isEmpty()
            && sts.getSpecialtyIdToName().get(key).getThird().isEmpty()){
                badKeys.add(key);
            }
        }
        for (String key : badKeys){
            sts.getSpecialtyIdToName().remove(key);
        }

    }

    protected void setDomainToSpecialty(SpecialtyToSubject sts, XSSFSheet sheet, String curDomainId, int curRow) {
        List<String> specialId = new ArrayList<>();
        XSSFRow row = null;
        String trueDomainId = setTrueDomainIdFormat(sheet.getRow(curRow), 0);
        for (int i = curRow; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) != null) {
                row = sheet.getRow(i);
            }
            if (row == null) throw new NullPointerException();
            if (row.getCell(0).toString().isEmpty() || curDomainId.equals(row.getCell(0).toString())) {
                specialId.add(setTrueIdFormat(row, 2));
            } else {
                break;
            }

        }
        sts.getDomainIdToSpecialtyId().put(trueDomainId, specialId);
    }

    protected String setTrueIdFormat(XSSFRow row, int cellPosition) {
        if (row.getCell(cellPosition).toString().length() > 5) {
            return (row.getCell(cellPosition).toString());
        } else {
            if (row.getCell(cellPosition).toString().split("\\.")[0].length() <= 2) {
                return ("0" + (int) Double.parseDouble(row.getCell(cellPosition).toString()));
            } else {
                return (String.valueOf((int) Double.parseDouble(row.getCell(cellPosition).toString())));
            }
        }
    }

    protected String setTrueDomainIdFormat(XSSFRow row, int cellPosition) {
        if (row.getCell(cellPosition).toString().length() > 4) {
            return (row.getCell(cellPosition).toString());
        } else {
            if (row.getCell(cellPosition).toString().split("\\.")[0].length() == 1) {
                return ("0" + (int) Double.parseDouble(row.getCell(cellPosition).toString()));
            } else {
                return (String.valueOf((int) Double.parseDouble(row.getCell(cellPosition).toString())));
            }
        }
    }

    protected void setSpecialty(SpecialtyToSubject sts, XSSFRow row) {
        String[] res = row.getCell(7).toString().split("або");
        Specialty specialty = new Specialty();
        if (!row.getCell(2).toString().isEmpty()) {
            specialty.setCode(row.getCell(2).toString());
        }
        if (!row.getCell(3).toString().isEmpty()) {
            specialty.setName(row.getCell(3).toString());
        }
        if (!row.getCell(4).toString().isEmpty() && row.getCell(4).toString() != null) {
            specialty.setFirst(checkSubject(row.getCell(4).toString()));
        }

        for (int i = 0; i < res.length; i++) {
            if (!res[i].equals("")) {
                specialty.getThird().add(checkSubject(res[i]));
            }
        }

        res = row.getCell(5).toString().split("або");

        for (int i = 0; i < res.length; i++) {
            if (!res[i].isEmpty()) {
                specialty.getSecond().add(checkSubject(res[i]));
            }
        }

        sts.getSpecialtyIdToName().put(setTrueIdFormat(row, 2), specialty);
    }
}

