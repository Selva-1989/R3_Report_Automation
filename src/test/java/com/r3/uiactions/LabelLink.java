package com.r3.uiactions;

import com.r3.utility.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LabelLink {
	
	WebDriver driver = DriverFactory.getDriver();
	public static void clickLabelLink(WebElement labelLinkWebElement) {
		 labelLinkWebElement.click();
	}
	public static String getLabelText(WebElement labeltextWebElement) {
		return labeltextWebElement.getText();
	}

}
