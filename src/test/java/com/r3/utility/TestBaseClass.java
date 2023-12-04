package com.r3.utility;

import com.aventstack.extentreports.Status;
import com.r3.datareader.PropertiesFileReader;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestBaseClass {
	WebDriver driver;
	public int fileExcelCount=1;
	@BeforeMethod
	public void startWebDriverAndBrowser() throws IOException {
		try {
			FileInputStream fis = new FileInputStream(PropertiesFileReader.getProperty("InputExcelPath_" + fileExcelCount));
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			String sheetName = workbook.getSheetAt(0).getSheetName();
			XSSFSheet sheet = workbook.getSheetAt(0);
			String tcType = sheet.getRow(1).getCell(5).toString().trim();
			String testerName = sheet.getRow(1).getCell(6).toString().trim();
			String browserName = PropertiesFileReader.getProperty("BrowserName");
			if(browserName.equalsIgnoreCase("Chrome")) {
				String ProxyServer = "gate.smartproxy.com";
				int ProxyPort = 7000;
				String sHttpProxy = ProxyServer + ":" + ProxyPort;
				Proxy proxy = new Proxy();
				proxy.setHttpProxy(sHttpProxy);
				ChromeDriverService service = new ChromeDriverService.Builder()
						.usingDriverExecutable(new File( PropertiesFileReader.getProperty("ChromeDriverPath")))
						.usingAnyFreePort()
						.build();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				options.addArguments("--remote-allow-origins=*");
				options.setCapability("proxy", proxy);
				options.merge(options);
				System.setProperty("webdriver.chrome.driver", PropertiesFileReader.getProperty("ChromeDriverPath"));

				driver=new ChromeDriver(service, options);
			}
			DriverFactory.setDriver(driver);
			ExtentReport.createTest(sheetName,tcType,testerName, "Windows");

			if(PropertiesFileReader.getProperty("TestExecutionRecordings").equalsIgnoreCase("yes")) {
				MyScreenRecorder.startRecording(sheetName);
			}

			ExtentManager.getExtentTest().log(Status.INFO,"********************************STARTS THE EXECUTION********************************");
			Assert.assertTrue(driver != null);
			ExtentManager.getExtentTest().log(Status.INFO,browserName + " Browser is opened successfully");
			sheetName=null;tcType=null;testerName=null;
			fileExcelCount++;
			fis.close();
		} catch (Exception e) {
			String browserName = PropertiesFileReader.getProperty("BrowserName");
			ExtentManager.getExtentTest().log(Status.FAIL,browserName + " Browser is NOT opened yet due to  >>" +e.getMessage() );
		}
	}
	@BeforeSuite
	public void setupExtentReport() throws IOException {
		ExtentReport.initExtentReport();
	}
	@AfterSuite
	public void tearDownExtentReport(){
		ExtentReport.flushExtentReport();
	}

}

