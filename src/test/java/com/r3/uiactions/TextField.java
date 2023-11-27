package com.r3.uiactions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.r3.utility.DriverFactory;

public class TextField {
	
	WebDriver driver = DriverFactory.getDriver();
	public static void clearTextField(WebElement txtFieldWebElement) {
		txtFieldWebElement.clear();
	}
	public static void inputTextField(WebElement txtFieldWebElement, String input) {
		txtFieldWebElement.sendKeys(input);
	}
	public static void inputTextFieldWithEnterKey(WebElement txtFieldWebElement, String input) {
		txtFieldWebElement.sendKeys(input, Keys.ENTER);
	}
	public static String getInputFromTextField(WebElement txtFieldWebElement) {
		return txtFieldWebElement.getText();
	}
	public static String getInputFromTextField(WebElement txtFieldWebElement, String attributeValue) {
		return txtFieldWebElement.getAttribute(attributeValue);
	}

}
