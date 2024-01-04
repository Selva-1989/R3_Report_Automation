package com.r3.webelements;

import java.util.List;

public class GoogleSearchKeywordPage_WebElements {
	public static final String Const_googleSearchTextFiled ="//textarea[@id='APjFqb']";
	public static final String Const_googleSearchResultsList1 ="//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]/div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/span";
	public static final String getORGPROVIDERNameURL1(int urlIdIndex){
		return "(//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]/div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}
	public static final String Const_googleSearchResultsList2 = "(//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]//div[@jscontroller='m1Ro8b'])[1]/div[3]//div[starts-with(@class,'TzHB6b')]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/span";
	public static final String getORGPROVIDERNameURL2(int urlIdIndex){
		return "((//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]//div[@jscontroller='m1Ro8b'])[1]/div[3]//div[starts-with(@class,'TzHB6b')]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}
	public static final String Const_webSiteContent ="html";

}
