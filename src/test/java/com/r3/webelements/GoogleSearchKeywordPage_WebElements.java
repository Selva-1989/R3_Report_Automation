package com.r3.webelements;

public class GoogleSearchKeywordPage_WebElements {
	public static final String Const_googleSearchTextFiled ="//textarea[@id='APjFqb']";

	//To get only ORG URLs
	public static final String Const_googleSearchResultsList1 ="//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div[@class='GTRloc']/span";
	//old//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/span

	public static final String getORGPROVIDERNameURL1(int urlIdIndex){
		return "(//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}
	public static final String Const_googleSearchResultsList2 = "(//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]//div[@jscontroller='m1Ro8b'])[1]/div[3]//div[starts-with(@class,'TzHB6b')]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/span";
	public static final String getORGPROVIDERNameURL2(int urlIdIndex){
		return "((//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]//div[@jscontroller='m1Ro8b'])[1]/div[3]//div[starts-with(@class,'TzHB6b')]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}
	public static final String Const_googleSearchResultsList3 ="//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]/div[@class='g']//child::a//child::div/div/div[@class='GTRloc']/span";

	public static final String getORGPROVIDERNameURL3(int urlIdIndex){
		return "(//div[@class='MjjYud' and not (ancestor::div[@id='botstuff'])]/div[@class='g']//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}
	public static final String Const_googleSearchResultsList4 ="//div[@class='MjjYud' and (ancestor::div[@id='botstuff'])]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div[@class='GTRloc']/span";
	public static final String getORGPROVIDERNameURL4(int urlIdIndex){
		return "(//div[@class='MjjYud' and (ancestor::div[@id='botstuff'])]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}




	public static final String Const_webSiteContent ="html";

	//To get all the URLs for Other ORG
	public static final String Const_googleSearchResultsList3_OtherORG ="//div[@class='MjjYud']//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/span";
	public static final String getORGPROVIDERNameURL3_OtherORG(int urlIdIndex){
		return "(//div[@class='MjjYud']//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}

	public static final String Const_googleSearchResultsList4_OtherORG  = "(//div[@class='MjjYud']//div[@jscontroller='m1Ro8b'])[1]/div[3]//div[starts-with(@class,'TzHB6b')]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/span";
	public static final String getORGPROVIDERNameURL24_OtherORG (int urlIdIndex){
		return "((//div[@class='MjjYud']//div[@jscontroller='m1Ro8b'])[1]/div[3]//div[starts-with(@class,'TzHB6b')]//div[@jscontroller='SC7lYd' and not(@jscontroller='Da4hkd')]//child::a//child::div/div/div/cite//ancestor::a[@jsname='UWckNb'])["+(urlIdIndex)+"]";
	}

	//To get all the URLs for ORG Cache
	public static final String Const_googleSideBarORGTitle ="//div[@data-attrid='title']";
	public static final String Const_googleSideBarORGWebSiteURL ="(//div[@class='IFmkIb ']//parent::a)[1]";
	public static final String Const_googleSideBarStyle2RGTitle ="//h2[@data-attrid='title']";
	public static final String Const_googleSideBarStyle2ORGWebSiteURL ="(//div[@class='QqG1Sd']//parent::a)[1]";

	/*public static final String Const_googlePlacesORGLinkHeader ="//div[@jsname='SaftMd']/div//child::span[@class='OSrXXb']";
	public static final String getGooglePlacesORGWebsiteURL(int urlIdIndex){
		return "((//div[@jsname='SaftMd']/div//child::span[@class='OSrXXb'])["+(urlIdIndex)+"]/following::a)[1]";
	}*/

	public static final String Const_googlePlacesORGLinkHeader ="//div[@jsname='jXK9ad']/div//child::span[@class='OSrXXb']";
	public static final String getGooglePlacesORGWebsiteURL(int urlIdIndex){
		return "((//div[@jsname='jXK9ad']/div//child::span[@class='OSrXXb'])["+(urlIdIndex)+"]/following::a)[1]";
	}






}
