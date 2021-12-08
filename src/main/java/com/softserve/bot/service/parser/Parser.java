package com.softserve.bot.service.parser;

import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Slf4j
@Component
public class Parser {
    private final SpecialtyToSubject specialtyToSubject;

    public Parser() {
        specialtyToSubject = new SpecialtyToSubject();
    }

    public Properties getPathToFiles() throws IOException {
        Properties props = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("files.properties")) {
            props.load(in);
        }
        return props;
    }

    public SpecialtyToSubject doParse() {

        doParseDomain(specialtyToSubject);
        doParseSpecialties(specialtyToSubject);
        setDomainToSpecialty(specialtyToSubject);

        return specialtyToSubject;
    }

    protected void doParseDomain(SpecialtyToSubject sts) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(getPathToFiles().getProperty("pathToCNBranches")))) {
            while ((line = br.readLine()) != null) {
                String[] domainInfo = line.split(";");
                sts.getDomainIdToName().put(domainInfo[0], domainInfo[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doParseSpecialties(SpecialtyToSubject sts) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(getPathToFiles().getProperty("pathToCNSpecialties")))) {
            while ((line = br.readLine()) != null) {
                String[] domainInfo = line.split(";");
                Specialty specialty = new Specialty();
                specialty.setCode(domainInfo[0]);
                specialty.setName(domainInfo[1]);
                specialty.setFirst(checkSubject(domainInfo[2]));
                specialty.setSecond(addSubjectListToEnum(domainInfo, 3));
                specialty.setThird(addSubjectListToEnum(domainInfo, 4));
                sts.getSpecialtyIdToName().put(domainInfo[0], specialty);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Set<Subject> addSubjectListToEnum(String[] second, int index) {
        String[] res = String.valueOf(second[index]).split(",");
        List<Subject> list = new ArrayList<>();
        for (String re : res) {
            list.add(checkSubject(re));
        }
        return EnumSet.copyOf(list);
    }

    Subject checkSubject(String subjectName) {
        switch (subjectName.trim().toLowerCase()) {
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
                throw new RuntimeException("There is no data to parse");
        }
    }

    protected void checkForEmptySubjectInSpecialty(SpecialtyToSubject sts) {
        List<String> badKeys = new ArrayList<>();
        for (String key : sts.getSpecialtyIdToName().keySet()) {
            if (sts.getSpecialtyIdToName().get(key).getFirst() == null && sts.getSpecialtyIdToName().get(key).getSecond().isEmpty()
                    && sts.getSpecialtyIdToName().get(key).getThird().isEmpty()) {
                badKeys.add(key);
            }
        }
        for (String key : badKeys) {
            sts.getSpecialtyIdToName().remove(key);
        }

    }

    public void doParseExcelToCSV() throws IOException {
        SpecialtyToSubject specialtyToSubject = new SpecialtyToSubject();


        try (XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream(getPathToFiles().getProperty("pathToExcel")));
             PrintWriter writerCSVDomain = new PrintWriter(getPathToFiles().getProperty("pathToCNBranches"));
             PrintWriter writerCSVSpecialty = new PrintWriter(getPathToFiles().getProperty("pathToCNSpecialties"))) {

            XSSFSheet excelSheet = excelBook.getSheet(getPathToFiles().getProperty("sheetName"));
            XSSFRow row;
            StringBuilder sbForDomain = new StringBuilder();
            StringBuilder sbForSpecialty = new StringBuilder();
            String domainName;
            String domainId = "";
            int domId;
            for (int i = 2; i <= excelSheet.getLastRowNum() + 1; i++) {
                if (excelSheet.getRow(i) != null) {
                    row = excelSheet.getRow(i);
                    if (excelSheet.getRow(i).getCell(0) != null) {
                        if (domainId.equals(specialtyToSubject.getDomainIdToName().get(domainId)) || !String.valueOf(row.getCell(0)).equals("")) {

                            domainId = deleteFirstAndLastSpaces(String.valueOf(row.getCell(0)));
                            domainName = deleteFirstAndLastSpaces(String.valueOf(row.getCell(1)));
                            domId = Integer.parseInt(domainId);
                            if ((domId >= 1 && domId <= 10) || (domId >= 20 && domId <= 24) || (domId >= 28 && domId <= 29)) {
                                sbForDomain = appendDomainIdDomainName(sbForDomain, domainId, domainName, "Г");
                            }
                            if ((domId >= 11 && domId <= 19) || (domId >= 25 && domId <= 27)) {
                                sbForDomain = appendDomainIdDomainName(sbForDomain, domainId, domainName, "Т");
                            }
                        }
                    }

                    sbForSpecialty = setSpecialtyForCSV(row, sbForSpecialty);
                }
            }
            writerCSVDomain.write(sbForDomain.toString());
            writerCSVSpecialty.write(sbForSpecialty.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    protected StringBuilder appendDomainIdDomainName(StringBuilder sb, String id, String name, String type) {
        return sb.append(id).append(";").append(name).append(";").append(type).append("\n");
    }

    protected String deleteFirstAndLastSpaces(String cellValue) {
        while (cellValue.startsWith(" ")) {
            cellValue = cellValue.substring(1);
        }
        while (cellValue.length() > 0 && (cellValue.charAt(cellValue.length() - 1) == ' ' || cellValue.charAt(cellValue.length() - 1) == '\u00A0')) {
            cellValue = cellValue.substring(0, cellValue.length() - 1);
        }
        return cellValue;
    }

    protected void setDomainToSpecialty(SpecialtyToSubject sts) {
        List<String> specialId = new ArrayList<>();
        for (String keyD : sts.getDomainIdToName().keySet()) {
            for (String keyS : sts.getSpecialtyIdToName().keySet()) {
                if ("014".equals(keyS)) {
                    continue;
                }
                if (keyD.substring(0, 2).equals(keyS.substring(0, 2))) {
                    specialId.add(keyS);
                }
            }
            sts.getDomainIdToSpecialtyId().put(keyD, specialId);
            specialId = new ArrayList<>();
        }
    }


    protected StringBuilder setSpecialtyForCSV(XSSFRow row, StringBuilder sb) {
        if ("014".equals(deleteFirstAndLastSpaces(String.valueOf(row.getCell(2)))) && "Середня освіта (за предметними спеціальностями)".equals(deleteFirstAndLastSpaces(String.valueOf(row.getCell(3))))) {
            return sb;
        }
        sb.append(deleteFirstAndLastSpaces(String.valueOf(row.getCell(2)).replace('\u00A0', ' '))).append(";")
                .append(deleteFirstAndLastSpaces(String.valueOf(row.getCell(3)).replace('\u00A0', ' '))).append(";")
                .append(deleteFirstAndLastSpaces(String.valueOf(row.getCell(4)).replace('\u00A0', ' '))).append(";");

        sb = setStringArraySpecialties(row, sb, 5);
        sb.append(";");

        sb = setStringArraySpecialties(row, sb, 6);
        sb.append("\n");
        return sb;
    }

    protected StringBuilder setStringArraySpecialties(XSSFRow row, StringBuilder sb, int column) {
        String[] res = String.valueOf(row.getCell(column)).replace(",", "").replace('\u00A0', ' ').split(" або ");
        for (String re : res) {
            sb.append(deleteFirstAndLastSpaces(re)).append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb;
    }


}
