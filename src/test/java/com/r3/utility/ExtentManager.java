package com.r3.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;

public class ExtentManager {
	public ExtentReports extentReport;
	public ExtentSparkReporter sparkReporter;
	public ExtentTest extentLogger;
	WebDriver driver;

	private ExtentManager(){};
	public static ThreadLocal<ExtentTest> extTest = new ThreadLocal<>();
	public static ExtentTest getExtentTest(){
		return extTest.get();
	}
	public static void setExtentTest(ExtentTest test){
		extTest.set(test);
	}
	public static void unload(){
		extTest.remove();
	}
}
