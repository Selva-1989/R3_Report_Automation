package com.r3.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ExtentReport {
	private ExtentReport(){};
	private static ExtentReports extentReport;
	public static void initExtentReport() {
		if(Objects.isNull(extentReport)) {
			extentReport = new ExtentReports();
			ExtentSparkReporter  sparkReporter = new ExtentSparkReporter(new File(System.getProperty("user.dir") + "\\Reports\\extentReport.html"));
			sparkReporter.config().setDocumentTitle("R3 Report Test Automation ");
			sparkReporter.config().setReportName("R3 Report Test Automation Report");
			sparkReporter.config().setTheme(Theme.DARK);
			extentReport.attachReporter(sparkReporter);
		}
	}
	public static void flushExtentReport(){
		if(Objects.nonNull(extentReport)) {
			extentReport.flush();
		}
	}
	public static void createTest(String testCaseName, String testerName, String testCaseType, String device){
		ExtentTest extLogger = extentReport.createTest(testCaseName)
									.assignAuthor(testerName)
									.assignCategory(testCaseType)
									.assignDevice(device);
		ExtentManager.setExtentTest(extLogger);
	}
}
