package com.r3.utility;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
	public static WebDriver driver;
	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver localDriver) {
	driver = localDriver;
	}
}
