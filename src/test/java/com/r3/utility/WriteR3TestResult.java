package com.r3.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteR3TestResult {
    FileOutputStream outFile;
    XSSFWorkbook workbook;
    XSSFSheet sheet;

    public void writeBasicDataForValidation(String R3TestResultFilePath, int executingRowIndex, String phoneValidationPriority,
                                            String ProviderAndOrgPhoneValidation, String OrganizationPhoneValidation,
                                            String ProviderPhoneValidation) throws IOException {
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
                                PriorityTypeCellValue.setCellValue("");
                                PriorityTypeCellValue.setCellValue(phoneValidationPriority);
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(phoneValidationPriority);
                        }
                    }
                    //Write the Provider_and_Org_Phone_Validation value to Provider_and_Org_Phone_Validation_Type column
                    if (columnHead.equalsIgnoreCase("Provider_and_Org_Phone_Validation_Type")) {
                        XSSFCell ProviderAndOrgPhoneValidationTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (ProviderAndOrgPhoneValidationTypeCellValue.getCellType() != null || ProviderAndOrgPhoneValidationTypeCellValue.getCellType() != CellType.BLANK) {
                                ProviderAndOrgPhoneValidationTypeCellValue.setCellValue("");
                                ProviderAndOrgPhoneValidationTypeCellValue.setCellValue(ProviderAndOrgPhoneValidation);
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(ProviderAndOrgPhoneValidation);
                        }
                    }
                    //Write the Organization_Phone_Validation value to Organization_Phone_Validation_Type column
                    if (columnHead.equalsIgnoreCase("Organization_Phone_Validation_Type")) {
                        XSSFCell OrganizationPhoneValidationTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (OrganizationPhoneValidationTypeCellValue.getCellType() != null || OrganizationPhoneValidationTypeCellValue.getCellType() != CellType.BLANK) {
                                OrganizationPhoneValidationTypeCellValue.setCellValue("");
                                OrganizationPhoneValidationTypeCellValue.setCellValue(OrganizationPhoneValidation);
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(OrganizationPhoneValidation);
                        }
                    }
                    //Write the Provider_Phone_Validation value  to Provider_Phone_Validation_Type column
                    if (columnHead.equalsIgnoreCase("Provider_Phone_Validation_Type")) {
                        XSSFCell ProviderPhoneValidationTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (ProviderPhoneValidationTypeCellValue.getCellType() != null || ProviderPhoneValidationTypeCellValue.getCellType() != CellType.BLANK) {
                                ProviderPhoneValidationTypeCellValue.setCellValue("");
                                ProviderPhoneValidationTypeCellValue.setCellValue(ProviderPhoneValidation);
                                outFile = new FileOutputStream(R3TestResultFilePath);
                                workbook.write(outFile);
                                outFile.close();
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(ProviderPhoneValidation);
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
    public void writeORGNameMatchStatus(String R3TestResultFilePath, int executingRowIndex, String ORGNameMatchingStatus,
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
                                WebSiteURLCellValue.setCellValue(Url);
                            }
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        if(oneToThree){
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url==null ? "None of the ORG Websites displayed the ORG Name!!! " :  Url);
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

    public void writePhoneNumberMatchStatus(String R3TestResultFilePath, int executingRowIndex, String PhoneNumberMatchingStatus,
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
                                WebSiteURLCellValue.setCellValue(Url);
                            }
                            outFile = new FileOutputStream(R3TestResultFilePath);
                            workbook.write(outFile);
                            outFile.close();
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        if(oneToThree){
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url==null ? "None of the ORG Websites displayed the Phone Number!!!": Url);
                        }else if(fourToSix){
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url);
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

    public void writePhoneNumberFoundURLMatchStatus(String R3TestResultFilePath, int executingRowIndex, String WebSiteFoundMatchingStatus) throws IOException {
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
                //Write the Url Matching status to PV_OV_Phone_Found_Websites_Status column
                if (columnHead.equalsIgnoreCase("PV_OV_Phone_Found_Websites_Status")) {
                    XSSFCell PV_OV_PhoneFoundWebSitesStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (PV_OV_PhoneFoundWebSitesStatusCellValue.getCellType() != null || PV_OV_PhoneFoundWebSitesStatusCellValue.getCellType() != CellType.BLANK) {
                            PV_OV_PhoneFoundWebSitesStatusCellValue.setCellValue("");
                            PV_OV_PhoneFoundWebSitesStatusCellValue.setCellValue(WebSiteFoundMatchingStatus);
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
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(WebSiteFoundMatchingStatus);
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
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    public void writePhoneNumberNOTFoundURLMatchStatus(String R3TestResultFilePath, int executingRowIndex, String WebSiteNOTFoundMatchingStatus) throws IOException {
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
                //Write the Url Matching status to PV_OV_Phone_Found_Websites_Status column
                if (columnHead.equalsIgnoreCase("PV_OV_Phone_Not_Found_Websites_Status")) {
                    XSSFCell PV_OV_PhoneNOTFoundWebSitesStatusCellValue = sheet.getRow(executingRowIndex).getCell(j);
                    try {
                        if (PV_OV_PhoneNOTFoundWebSitesStatusCellValue.getCellType() != null || PV_OV_PhoneNOTFoundWebSitesStatusCellValue.getCellType() != CellType.BLANK) {
                            PV_OV_PhoneNOTFoundWebSitesStatusCellValue.setCellValue("");
                            PV_OV_PhoneNOTFoundWebSitesStatusCellValue.setCellValue(WebSiteNOTFoundMatchingStatus);
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
                            break;
                        }
                    } catch (NullPointerException e) {
                        sheet.getRow(executingRowIndex).createCell(j);
                        sheet.getRow(executingRowIndex).getCell(j).setCellValue(WebSiteNOTFoundMatchingStatus);
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
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    public void writeProviderNameMatchStatus(String R3TestResultFilePath, int executingRowIndex, String ProviderNameMatchingStatus,
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
    }


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
                    if (sheet.getRow(0).getCell(j+2).toString().trim().equalsIgnoreCase("Provider_Name_Matching_Status")) {
                        ProviderNameMatchingStatus = sheet.getRow(executingRowIndex).getCell(j+2).getStringCellValue();
                    }
                    if (sheet.getRow(0).getCell(j+4).toString().trim().equalsIgnoreCase("Phone_Number_Matching_Status")) {
                        PhoneNumberMatchingStatus = sheet.getRow(executingRowIndex).getCell(j+4).getStringCellValue();
                    }
                    if (sheet.getRow(0).getCell(j+6).toString().trim().equalsIgnoreCase("Address_Matching_Status")) {
                        AddressMatchingStatus = sheet.getRow(executingRowIndex).getCell(j+6).getStringCellValue();
                    }
                    try {
                        if(ORGNameMatchingStatusCellValue.equalsIgnoreCase("PASS") &&
                                ProviderNameMatchingStatus.equalsIgnoreCase("PASS") &&
                                PhoneNumberMatchingStatus.equalsIgnoreCase("PASS") &&
                                AddressMatchingStatus.equalsIgnoreCase("PASS")){
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
