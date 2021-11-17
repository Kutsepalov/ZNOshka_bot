import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

//    public Properties getPathToExcel() throws IOException {
//        Properties properties = new Properties();
//        try (InputStream in = Application.class.getClassLoader().getResourceAsStream("excel.properties")) {
//            properties.load(in);
//        }
//        return properties;
//    }

    public SpecialtyToSubject doParse() throws IOException {
        SpecialtyToSubject specialtyToSubject = new SpecialtyToSubject();
        XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream("Book1.xlsx"));
        XSSFSheet excelSheet = excelBook.getSheet("Sheet1");
        XSSFRow row = null;
        String domainName = "";
        String domainId = "";

        for (int i = 2; i <= excelSheet.getLastRowNum() + 1; i++) {
            if (excelSheet.getRow(i) != null) {
                row = excelSheet.getRow(i);
                if(domainId.equals(specialtyToSubject.getDomainIdToName().get(domainId)) || !row.getCell(0).toString().equals("")){
                    domainId = row.getCell(0).toString();
                    domainName = row.getCell(1).toString();
                    specialtyToSubject.getDomainIdToName().put(domainId, domainName);
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
        for (int i = curRow; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) != null) {
                row = sheet.getRow(i);
            }
            if (row.getCell(0).toString().isEmpty() || curDomainId.equals(row.getCell(0).toString())) {
                specialId.add(row.getCell(2).toString());
            } else {
                break;
            }

        }
        sts.getDomainIdToSpecialtyId().put(curDomainId, specialId);
    }

    protected void setSpecialty(SpecialtyToSubject sts, XSSFRow row) {
        String[] res = row.getCell(7).toString().split("або ");
        Specialty specialty = new Specialty();
        specialty.setName(row.getCell(3).toString());
        specialty.setFirst(row.getCell(4).toString());

        for (int i = 0; i < res.length; i++) {
            if(!res[i].equals("")){
                specialty.getThird().add(res[i]);
            }

        }

        res = row.getCell(5).toString().split(" або ");

        for (int i = 0; i < res.length; i++) {
            specialty.getSecond().add(res[i]);
        }

        sts.getSpecialtyIdToName().put(row.getCell(2).toString(), specialty);
    }

    public static void main(String[] args) throws IOException {
        SpecialtyToSubject sts = new Parser().doParse();

//        System.out.println(sts.getDomainIdToName().toString());
//        sts.print();
        sts.printSecond();
    }
}
