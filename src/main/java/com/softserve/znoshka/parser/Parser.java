package com.softserve.znoshka.parser;

import com.softserve.znoshka.repository.Specialty;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Parser {

    public Properties getPathToExcel() throws IOException {
        Properties props = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("excel.properties")) {
            props.load(in);
        }
        return props;
    }

    public SpecialtyToSubject doParse() throws IOException {
        SpecialtyToSubject specialtyToSubject = new SpecialtyToSubject();
        XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream(getPathToExcel().getProperty("pathToExcel")));
        XSSFSheet excelSheet = excelBook.getSheet(getPathToExcel().getProperty("sheetName"));
        XSSFRow row = null;
        String domainName = "";
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
                return ("0" + String.valueOf((int) Double.parseDouble(row.getCell(cellPosition).toString())));
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
                return ("0" + String.valueOf((int) Double.parseDouble(row.getCell(cellPosition).toString())));
            } else {
                return (String.valueOf((int) Double.parseDouble(row.getCell(cellPosition).toString())));
            }
        }
    }

    protected void setSpecialty(SpecialtyToSubject sts, XSSFRow row) {
        String[] res = row.getCell(7).toString().split("або ");
        Specialty specialty = new Specialty();
        specialty.setCode(row.getCell(2).toString());
        specialty.setName(row.getCell(3).toString());
        specialty.setFirst(row.getCell(4).toString());

        for (int i = 0; i < res.length; i++) {
            if (!res[i].equals("")) {
                specialty.getThird().add(res[i]);
            }
        }

        res = row.getCell(5).toString().split(" або ");

        for (int i = 0; i < res.length; i++) {
            specialty.getSecond().add(res[i]);
        }

        sts.getSpecialtyIdToName().put(setTrueIdFormat(row, 2), specialty);
    }

    public static void main(String[] args) throws IOException {
        SpecialtyToSubject sts = new Parser().doParse();

        //sts.printFirst();
        //sts.printSecond();
        sts.printThird();
    }
}
