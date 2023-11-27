package com.r3.uiactions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.r3.utility.DriverFactory;

public class DropDown {
	
	WebDriver driver = DriverFactory.getDriver();
	
	public static String getInputFromTextField(WebElement DropDowndWebElement)
	{
		return DropDowndWebElement.getText();
	}
	public static void expandDropDown(WebElement DropDowndWebElement)
	{
		DropDowndWebElement.click();
	}
	public static void inputDropDownField(WebElement dropDownFieldWebElement, String input) {
		dropDownFieldWebElement.sendKeys(input);
	}

}
