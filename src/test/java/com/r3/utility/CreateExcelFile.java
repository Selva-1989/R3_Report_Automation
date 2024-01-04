package com.r3.utility;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.r3.datareader.PropertiesFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CreateExcelFile {
    static Path clonedR3File;
    public static String cloneR3Report(String testCaseName) {
        try {
            Path originalR3File = Paths.get(PropertiesFileReader.getProperty("R3TestReportExcelPath"));
            clonedR3File = Paths.get(getNewFileName(PropertiesFileReader.getProperty("R3TestReportExcelPath")));
            Files.copy(originalR3File, clonedR3File);
            ExtentManager.getExtentTest().log(Status.INFO,("Test Output Excel File is created ->> "+ clonedR3File));
            try {
                FileInputStream fileInputStream = new FileInputStream(clonedR3File.toFile());
                Workbook workbook = new XSSFWorkbook(fileInputStream);
                Sheet sheet = workbook.getSheetAt(0);
                addNewColumns(sheet, (XSSFWorkbook) workbook, testCaseName);
                FileOutputStream fileOutputStream = new FileOutputStream(clonedR3File.toFile());
                workbook.write(fileOutputStream);
                fileInputStream.close();
                fileOutputStream.close();
                System.out.println("Columns added successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clonedR3File.toString();
    }
    public static String getNewFileName(String fileName){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = now.format(formatter);
        return fileName.replace(".xlsx","_Test_Result_"+timeStamp+".xlsx");
    }

    private static void addNewColumns(Sheet sheet, XSSFWorkbook workbook, String testCaseName) {
        // Define new column names
        List<String> outputColumns=null;
        if(testCaseName.equalsIgnoreCase("Verify_All_Buckets_ORG_PHONE")) {
            String[] arrayColumns = {"Priority_Type", "Organization_Phone_Validation_Type", "ORG_Name_Matching_Status",
                                     "ORG_Name_Matching_URL", "Phone_Number_Matching_Status", "Phone_Number_Matching_URL",
                                     "OV_Phone_Found_Websites_Status","OV_Phone_Found_Organization_Websites_Status",
                                     "OV_Phone_Not_Found_Websites_Status","OV_Phone_Not_Found_Organization_Websites_Status", "Remarks"};
            outputColumns = new ArrayList<>(Arrays.asList(arrayColumns));
        }//else part will continue as far as use cases are increasing
        Row headerRow = sheet.getRow(0);
        // Create a cell style with a fill color and borders
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        for (int i = 0; i < outputColumns.size(); i++) {
            Cell newCell = headerRow.createCell(headerRow.getLastCellNum());
            newCell.setCellValue(outputColumns.get(i));
            newCell.setCellStyle(style);
        }
    }
}
