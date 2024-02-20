package com.r3.testcases;

import com.aventstack.extentreports.Status;
import com.r3.datareader.PropertiesFileReader;
import com.r3.providerUtility.ORGView;
import com.r3.providerUtility.ORG_WEBSITE_CACHE;
import com.r3.providerUtility.ProviderView;
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
    ORGView objORGView;
    ProviderView objPVView;
    ORG_WEBSITE_CACHE objORGWebsiteCache;
	WebDriver driver;
	public int ExcelCount=0;
	@Test(priority = 1, enabled = true)
	public void keywordEngine() throws Exception {
        ExtentManager.getExtentTest().log(Status.INFO, "Input Excel Keywords are Started driving the Automation");
        ExcelCount++;
		driver  = DriverFactory.getDriver();
		objORGView = new ORGView(driver);
        objPVView = new ProviderView(driver);
        objORGWebsiteCache = new ORG_WEBSITE_CACHE(driver);
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
                String Execution = sheet.getRow(i + 1).getCell(ColumnsCount + 4).toString().trim();
                if (Execution.equalsIgnoreCase("Yes")) {
                    switch (Action_Keywords) {
                        case "Verify_All_Buckets_ORG_PHONE":
                            ExtentManager.getExtentTest().log(Status.INFO,"Started Executing Test Scenario Verify_All_Buckets_ORG_PHONE");
                            ExtentManager.getExtentTest().log(Status.INFO,"No of Rows that you selected to execute from R3 Test Report Excel is " + No_of_R3_Excel_Rows_to_Execute);
                            objORGView.Verify_All_Buckets_ORG_PHONE(Integer.parseInt(No_of_R3_Excel_Rows_to_Execute));
                            break;
                        case "Verify_All_Buckets_ORG_PV_PHONE":
                            ExtentManager.getExtentTest().log(Status.INFO,"Started Executing Test Scenario Verify_All_Buckets_ORG_PV_PHONE");
                            ExtentManager.getExtentTest().log(Status.INFO,"No of Rows that you selected to execute from R3 Test Report Excel is " + No_of_R3_Excel_Rows_to_Execute);
                            objPVView.Verify_All_Buckets_ORG_PV_PHONE(Integer.parseInt(No_of_R3_Excel_Rows_to_Execute));
                            break;
                        case "Verify_ORG_WEBSITE_CACHE":
                            ExtentManager.getExtentTest().log(Status.INFO,"Started Executing Test Scenario Verify_ORG_WEBSITE_CACHE");
                            ExtentManager.getExtentTest().log(Status.INFO,"No of Rows that you selected to execute from R3 Test Report Excel is " + No_of_R3_Excel_Rows_to_Execute);
                            objORGWebsiteCache.Verify_ORG_WEBSITE_CACHE(Integer.parseInt(No_of_R3_Excel_Rows_to_Execute));
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
            if(PropertiesFileReader.getProperty("TestExecutionRecordings").equalsIgnoreCase("yes")){
                MyScreenRecorder.stopRecording();
            }

        }
    }

}
//<listener class-name="com.hilabsdartui.listeners.AllureListeners"></listener>