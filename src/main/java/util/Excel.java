package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
    private static XSSFWorkbook Excelwbook;
    private static XSSFSheet ExcelWSheet;
    private static XSSFRow Row;
    private static XSSFCell Cell;

    public static void setExcelFile(String filePath, String sheetName) throws IOException {
        FileInputStream ExcelFile = new FileInputStream(filePath);
        Excelwbook = new XSSFWorkbook(ExcelFile);
        ExcelWSheet = Excelwbook.getSheet(sheetName);
    }

    public static String getCellData(int RowNum, int ColNum) {
        Row = ExcelWSheet.getRow(RowNum);
        Cell = Row.getCell(ColNum);

        String celldata = (String) (Cell.getCellType() == CellType.STRING
                ? Cell.getStringCellValue() : "" + Cell.getNumericCellValue());

        return celldata;
    }

    public static void setCellData(int rowNum, int colNum, String result) throws IOException {
        Row = ExcelWSheet.getRow(rowNum);
        Cell = Row.getCell(colNum, XSSFRow.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (Cell == null) {
            Cell = Row.createCell(colNum);
            Cell.setCellValue(result);
        } else {
            Cell.setCellValue(result);
        }

        FileOutputStream fileout = new FileOutputStream(Constant.TestDataExcelFilePath);
        Excelwbook.write(fileout);
        fileout.flush();
        fileout.close();
    }

    public static Object[][] getTestData(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        Workbook workbook = null;
        String fileExtension = filePath.substring(filePath.indexOf("."));

        if (fileExtension.equals(".xls")) {
            workbook = new HSSFWorkbook(input);
        } else if (fileExtension.equals(".xlsx")) {
            workbook = new XSSFWorkbook(input);
        }

        Sheet sheet = workbook.getSheet(sheetName);
        Log.info("sheet.getLastRowNum()：" + sheet.getLastRowNum());
        Log.info("sheet.getFirstRowNum()：" + sheet.getFirstRowNum());
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        ArrayList<Object[]> records = new ArrayList<Object[]>();

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            System.out.println("print out :" + row.getLastCellNum());
            String field[] = new String[row.getLastCellNum()];
            for (int j = 0; j < row.getLastCellNum(); j++) {
                field[j] = (String) (row.getCell(j).getCellType() == CellType.STRING ?
                        row.getCell(j).getStringCellValue() : "" + new DecimalFormat("0").format(row.getCell(j).getNumericCellValue()));
            }
            records.add(field);
        }

        Object[][] results = new Object[records.size()][];
        for (int i = 0; i < records.size(); i++) {
            results[i] = records.get(i);
        }
        return results;
    }

    public static int getLastColumnNum() {
        Log.info("getLastCellNum():" + ExcelWSheet.getRow(0).getLastCellNum());
        return ExcelWSheet.getRow(0).getLastCellNum() - 1;
    }
}