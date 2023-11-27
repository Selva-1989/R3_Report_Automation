package com.r3.webelements;

import java.util.List;

public class GoogleSearchKeywordPage_WebElements {
	public static final String Const_googleSearchTextFiled ="//textarea[@id='APjFqb']";
	public static final String Const_googleSearchResultsList ="//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]/div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/span";
	public static final String getORGPROVIDERNameURL(int urlIdIndex){
		return "(//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]/div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}
	public static final String Const_webSiteBodyContent ="body";

}
