package com.r3.testcases;

import com.aventstack.extentreports.Status;
import com.r3.datareader.PropertiesFileReader;
import com.r3.providerUtility.R3Phone;
import com.r3.utility.DriverFactory;
import com.r3.utility.ExtentManager;
import com.r3.utility.MyScreenRecorder;
import com.r3.utility.TestBaseClass;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class ActionsKeywords extends TestBaseClass {
	public FileInputStream fis = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	R3Phone objR3Phone;
	WebDriver driver;
	public int ExcelCount=0;
	@Test(priority = 1, enabled = true)
	public void keywordEngine() throws Exception {
        ExtentManager.getExtentTest().log(Status.INFO, "Input Excel Keywords are Started driving the Automation");
        ExcelCount++;
		driver  = DriverFactory.getDriver();
		objR3Phone = new R3Phone(driver);
		try {
			fis = new FileInputStream(PropertiesFileReader.getProperty("InputExcelPath_"+ExcelCount));
			workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            ExtentManager.getExtentTest().log(Status.INFO,"You are Executing Test Scenarios from the Excels Sheet >>> " + sheet.getSheetName());
            int ColumnsCount = 0;
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                String Test_Steps = sheet.getRow(i + 1).getCell(ColumnsCount + 1).toString().trim();
                String Action_Keywords = sheet.getRow(i + 1).getCell(ColumnsCount + 2).toString().trim();
                String No_of_R3_Excel_Rows_to_Execute = sheet.getRow(i + 1).getCell(ColumnsCount + 3).toString().trim();
                String Priority = sheet.getRow(i + 1).getCell(ColumnsCount + 4).toString().trim();
                String Execution = sheet.getRow(i + 1).getCell(ColumnsCount + 5).toString().trim();
                if (Execution.equalsIgnoreCase("Yes")) {
                    switch (Action_Keywords) {
                        case "Verify_Accurate_Bucket_ORG_PROV_Phone":
                            ExtentManager.getExtentTest().log(Status.INFO,"Currently Executing Test Scenario Name Verify_Accurate_Bucket_ORG_PROV_Phone and Priority is " + Priority);
                            ExtentManager.getExtentTest().log(Status.INFO,"No of Rows that you selected to execute from R3 Test Report Excel is " + No_of_R3_Excel_Rows_to_Execute);
                            objR3Phone.Verify_Accurate_Bucket_ORG_PROV_Phone(Integer.parseInt(No_of_R3_Excel_Rows_to_Execute), Priority);
                            break;

                    }
                } else if (Execution.equalsIgnoreCase("No")) {
                    ExtentManager.getExtentTest().log(Status.INFO, "Test Step (" +Test_Steps + ") Execution Status is Mentioned as = (NO)");
                }

            }
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //MyScreenRecorder.stopRecording();
        }
    }

}
//<listener class-name="com.hilabsdartui.listeners.AllureListeners"></listener>