package com.softserve.webhookbot.service.parser;

import com.softserve.webhookbot.enumeration.Subject;
import com.softserve.webhookbot.service.repository.Specialty;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final SpecialtyToSubject specialtyToSubject;

    public Parser(){
        specialtyToSubject = new SpecialtyToSubject();
    }

//    private Properties getPathToExcel() {
//        Properties props = new Properties();
//        try (InputStream in = this.getClass().getResourceAsStream("excel.properties")) {
//            props.load(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return props;
//    }

    private Subject checkSubject(String subjectName) {
        Subject subj;

        switch (subjectName.replace('\u00A0', ' ').trim().toLowerCase()) {
            case "українська мова":
                subj = Subject.UKRAINIAN;
                break;
            case "українська мова і література":
                subj = Subject.LITERATURE;
                break;
            case "математика":
                subj = Subject.MATH_STANDARD;
                break;
            case "англійська мова":
                subj = Subject.ENGLISH;
                break;
            case "іспанська мова":
                subj = Subject.SPANISH;
                break;
            case "німецька мова":
                subj = Subject.GERMANY;
                break;
            case "французька мова":
                subj = Subject.FRENCH;
                break;
            case "іноземна мова":
                subj = Subject.FOREIGN_LANGUAGE;
                break;
            case "біологія":
                subj = Subject.BIOLOGY;
                break;
            case "географія":
                subj = Subject.GEOGRAPHY;
                break;
            case "фізика":
                subj = Subject.PHYSICS;
                break;
            case "хімія":
                subj = Subject.CHEMISTRY;
                break;
            case "творчий конкурс":
                subj = Subject.CREATIVE_COMPETITION;
                break;
            case "історія україни":
                subj = Subject.HISTORY;
                break;
            default:
                subj = null;
        }
        return subj;
    }

    private XSSFWorkbook getWorkbook() throws IOException {
        return new XSSFWorkbook(new FileInputStream("src/main/resources/Book1.xlsx"));
    }

    public SpecialtyToSubject doParse() throws IOException {
        XSSFWorkbook excelBook = getWorkbook();
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

        return specialtyToSubject;
    }

    protected void setDomainToSpecialty(SpecialtyToSubject sts, XSSFSheet sheet, String curDomainId, int curRow) {
        List<String> specialId = new ArrayList<>();
        XSSFRow row = null;
        String trueDomainId = setTrueDomainIdFormat(sheet.getRow(curRow), 0);
        for (int i = curRow; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) != null) {
                row = sheet.getRow(i);
            }
            if(row == null) throw new NullPointerException();
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
        specialty.setName(row.getCell(3).toString());
        specialty.setFirst(checkSubject(row.getCell(4).toString()));

        for (int i = 0; i < res.length; i++) {
            if (!res[i].equals("")) {
                specialty.getThird().add(checkSubject(res[i]));
            }
        }

        res = row.getCell(5).toString().split("або");

        for (int i = 0; i < res.length; i++) {
            specialty.getSecond().add(checkSubject(res[i]));
        }

        sts.getSpecialtyIdToName().put(setTrueIdFormat(row, 2), specialty);
    }
}

