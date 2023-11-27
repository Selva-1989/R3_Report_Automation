package com.r3.utility;

import com.r3.datareader.PropertiesFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateExcelFile {
    static Path clonedR3File;
    public static String cloneR3Report() {

        try {
            Path originalR3File = Paths.get(PropertiesFileReader.getProperty("R3TestReportExcelPath"));
            clonedR3File = Paths.get(getNewFileName(PropertiesFileReader.getProperty("R3TestReportExcelPath")));
            Files.copy(originalR3File, clonedR3File);
            System.out.println("new excel file is created");

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
}
