package com.r3.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteR3TestResult {
    FileOutputStream outFile;
    XSSFWorkbook workbook;
    XSSFSheet sheet;

    public void writeBasicDataForValidation(String R3TestResultFilePath, int executingRowIndex,
                                            String phoneValidationPriority, String OrganizationPhoneValidation) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                try {
                    String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                    //Write the priority to Priority_Type column
                    if (columnHead.equalsIgnoreCase("Priority_Type")) {
                        XSSFCell PriorityTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (PriorityTypeCellValue.getCellType() != null || PriorityTypeCellValue.getCellType() != CellType.BLANK) {
                                CellStyle style = workbook.createCellStyle();
                                style.setBorderBottom(BorderStyle.THIN);
                                style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                                style.setBorderTop(BorderStyle.THIN);
                                style.setTopBorderColor(IndexedColors.BLACK.getIndex());
                                style.setBorderLeft(BorderStyle.THIN);
                                style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                                style.setBorderRight(BorderStyle.THIN);
                                style.setRightBorderColor(IndexedColors.BLACK.getIndex());
                                PriorityTypeCellValue.setCellValue("");
                                PriorityTypeCellValue.setCellValue(phoneValidationPriority);
                            }
                        } catch (NullPointerException e) {
                            CellStyle style = workbook.createCellStyle();
                            style.setBorderBottom(BorderStyle.THIN);
                            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                            style.setBorderTop(BorderStyle.THIN);
                            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
                            style.setBorderLeft(BorderStyle.THIN);
                            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                            style.setBorderRight(BorderStyle.THIN);
                            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(phoneValidationPriority);
                        }
                    }
                    //Write the Organization_Phone_Validation value to Organization_Phone_Validation_Type column
                    if (columnHead.equalsIgnoreCase("Organization_Phone_Validation_Type")) {
                        XSSFCell OrganizationPhoneValidationTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (OrganizationPhoneValidationTypeCellValue.getCellType() != null || OrganizationPhoneValidationTypeCellValue.getCellType() != CellType.BLANK) {
                                CellStyle style = workbook.createCellStyle();
                                style.setBorderBottom(BorderStyle.THIN);
                                style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                                style.setBorderTop(BorderStyle.THIN);
                                style.setTopBorderColor(IndexedColors.BLACK.getIndex());
                                style.setBorderLeft(BorderStyle.THIN);
                                style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                                style.setBorderRight(BorderStyle.THIN);
                                style.setRightBorderColor(IndexedColors.BLACK.getIndex());
                                OrganizationPhoneValidationTypeCellValue.setCellValue("");
                                OrganizationPhoneValidationTypeCellValue.setCellValue(OrganizationPhoneValidation);
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                            }
                        } catch (NullPointerException e) {
                            CellStyle style = workbook.createCellStyle();
                            style.setBorderBottom(BorderStyle.THIN);
                            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                            style.setBorderTop(BorderStyle.THIN);
                            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
                            style.setBorderLeft(BorderStyle.THIN);
                            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                            style.setBorderRight(BorderStyle.THIN);
                            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(OrganizationPhoneValidation);
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                        }
                    }
                } catch (Exception e) {
            }
        }
    }
    CellStyle greenCellStyle;
    CellStyle redCellStyle;
    CellStyle pinkCellStyle;
    CellStyle blueCellStyle;
    public void writeORGNameMatchStatus(String R3TestResultFilePath, int executingRowIndex, String ORGNameMatchingStatus, String Url) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);

            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.THIN);
            greenCellStyle.setBorderBottom(BorderStyle.THIN);
            greenCellStyle.setBorderLeft(BorderStyle.THIN);
            greenCellStyle.setBorderRight(BorderStyle.THIN);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.THIN);
            redCellStyle.setBorderBottom(BorderStyle.THIN);
            redCellStyle.setBorderLeft(BorderStyle.THIN);
            redCellStyle.setBorderRight(BorderStyle.THIN);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.THIN);
            pinkCellStyle.setBorderBottom(BorderStyle.THIN);
            pinkCellStyle.setBorderLeft(BorderStyle.THIN);
            pinkCellStyle.setBorderRight(BorderStyle.THIN);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the ORG Name Matching status to ORG_Name_Matching_Status column
                if (columnHead.equalsIgnoreCase("ORG_Name_Matching_Status")) {
                    XSSFCell ORGNameMatchingStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (ORGNameMatchingStatusCellValue.getCellType() != null || ORGNameMatchingStatusCellValue.getCellType() != CellType.BLANK) {
                            ORGNameMatchingStatusCellValue.setCellValue("");
                            ORGNameMatchingStatusCellValue.setCellValue(ORGNameMatchingStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")) {
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                            }
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(ORGNameMatchingStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                        }
                    }
                }
                //Write the currently Executed URL to ORG_Name_Matching_URL column
                if (columnHead.equalsIgnoreCase("ORG_Name_Matching_URL")) {
                    XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                   /* boolean oneToThree = Integer.parseInt(phoneValidationPriority)>=1 && Integer.parseInt(phoneValidationPriority)<=3;
                    boolean fourToSix = Integer.parseInt(phoneValidationPriority)>=4 && Integer.parseInt(phoneValidationPriority)<=6;
                    boolean sevenToNine = Integer.parseInt(phoneValidationPriority)>=7 && Integer.parseInt(phoneValidationPriority)<=9;*/
                    try {
                        if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                            WebSiteURLCellValue.setCellValue("");
                            WebSiteURLCellValue.setCellValue(Url);
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url==null ? "None of the ORG Websites displayed the ORG Name!!! " :  Url);
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    public void writePhoneNumberMatchStatus(String R3TestResultFilePath, int executingRowIndex, String PhoneNumberMatchingStatus, String Url) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.THIN);
            greenCellStyle.setBorderBottom(BorderStyle.THIN);
            greenCellStyle.setBorderLeft(BorderStyle.THIN);
            greenCellStyle.setBorderRight(BorderStyle.THIN);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.THIN);
            redCellStyle.setBorderBottom(BorderStyle.THIN);
            redCellStyle.setBorderLeft(BorderStyle.THIN);
            redCellStyle.setBorderRight(BorderStyle.THIN);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.THIN);
            pinkCellStyle.setBorderBottom(BorderStyle.THIN);
            pinkCellStyle.setBorderLeft(BorderStyle.THIN);
            pinkCellStyle.setBorderRight(BorderStyle.THIN);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the Phone Number Matching status to PhoneNumberMatchingStatus column
                if (columnHead.equalsIgnoreCase("Phone_Number_Matching_Status")) {
                    XSSFCell PhoneNumberMatchingStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (PhoneNumberMatchingStatusCellValue.getCellType() != null || PhoneNumberMatchingStatusCellValue.getCellType() != CellType.BLANK) {
                            PhoneNumberMatchingStatusCellValue.setCellValue("");
                            PhoneNumberMatchingStatusCellValue.setCellValue(PhoneNumberMatchingStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                            }
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(PhoneNumberMatchingStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                        }
                    }
                }
                //Write the currently Executed URL to Phone_Number_Matching_URL column
                if (columnHead.equalsIgnoreCase("Phone_Number_Matching_URL")) {
                    XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                            WebSiteURLCellValue.setCellValue("");
                            WebSiteURLCellValue.setCellValue(Url);
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url==null ? "None of the ORG Websites displayed the Phone Number!!!": Url);
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }
    }
    public void writePhoneNumberMatchStatus(String R3TestResultFilePath, int executingRowIndex, String PhoneNumberMatchingStatus, List<String> orgNameMatchedURList) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.THIN);
            greenCellStyle.setBorderBottom(BorderStyle.THIN);
            greenCellStyle.setBorderLeft(BorderStyle.THIN);
            greenCellStyle.setBorderRight(BorderStyle.THIN);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.THIN);
            redCellStyle.setBorderBottom(BorderStyle.THIN);
            redCellStyle.setBorderLeft(BorderStyle.THIN);
            redCellStyle.setBorderRight(BorderStyle.THIN);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.THIN);
            pinkCellStyle.setBorderBottom(BorderStyle.THIN);
            pinkCellStyle.setBorderLeft(BorderStyle.THIN);
            pinkCellStyle.setBorderRight(BorderStyle.THIN);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the Phone Number Matching status to PhoneNumberMatchingStatus column
                if (columnHead.equalsIgnoreCase("Phone_Number_Matching_Status")) {
                    XSSFCell PhoneNumberMatchingStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (PhoneNumberMatchingStatusCellValue.getCellType() != null || PhoneNumberMatchingStatusCellValue.getCellType() != CellType.BLANK) {
                            PhoneNumberMatchingStatusCellValue.setCellValue("");
                            PhoneNumberMatchingStatusCellValue.setCellValue(PhoneNumberMatchingStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                            }
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(PhoneNumberMatchingStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                        }
                    }
                }
                //Write the currently Executed URL to Phone_Number_Matching_URL column
                if (columnHead.equalsIgnoreCase("Phone_Number_Matching_URL")) {
                    XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                            WebSiteURLCellValue.setCellValue("");
                            String lisURL = "";
                            for(String Url : orgNameMatchedURList) {
                                lisURL = lisURL + "| " + Url;
                            }
                            WebSiteURLCellValue.setCellValue(lisURL);
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        String lisURL = "";
                        for(String Url : orgNameMatchedURList) {
                            lisURL =lisURL + "| " + Url;
                        }
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(lisURL==null ? "None of the ORG Websites displayed the Phone Number!!!": lisURL);
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }
    }


    public void writePhoneNumberFoundURLMatchStatus(String R3TestResultFilePath, int executingRowIndex,
                                                    String OV_PhoneFoundWebsitesMatchingStatus,
                                                    String OV_PhoneFoundOrgWebsitesMatchingStatus) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.THIN);
            greenCellStyle.setBorderBottom(BorderStyle.THIN);
            greenCellStyle.setBorderLeft(BorderStyle.THIN);
            greenCellStyle.setBorderRight(BorderStyle.THIN);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.THIN);
            redCellStyle.setBorderBottom(BorderStyle.THIN);
            redCellStyle.setBorderLeft(BorderStyle.THIN);
            redCellStyle.setBorderRight(BorderStyle.THIN);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.THIN);
            pinkCellStyle.setBorderBottom(BorderStyle.THIN);
            pinkCellStyle.setBorderLeft(BorderStyle.THIN);
            pinkCellStyle.setBorderRight(BorderStyle.THIN);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            blueCellStyle = workbook.createCellStyle();
            blueCellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            blueCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            blueCellStyle.setBorderTop(BorderStyle.THIN);
            blueCellStyle.setBorderBottom(BorderStyle.THIN);
            blueCellStyle.setBorderLeft(BorderStyle.THIN);
            blueCellStyle.setBorderRight(BorderStyle.THIN);
            blueCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            blueCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            blueCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            blueCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the Url Matching status to OV_Phone_Found_Websites_Status column
                if (columnHead.equalsIgnoreCase("OV_Phone_Found_Websites_Status")) {
                    XSSFCell OV_PhoneFoundWebSitesStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (OV_PhoneFoundWebSitesStatusCellValue.getCellType() != null || OV_PhoneFoundWebSitesStatusCellValue.getCellType() != CellType.BLANK) {
                            OV_PhoneFoundWebSitesStatusCellValue.setCellValue("");
                            OV_PhoneFoundWebSitesStatusCellValue.setCellValue(OV_PhoneFoundWebsitesMatchingStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                            }
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(OV_PhoneFoundWebsitesMatchingStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                        }
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                    }
                }

                //Write the Url Matching status to OV_Phone_Found_Organization_Websites_Status column
                 columnHead = sheet.getRow(0).getCell(j+1).toString().trim();
                if (columnHead.equalsIgnoreCase("OV_Phone_Found_Organization_Websites_Status")) {
                    XSSFCell OV_PhoneFoundOrgWebsitesStatusCellValue = sheet.getRow(executingRowIndex).getCell(j+1);
                    try {
                        if (OV_PhoneFoundOrgWebsitesStatusCellValue.getCellType() != null || OV_PhoneFoundOrgWebsitesStatusCellValue.getCellType() != CellType.BLANK) {
                            OV_PhoneFoundOrgWebsitesStatusCellValue.setCellValue("");
                            OV_PhoneFoundOrgWebsitesStatusCellValue.setCellValue(OV_PhoneFoundOrgWebsitesMatchingStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                                sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(pinkCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("NO NEED")){
                                sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(blueCellStyle);
                            }
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j+1);
                        sheet.getRow(executingRowIndex).getCell(j+1).setCellValue(OV_PhoneFoundOrgWebsitesMatchingStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(pinkCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("NO NEED")){
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(blueCellStyle);
                        }
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                        break;
                    }
                }

            } catch (Exception e) {

            }
        }
    }

    public void writePhoneNumberNOTFoundURLMatchStatus(String R3TestResultFilePath, int executingRowIndex,
                                                       String OV_PhoneNotFoundWebsitesStatus,
                                                       String OV_PhoneNOTFoundORGWebsitesMatchingStatus) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.THIN);
            greenCellStyle.setBorderBottom(BorderStyle.THIN);
            greenCellStyle.setBorderLeft(BorderStyle.THIN);
            greenCellStyle.setBorderRight(BorderStyle.THIN);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.THIN);
            redCellStyle.setBorderBottom(BorderStyle.THIN);
            redCellStyle.setBorderLeft(BorderStyle.THIN);
            redCellStyle.setBorderRight(BorderStyle.THIN);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.THIN);
            pinkCellStyle.setBorderBottom(BorderStyle.THIN);
            pinkCellStyle.setBorderLeft(BorderStyle.THIN);
            pinkCellStyle.setBorderRight(BorderStyle.THIN);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            blueCellStyle = workbook.createCellStyle();
            blueCellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            blueCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            blueCellStyle.setBorderTop(BorderStyle.THIN);
            blueCellStyle.setBorderBottom(BorderStyle.THIN);
            blueCellStyle.setBorderLeft(BorderStyle.THIN);
            blueCellStyle.setBorderRight(BorderStyle.THIN);
            blueCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            blueCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            blueCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            blueCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the Url Matching status to OV_Phone_Not_Found_Websites_Status column
                if (columnHead.equalsIgnoreCase("OV_Phone_Not_Found_Websites_Status")) {
                    XSSFCell PV_OV_PhoneNOTFoundWebSitesStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (PV_OV_PhoneNOTFoundWebSitesStatusCellValue.getCellType() != null || PV_OV_PhoneNOTFoundWebSitesStatusCellValue.getCellType() != CellType.BLANK) {
                            PV_OV_PhoneNOTFoundWebSitesStatusCellValue.setCellValue("");
                            PV_OV_PhoneNOTFoundWebSitesStatusCellValue.setCellValue(OV_PhoneNotFoundWebsitesStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                            }
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(OV_PhoneNotFoundWebsitesStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                        }
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                    }
                }

                //Write the Url Matching status to OV_Phone_Not_Found_Organization_Websites_Status column
                columnHead = sheet.getRow(0).getCell(j+1).toString().trim();
                if (columnHead.equalsIgnoreCase("OV_Phone_Not_Found_Organization_Websites_Status")) {
                    XSSFCell PV_OV_PhoneNOTFoundWebSitesStatusCellValue = sheet.getRow(executingRowIndex).getCell(j+1);
                    try {
                        if (PV_OV_PhoneNOTFoundWebSitesStatusCellValue.getCellType() != null || PV_OV_PhoneNOTFoundWebSitesStatusCellValue.getCellType() != CellType.BLANK) {
                            PV_OV_PhoneNOTFoundWebSitesStatusCellValue.setCellValue("");
                            PV_OV_PhoneNOTFoundWebSitesStatusCellValue.setCellValue(OV_PhoneNOTFoundORGWebsitesMatchingStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                                sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(pinkCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("NO NEED")){
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(blueCellStyle);
                        }
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j+1);
                        sheet.getRow(executingRowIndex).getCell(j+1).setCellValue(OV_PhoneNOTFoundORGWebsitesMatchingStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(pinkCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j+1).getStringCellValue().equalsIgnoreCase("NO NEED")) {
                            sheet.getRow(executingRowIndex).getCell(j+1).setCellStyle(blueCellStyle);
                        }
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }
    }
    public void writeRemarks_Accurate(String R3TestResultFilePath, int executingRowIndex,
                             String ORGNameMatchingStatus, String PhoneNumberMatchingStatus,
                             String OVPhoneFoundWebsitesMatchingStatus, String OVPhoneFoundORGWebsitesMatchingStatus,
                             String OVPhoneNOTFoundWebsitesMatchingStatus, boolean reverseFlag, String remarkScope,
                             String combinedSearchKeyword_OrgProvPhone,String OV_PhoneNOTFoundORGWebsitesMatchingStatus) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.THIN);
            greenCellStyle.setBorderBottom(BorderStyle.THIN);
            greenCellStyle.setBorderLeft(BorderStyle.THIN);
            greenCellStyle.setBorderRight(BorderStyle.THIN);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.THIN);
            redCellStyle.setBorderBottom(BorderStyle.THIN);
            redCellStyle.setBorderLeft(BorderStyle.THIN);
            redCellStyle.setBorderRight(BorderStyle.THIN);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.THIN);
            pinkCellStyle.setBorderBottom(BorderStyle.THIN);
            pinkCellStyle.setBorderLeft(BorderStyle.THIN);
            pinkCellStyle.setBorderRight(BorderStyle.THIN);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the test result comment to Remarks column
                if (columnHead.equalsIgnoreCase("Remarks")) {
                    if(remarkScope.equalsIgnoreCase("ORG-NAME")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                            //ORG Number validation remark starts here
                            if (ORGNameMatchingStatus.equalsIgnoreCase("PASS")) {
                                if (reverseFlag) {
                                    appendedText.append("ORG-NAME->> QC Passed: ***Note:ORG link is reversely checked. Pls cross-check once***");
                                }
                            } else if (ORGNameMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("ORG-NAME->> QC Inconclusive: ORG not found anywhere online. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone );
                            } else if (ORGNameMatchingStatus.equalsIgnoreCase("FAIL-BROKEN-WEBSITE")) {
                                appendedText.append("ORG-NAME->> QC Inconclusive: Not able to get the Web content due to Broken Website Issue" + combinedSearchKeyword_OrgProvPhone);
                            }
                            //ORG Number validation remark ends here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //ORG Number validation remark starts here
                            if (ORGNameMatchingStatus.equalsIgnoreCase("PASS")) {
                                if (reverseFlag) {
                                    appendedText.append("ORG-NAME->> QC Passed: ***Note:ORG link is reversely checked. Pls cross-check once***");
                                }
                            } else if (ORGNameMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("ORG-NAME->> QC Inconclusive: ORG not found anywhere online. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone );
                            } else if (ORGNameMatchingStatus.equalsIgnoreCase("FAIL-BROKEN-WEBSITE")) {
                                appendedText.append("ORG-NAME->>: QC Inconclusive: Not able to get the Web content due to Broken Website Issue" + combinedSearchKeyword_OrgProvPhone);
                            } else if (ORGNameMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("ORG-NAME->> NP: NOT able to find the ORG NAME itself. " +
                                        "So Phone Number Matching Case is NOT APPLICABLE");
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //ORG Number validation remark ends here
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                        break;
                    }
                }

                    if(remarkScope.equalsIgnoreCase("ORG-PHONE")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //Phone Number validation remark starts here
                                if (PhoneNumberMatchingStatus.equalsIgnoreCase("PASS")) {
                                    if (reverseFlag) {
                                        appendedText.append("ORG-PHONE->> QC Passed: ***Note:Phone Number Passed in reversely checked ORG link . Pls cross-check ORG link once***");
                                    }
                                } else if (PhoneNumberMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("ORG-PHONE->> QC Failed: Phone did not match in ORG site but R3 says OV is Accurate. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                } else if (PhoneNumberMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                    appendedText.append("ORG-PHONE->> NP: NOT able to find the ORG NAME itself. So Phone Number Matching Case is NOT APPLICABLE");
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //Phone Number validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //Phone Number validation remark starts here
                            if (PhoneNumberMatchingStatus.equalsIgnoreCase("PASS")) {
                                if (reverseFlag) {
                                    appendedText.append("ORG-PHONE->> QC Passed: ***Note:Phone Number Passed in reversely checked ORG link . Pls cross-check ORG link once***");
                                }
                            } else if (PhoneNumberMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("ORG-PHONE->> QC Failed: Phone did not match in ORG site but R3 says OV is Accurate. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            } else if (PhoneNumberMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("ORG-PHONE->> NP: NOT able to find the ORG NAME itself. So Phone Number Matching Case is NOT APPLICABLE");
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //Phone Number validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("OV_Phone_Found_Websites_Status")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        /*boolean firstPriority = Float.parseFloat(phoneValidationPriority)>=1.0 && Float.parseFloat(phoneValidationPriority)<=3.9;
                        boolean secondPriority = Float.parseFloat(phoneValidationPriority)>=4 && Float.parseFloat(phoneValidationPriority)<=6;
                        boolean thirdPriority = Float.parseFloat(phoneValidationPriority)>=7 && Float.parseFloat(phoneValidationPriority)<=9;*/
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                     appendedText = new StringBuilder();
                                }else{
                                     appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //OV_Phone_Found_Websites validation remark starts here
                                if (OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("OV_Phone_Found_Websites_Status->> QC Passed: ORG site and its details not found by R3 under OV. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                } else if (OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                    appendedText.append("OV_Phone_Found_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                            "So OV_Phone_Found_Websites Matching Case is NOT APPLICABLE");
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //OV_Phone_Found_Websites validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //OV_Phone_Found_Websites validation remark starts here
                            if (OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("OV_Phone_Found_Websites_Status->> QC Passed: ORG site and its details not found by R3 under OV. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            } else if (OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("OV_Phone_Found_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                        "So OV_Phone_Found_Websites Matching Case is NOT APPLICABLE");
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //OV_Phone_Found_Websites validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("OV_Phone_Found_Organization_Websites_Status")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        /*boolean firstPriority = Float.parseFloat(phoneValidationPriority)>=1.0 && Float.parseFloat(phoneValidationPriority)<=3.9;
                        boolean secondPriority = Float.parseFloat(phoneValidationPriority)>=4 && Float.parseFloat(phoneValidationPriority)<=6;
                        boolean thirdPriority = Float.parseFloat(phoneValidationPriority)>=7 && Float.parseFloat(phoneValidationPriority)<=9;*/
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //OV_Phone_Found_Organization_Websites_Status validation remark starts here
                                if (OVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("OV_Phone_Found_Organization_Websites_Status->> QC Passed: ORG site not classified as ORG by R3 under OV. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                } else if (OVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                    appendedText.append("OV_Phone_Found_Organization_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                            "So OV_Phone_Found_Organization_Websites_Status Matching Case is NOT APPLICABLE");
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //OV_Phone_Found_Organization_Websites_Status validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //OV_Phone_Found_Organization_Websites_Status validation remark starts here
                            if (OVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("OV_Phone_Found_Organization_Websites_Status->> QC Passed: ORG site not classified as ORG by R3 under OV. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            } else if (OVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("OV_Phone_Found_Organization_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                        "So OV_Phone_Found_Organization_Websites_Status Matching Case is NOT APPLICABLE");
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //OV_Phone_Found_Organization_Websites_Status validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("OV_Phone_Not_Found_Websites_Status")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        /*boolean firstPriority = Float.parseFloat(phoneValidationPriority)>=1.0 && Float.parseFloat(phoneValidationPriority)<=3.9;
                        boolean secondPriority = Float.parseFloat(phoneValidationPriority)>=4 && Float.parseFloat(phoneValidationPriority)<=6;
                        boolean thirdPriority = Float.parseFloat(phoneValidationPriority)>=7 && Float.parseFloat(phoneValidationPriority)<=9;*/
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //OV_Phone_Not_Found_Websites_Status validation remark starts here
                                if (OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("OV_Phone_Not_Found_Websites_Status->> QC passed: ORG site is getting populated in Not_Found_In column under OV. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                } else if (OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                    appendedText.append("OV_Phone_Not_Found_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                            "So OV_Phone_Not_Found_Websites_Status Matching Case is NOT APPLICABLE");
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //OV_Phone_Not_Found_Websites_Status validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //OV_Phone_Not_Found_Websites_Status validation remark starts here
                            if (OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("OV_Phone_Not_Found_Websites_Status->> QC passed: ORG site is getting populated in Not_Found_In column. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            } else if (OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("OV_Phone_Not_Found_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                        "So OV_Phone_Not_Found_Websites_Status Matching Case is NOT APPLICABLE");
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //OV_Phone_Not_Found_Websites_Status validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("OV_Phone_Not_Found_Organization_Websites_Status")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                                if (OV_PhoneNOTFoundORGWebsitesMatchingStatus.equalsIgnoreCase("No NEED")) {
                                    appendedText.append("OV_Phone_Not_Found_Organization_Websites_Status->> NO NEED: Because this is Accurate Case");
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                            //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                            if (OV_PhoneNOTFoundORGWebsitesMatchingStatus.equalsIgnoreCase("No NEED")) {
                                appendedText.append("OV_Phone_Not_Found_Organization_Websites_Status->> NO NEED: Because this is Accurate Case");
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }
            }

            } catch (Exception e) {

            }
        }
    }
    public void writeRemarks_Inaccurate(String R3TestResultFilePath, int executingRowIndex,
                                      String ORGNameMatchingStatus, String PhoneNumberMatchingStatus,
                                      String OVPhoneFoundWebsitesMatchingStatus, String OVPhoneFoundORGWebsitesMatchingStatus,
                                      String OVPhoneNOTFoundWebsitesMatchingStatus, boolean reverseFlag, String remarkScope,
                                      String combinedSearchKeyword_OrgProvPhone,String OV_PhoneNOTFoundORGWebsitesMatchingStatus) {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.THIN);
            greenCellStyle.setBorderBottom(BorderStyle.THIN);
            greenCellStyle.setBorderLeft(BorderStyle.THIN);
            greenCellStyle.setBorderRight(BorderStyle.THIN);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.THIN);
            redCellStyle.setBorderBottom(BorderStyle.THIN);
            redCellStyle.setBorderLeft(BorderStyle.THIN);
            redCellStyle.setBorderRight(BorderStyle.THIN);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.THIN);
            pinkCellStyle.setBorderBottom(BorderStyle.THIN);
            pinkCellStyle.setBorderLeft(BorderStyle.THIN);
            pinkCellStyle.setBorderRight(BorderStyle.THIN);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the test result comment to Remarks column
                if (columnHead.equalsIgnoreCase("Remarks")) {
                    if(remarkScope.equalsIgnoreCase("ORG-NAME")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //ORG Number validation remark starts here
                                if (ORGNameMatchingStatus.equalsIgnoreCase("PASS")) {
                                    if (reverseFlag) {
                                        appendedText.append("ORG-NAME->> QC Passed: ***Note:ORG link is reversely checked. Pls cross-check once***");
                                    }
                                } else if (ORGNameMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("ORG-NAME->> QC Inconclusive: ORG not found anywhere online. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                } else if (ORGNameMatchingStatus.equalsIgnoreCase("FAIL-BROKEN-WEBSITE")) {
                                    appendedText.append("ORG-NAME->> QC Inconclusive: Not able to get the Web content due to Broken Website Issue" + combinedSearchKeyword_OrgProvPhone);
                                }
                                //ORG Number validation remark ends here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //ORG Number validation remark starts here
                            if (ORGNameMatchingStatus.equalsIgnoreCase("PASS")) {
                                if (reverseFlag) {
                                    appendedText.append("ORG-NAME->> QC Passed: ***Note:ORG link is reversely checked. Pls cross-check once***");
                                }
                            } else if (ORGNameMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("ORG-NAME->> QC Inconclusive: ORG not found anywhere online. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            } else if (ORGNameMatchingStatus.equalsIgnoreCase("FAIL-BROKEN-WEBSITE")) {
                                appendedText.append("ORG-NAME->>: QC Inconclusive: Not able to get the Web content due to Broken Website Issue" + combinedSearchKeyword_OrgProvPhone);
                            } else if (ORGNameMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("ORG-NAME->> NP: NOT able to find the ORG NAME itself. " +
                                        "So Phone Number Matching Case is NOT APPLICABLE");
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //ORG Number validation remark ends here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("ORG-PHONE")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //Phone Number validation remark starts here
                                if (PhoneNumberMatchingStatus.equalsIgnoreCase("PASS")) {
                                    if (reverseFlag) {
                                        appendedText.append("ORG-PHONE->> QC Passed: ***Note:Phone Number Passed in reversely checked ORG link . Pls cross-check ORG link once***");
                                    }
                                } else if (PhoneNumberMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("ORG-PHONE->> QC Failed: Phone matched in ORG site but R3 says OV is Inaccurate.  Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                } else if (PhoneNumberMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                    appendedText.append("ORG-PHONE->> NP: NOT able to find the ORG NAME itself. So Phone Number Matching Case is NOT APPLICABLE. Search Keyword: >> "+ combinedSearchKeyword_OrgProvPhone);
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //Phone Number validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //Phone Number validation remark starts here
                            if (PhoneNumberMatchingStatus.equalsIgnoreCase("PASS")) {
                                if (reverseFlag) {
                                    appendedText.append("ORG-PHONE->> QC Passed: ***Note:Phone Number Passed in reversely checked ORG link . Pls cross-check ORG link once***");
                                }
                            } else if (PhoneNumberMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("ORG-PHONE->> QC Failed: Phone matched in ORG site but R3 says OV is Inaccurate. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            } else if (PhoneNumberMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("ORG-PHONE->> NP: NOT able to find the ORG NAME itself. So Phone Number Matching Case is NOT APPLICABLE. Search Keyword: >> "+ combinedSearchKeyword_OrgProvPhone);
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //Phone Number validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("OV_Phone_Not_Found_Websites_Status")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //OV_Phone_Not_Found_Websites_Status validation remark starts here
                                if (OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("OV_Phone_Not_Found_Websites_Status->> QC passed: error in OV_Not_Found column. " +
                                            "ORG site and its details not found by R3 under OV" + combinedSearchKeyword_OrgProvPhone);
                                } else if (OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                    appendedText.append("OV_Phone_Not_Found_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                            "So OV_Phone_Not_Found_Websites_Status Matching Case is NOT APPLICABLE"+ combinedSearchKeyword_OrgProvPhone);
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //OV_Phone_Not_Found_Websites_Status validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //OV_Phone_Not_Found_Websites_Status validation remark starts here
                            if (OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("OV_Phone_Not_Found_Websites_Status->> QC passed: error in OV_Not_Found column. " +
                                        "ORG site and its details not found by R3 under OV. Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            } else if (OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("OV_Phone_Not_Found_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                        "So OV_Phone_Not_Found_Websites_Status Matching Case is NOT APPLICABLE.Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //OV_Phone_Not_Found_Websites_Status validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("OV_Phone_Not_Found_Organization_Websites_Status")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                                if (OV_PhoneNOTFoundORGWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("OV_Phone_Not_Found_Organization_Websites_Status->> QC Passed: ORG site not classified as ORG by R3 under OV.Search Keyword: >> "
                                            + combinedSearchKeyword_OrgProvPhone);
                                }else if (OV_PhoneNOTFoundORGWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                    appendedText.append("OV_Phone_Not_Found_Organization_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                            "So OV_Phone_Not_Found_Websites_Status Matching Case is NOT APPLICABLE.Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                            if (OV_PhoneNOTFoundORGWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("OV_Phone_Not_Found_Organization_Websites_Status->> QC Passed: ORG site not classified as ORG by R3 under OV.Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            }else if (OV_PhoneNOTFoundORGWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("OV_Phone_Not_Found_Organization_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                        "So OV_Phone_Not_Found_Websites_Status Matching Case is NOT APPLICABLE.Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //OV_PhoneNOTFoundORGWebsitesMatchingStatus validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("OV_Phone_Found_Websites_Status")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //OV_Phone_Found_Websites validation remark starts here
                                if (OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                    appendedText.append("OV_Phone_Found_Websites_Status->> QC Passed: ORG site is getting populated in Found_In column.Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                } else if (OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                    appendedText.append("OV_Phone_Found_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                            "So OV_Phone_Found_Websites Matching Case is NOT APPLICABLE.Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //OV_Phone_Found_Websites validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //OV_Phone_Found_Websites validation remark starts here
                            //OV_Phone_Found_Websites validation remark starts here
                            if (OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
                                appendedText.append("OV_Phone_Found_Websites_Status->> QC Passed: ORG site is getting populated in Found_In column.Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            } else if (OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
                                appendedText.append("OV_Phone_Found_Websites_Status->> NP: NOT able to find the ORG NAME/PHONE NUMBER itself. " +
                                        "So OV_Phone_Found_Websites Matching Case is NOT APPLICABLE.Search Keyword: >> " + combinedSearchKeyword_OrgProvPhone);
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //OV_Phone_Found_Websites validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }

                    if(remarkScope.equalsIgnoreCase("OV_Phone_Found_Organization_Websites_Status")){
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                String existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                    WebSiteURLCellValue.setCellValue("");
                                    existingRemarks = WebSiteURLCellValue.getStringCellValue();
                                }
                                StringBuilder appendedText=null;
                                if(existingRemarks.isEmpty()){
                                    appendedText = new StringBuilder();
                                }else{
                                    appendedText = new StringBuilder(existingRemarks+ "\n");
                                }
                                //OV_Phone_Found_Organization_Websites_Status validation remark starts here
                                if (OVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("No NEED")) {
                                    appendedText.append("OV_Phone_Found_Organization_Websites_Status->> NO NEED: Because this is Inaccurate Case");
                                }
                                WebSiteURLCellValue.setCellValue(appendedText.toString());
                                //OV_Phone_Found_Organization_Websites_Status validation remark starts here
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                                break;
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            String existingRemarks = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                            if(existingRemarks.equalsIgnoreCase("BLANK | ")){
                                WebSiteURLCellValue.setCellValue("");
                                existingRemarks = WebSiteURLCellValue.getStringCellValue();
                            }
                            StringBuilder appendedText=null;
                            if(existingRemarks.isEmpty()){
                                appendedText = new StringBuilder();
                            }else{
                                appendedText = new StringBuilder(existingRemarks+ "\n");
                            }
                            //OV_Phone_Found_Organization_Websites_Status validation remark starts here
                            if (OVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("No NEED")) {
                                appendedText.append("OV_Phone_Found_Organization_Websites_Status->> NO NEED: Because this is Inaccurate Case");
                            }
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(appendedText.toString());
                            //OV_Phone_Found_Organization_Websites_Status validation remark starts here
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    }
                }

            } catch (Exception e) {

            }
        }
    }



   /* public void writeProviderNameMatchStatus(String R3TestResultFilePath, int executingRowIndex, String ProviderNameMatchingStatus,
                                             String phoneValidationPriority, String Url) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.HAIR);
            greenCellStyle.setBorderBottom(BorderStyle.HAIR);
            greenCellStyle.setBorderLeft(BorderStyle.HAIR);
            greenCellStyle.setBorderRight(BorderStyle.HAIR);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.HAIR);
            redCellStyle.setBorderBottom(BorderStyle.HAIR);
            redCellStyle.setBorderLeft(BorderStyle.HAIR);
            redCellStyle.setBorderRight(BorderStyle.HAIR);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.HAIR);
            pinkCellStyle.setBorderBottom(BorderStyle.HAIR);
            pinkCellStyle.setBorderLeft(BorderStyle.HAIR);
            pinkCellStyle.setBorderRight(BorderStyle.HAIR);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the Provider Name Matching status to ProviderNameMatchingStatus column
                if (columnHead.equalsIgnoreCase("Provider_Name_Matching_Status")) {
                    XSSFCell ProviderNameMatchingStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (ProviderNameMatchingStatusCellValue.getCellType() != null || ProviderNameMatchingStatusCellValue.getCellType() != CellType.BLANK) {
                            ProviderNameMatchingStatusCellValue.setCellValue("");
                            ProviderNameMatchingStatusCellValue.setCellValue(ProviderNameMatchingStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                            }
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(ProviderNameMatchingStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                        }
                    }
                }
                //Write the currently Executed URL to Provider_Name_Matching_URL column
                if (columnHead.equalsIgnoreCase("Provider_Name_Matching_URL")) {
                    XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    boolean oneToThree = Integer.parseInt(phoneValidationPriority)>=1 && Integer.parseInt(phoneValidationPriority)<=3;
                    boolean fourToSix = Integer.parseInt(phoneValidationPriority)>=4 && Integer.parseInt(phoneValidationPriority)<=6;
                    boolean sevenToNine = Integer.parseInt(phoneValidationPriority)>=7 && Integer.parseInt(phoneValidationPriority)<=9;
                    try {
                        if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                            WebSiteURLCellValue.setCellValue("");
                            if(oneToThree){
                                WebSiteURLCellValue.setCellValue(Url);
                            }else if(fourToSix){
                                WebSiteURLCellValue.setCellValue(Url);
                            }else if(sevenToNine){
                                WebSiteURLCellValue.setCellValue( Url);
                            }
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        if(oneToThree){
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url==null ? "None of the ORG Websites displayed the Provider Name!!! : ": Url);
                        }else if(fourToSix){
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue( Url);
                        }else if(sevenToNine) {
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue( Url);
                        }
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                        break;
                    }
                }


            } catch (Exception e) {

            }
        }
    }

    public void writeAddressMatchStatus(String R3TestResultFilePath, int executingRowIndex, String AddressMatchingStatus,
                                        String phoneValidationPriority, String Url) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            greenCellStyle = workbook.createCellStyle();
            greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenCellStyle.setBorderTop(BorderStyle.HAIR);
            greenCellStyle.setBorderBottom(BorderStyle.HAIR);
            greenCellStyle.setBorderLeft(BorderStyle.HAIR);
            greenCellStyle.setBorderRight(BorderStyle.HAIR);
            greenCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            greenCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setBorderTop(BorderStyle.HAIR);
            redCellStyle.setBorderBottom(BorderStyle.HAIR);
            redCellStyle.setBorderLeft(BorderStyle.HAIR);
            redCellStyle.setBorderRight(BorderStyle.HAIR);
            redCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            redCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            pinkCellStyle = workbook.createCellStyle();
            pinkCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
            pinkCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            pinkCellStyle.setBorderTop(BorderStyle.HAIR);
            pinkCellStyle.setBorderBottom(BorderStyle.HAIR);
            pinkCellStyle.setBorderLeft(BorderStyle.HAIR);
            pinkCellStyle.setBorderRight(BorderStyle.HAIR);
            pinkCellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            pinkCellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the Address Matching status to Address_Matching_Status column
                if (columnHead.equalsIgnoreCase("Address_Matching_Status")) {
                    XSSFCell AddressMatchingStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (AddressMatchingStatusCellValue.getCellType() != null || AddressMatchingStatusCellValue.getCellType() != CellType.BLANK) {
                            AddressMatchingStatusCellValue.setCellValue("");
                            AddressMatchingStatusCellValue.setCellValue(AddressMatchingStatus);
                            if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                            }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                                sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                            }
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(AddressMatchingStatus);
                        if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("PASS")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(greenCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("FAIL")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(redCellStyle);
                        }else if(sheet.getRow(executingRowIndex).getCell(j).getStringCellValue().equalsIgnoreCase("NOT APPLICABLE")){
                            sheet.getRow(executingRowIndex).getCell(j).setCellStyle(pinkCellStyle);
                        }
                    }
                }
                //Write the currently Executed URL to Address_Matching_URL column
                if (columnHead.equalsIgnoreCase("Address_Matching_URL")) {
                    XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    boolean oneToThree = Integer.parseInt(phoneValidationPriority)>=1 && Integer.parseInt(phoneValidationPriority)<=3;
                    boolean fourToSix = Integer.parseInt(phoneValidationPriority)>=4 && Integer.parseInt(phoneValidationPriority)<=6;
                    boolean sevenToNine = Integer.parseInt(phoneValidationPriority)>=7 && Integer.parseInt(phoneValidationPriority)<=9;
                    try {
                        if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                            WebSiteURLCellValue.setCellValue("");
                            if(oneToThree){
                                WebSiteURLCellValue.setCellValue( Url);
                            }else if(fourToSix){
                                WebSiteURLCellValue.setCellValue(Url);
                            }else if(sevenToNine){
                                WebSiteURLCellValue.setCellValue( Url);
                            }
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        if(oneToThree){
                            sheet.getRow(executingRowIndex).getCell(j)
                                    .setCellValue(Url==null ? "None of the ORG Websites displayed the Address!!! ":Url);
                        }else if(fourToSix){
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url);
                        }else if(sevenToNine) {
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url);
                        }
                        outFile = new FileOutputStream(R3TestResultFilePath);
                        workbook.write(outFile);
                        outFile.close();
                        break;
                    }
                }


            } catch (Exception e) {

            }
        }
    }*/

    String ORGNameMatchingStatusCellValue;
    String ProviderNameMatchingStatus;
    String PhoneNumberMatchingStatus;
    String AddressMatchingStatus;
    boolean allPassStatus=false;
    public boolean getTestResult(String R3TestResultFilePath, int executingRowIndex) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the Address Matching status to Address_Matching_Status column
                if (columnHead.equalsIgnoreCase("ORG_Name_Matching_Status")) {
                     ORGNameMatchingStatusCellValue = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                    if (sheet.getRow(0).getCell(j+2).toString().trim().equalsIgnoreCase("Phone_Number_Matching_Status")) {
                        PhoneNumberMatchingStatus = sheet.getRow(executingRowIndex).getCell(j+2).getStringCellValue();
                    }
                    try {
                        if(ORGNameMatchingStatusCellValue.equalsIgnoreCase("PASS") &&
                                PhoneNumberMatchingStatus.equalsIgnoreCase("PASS")){
                            allPassStatus = true;
                        }
                    } catch (NullPointerException e) {
                        System.out.println();
                    }
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return allPassStatus;
    }

    boolean anyFailStatus=false;
    public boolean getTestResultInAccurate(String R3TestResultFilePath, int executingRowIndex) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(R3TestResultFilePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
            try {
                String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                //Write the Address Matching status to Address_Matching_Status column
                if (columnHead.equalsIgnoreCase("ORG_Name_Matching_Status")) {
                    ORGNameMatchingStatusCellValue = sheet.getRow(executingRowIndex).getCell(j).getStringCellValue();
                    if (sheet.getRow(0).getCell(j+2).toString().trim().equalsIgnoreCase("Phone_Number_Matching_Status")) {
                        PhoneNumberMatchingStatus = sheet.getRow(executingRowIndex).getCell(j+2).getStringCellValue();
                    }
                    try {
                        if(ORGNameMatchingStatusCellValue.equalsIgnoreCase("FAIL") ||
                                PhoneNumberMatchingStatus.equalsIgnoreCase("FAIL")){
                            anyFailStatus = true;
                        }
                    } catch (NullPointerException e) {
                        System.out.println();
                    }
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return anyFailStatus;
    }

}
// Priority_Type
// Provider_and_Org_Phone_Validation_Type
// Organization_Phone_Validation_Type
// Provider_Phone_Validation_Type
// ORG_Name_Matching_Status	ORG_Name_Matching_URL
// Provider_Name_Matching_Status
// Provider_Name_Matching_URL
// Phone_Number_Matching_Status
// Phone_Number_Matching_URL
// Address_Matching_Status
// Address_Matching_URL
// Test_Result
// Remarks
