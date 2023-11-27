package com.r3.uiactions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.r3.utility.DriverFactory;

public class Button {
	
	WebDriver driver = DriverFactory.getDriver();
	
	public static void clickBtn(WebElement BtnWebElement) {
		BtnWebElement.click();
	}
	
	public static String getBtnName(WebElement BtnWebElement) {
		return BtnWebElement.getText();
	}

}
