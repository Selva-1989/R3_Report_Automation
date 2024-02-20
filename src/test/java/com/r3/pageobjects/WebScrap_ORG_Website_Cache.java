package com.r3.pageobjects;

import com.r3.utility.OrgWebsiteFinder;
import com.r3.utility.WriteR3TestResult;
import com.r3.webelements.GoogleSearchKeywordPage_WebElements;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class WebScrap_ORG_Website_Cache {
	WebDriver driver;
	String Url = null;
	WebDriverWait wait;
	public WebScrap_ORG_Website_Cache(WebDriver TestBaseClassDriver) {
		driver = TestBaseClassDriver;
		PageFactory.initElements(TestBaseClassDriver, this);
	}

	@FindBy(xpath= GoogleSearchKeywordPage_WebElements.Const_googleSearchTextFiled)
	public WebElement googleSearchTextFiled;
	@FindBy(xpath= GoogleSearchKeywordPage_WebElements.Const_googleSearchResultsList1)
	public List<WebElement> googleSearchResultsList1;
	@FindBy(xpath= GoogleSearchKeywordPage_WebElements.Const_googleSearchResultsList2)
	public List<WebElement> googleSearchResultsList2;
	@FindBy(tagName= GoogleSearchKeywordPage_WebElements.Const_webSiteContent)
	public WebElement webSiteContent;

	List<WebElement> linkHeaderList = null;
	String elementURLPath = null;
	String OrgURLFoundStatus = null;
	String finalORGUrl = null;
	public void check_ORG_Website_Cache_Accuracy(String clonedR3File, int executingRowIndex,LinkedHashSet<String> orgNameKey, String State, String Sites) throws IOException {
		WriteR3TestResult objWriteR3TestResult = new WriteR3TestResult();
		wait = new WebDriverWait(driver, 30);
		driver.manage().deleteAllCookies();
		driver.get("chrome://settings/clearBrowserData");
		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		driver.get("https://www.google.com");

		LinkedHashSet<String> orgNameMatchedURSetDataType = new LinkedHashSet<>();
		//1st Search String is "providerName + credentials + state AND phone number" P1
		String searchString1 = orgNameKey.toString()+" "+State;

		//Adding all Search String
		List<String> searchStringList = new ArrayList<>(Arrays.asList(searchString1));
		List<String> cleanedList = new ArrayList<>();
		for (String str : searchStringList) {
			// Remove the opening and closing brackets
			String cleanedString = str.substring(1, str.indexOf("]")) + str.substring(str.indexOf("]")+1);
			cleanedList.add(cleanedString);
		}

		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		boolean googleBackwardFlag=false;
		int eachWebLinkCount = 1;
		/** Multiple Search string operation starts here **/
		for(String eachSearchString : searchStringList) {
			if(googleBackwardFlag) {
				driver.navigate().back();
			}
			googleSearchTextFiled.sendKeys(cleanedList.get(0), Keys.ENTER);
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
			linkHeaderList = googleSearchResultsList1;
			elementURLPath = "getORGPROVIDERNameURL1";
			if (linkHeaderList.size() == 0) {
				linkHeaderList = googleSearchResultsList2;
				elementURLPath = "getORGPROVIDERNameURL2";
			}

			for (WebElement webElement : linkHeaderList) {
				String eachSearchLinkHeaderWithoutSpace = webElement.getText().toLowerCase().replace(" ", "").replace("-", "");
				for (String eachOrgNameKey : orgNameKey) {
					String orgNameKeyWithoutSpace = eachOrgNameKey.toLowerCase().replace(" ", "");
					if ((compareGivenStringsWithParticularPercentage(orgNameKeyWithoutSpace, eachSearchLinkHeaderWithoutSpace, 75.0) ||
							compareGivenStringsWithParticularPercentage(eachSearchLinkHeaderWithoutSpace, orgNameKeyWithoutSpace, 75.0))
							&& eachSearchLinkHeaderWithoutSpace.length() != 0 && !eachSearchLinkHeaderWithoutSpace.startsWith("facebook")
							&& !eachSearchLinkHeaderWithoutSpace.startsWith("linkedin") && !eachSearchLinkHeaderWithoutSpace.startsWith("Healthgrades")) {
						if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL1")) {
							Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL1(eachWebLinkCount))).getAttribute("href");
						} else if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL2")) {
							Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL2(eachWebLinkCount))).getAttribute("href");
						}
						if (!Url.toLowerCase().startsWith("https://www.facebook.")
								&& !Url.toLowerCase().startsWith("https://www.linkedin.")
								&& !Url.toLowerCase().startsWith("https://www.healthgrades")) {
							orgNameMatchedURSetDataType.add(Url);
							break;
						}
					} else {
						String[] eachWordOfR3OrgNameList = eachOrgNameKey.toLowerCase()
								.replace(" or ", "")
								.replace("  ", " ")//new
								.replace(" and ", "").split("\\s");
						List<String> wordList = new ArrayList<>(Arrays.asList("and", "or", "in", "for", "&", "of", "on", "is", "at", "has", "had", "a", "to"));// will have to update if any word
						String eachSearchLinkHeaderFirstWord = webElement.getText().toLowerCase().split("\\s")[0]; //only checking the First word of Link header
						String s2 = eachSearchLinkHeaderFirstWord;
						for (String s1 : eachWordOfR3OrgNameList) {
							if (!wordList.contains(s1)) {
								if (compareStringsWithGivenPercentage(s1, s2, 95.0)) {
									if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL1")) {
										Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL1(eachWebLinkCount))).getAttribute("href");
									} else if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL2")) {
										Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL2(eachWebLinkCount))).getAttribute("href");
									}
									if (!Url.toLowerCase().startsWith("https://www.facebook.")
											&& !Url.toLowerCase().startsWith("https://www.linkedin.")
											&& !Url.toLowerCase().startsWith("https://www.healthgrades")) {
										orgNameMatchedURSetDataType.add(Url);
										break;
									}
								}
							}
						}
					}
				}
				eachWebLinkCount++;
			}
			linkHeaderList=null;
			googleBackwardFlag=true;
			eachWebLinkCount = 1;
			if(orgNameMatchedURSetDataType.size()!=0){
				break;
			}
		}
		List<String> finalWebSiteList = new ArrayList<>(orgNameMatchedURSetDataType);

		if(finalWebSiteList.size()!=0) {
			try {
				OrgURLFoundStatus = "PASS";
				 finalORGUrl = OrgWebsiteFinder.WebsiteFinder_OrgCache(finalWebSiteList);
				objWriteR3TestResult.writeORGURLCache(clonedR3File, executingRowIndex, finalORGUrl,OrgURLFoundStatus );
			} catch (Exception e) {
			}
		}else if(finalWebSiteList.size()==0){
			OrgURLFoundStatus = "FAIL";
			finalORGUrl = "No ORG URL Found";
			objWriteR3TestResult.writeORGURLCache(clonedR3File, executingRowIndex, finalORGUrl,OrgURLFoundStatus );
		}
	}

	/*utility methods to compare the Link header string in goggle links and Search Key word ORG name*/
	public static boolean compareGivenStringsWithParticularPercentage(String str1, String str2, double requiredMatchPercentage) {
		String normalizedStr1 = str1.replaceAll(" ", "").toLowerCase();
		String normalizedStr2 = str2.replaceAll(" ", "").toLowerCase();
		return containsAtLeastPercentMatch(normalizedStr1, normalizedStr2, requiredMatchPercentage);
	}
	private static boolean containsAtLeastPercentMatch(String str1, String str2, double minMatchPercentage) {
		int minLength = str1.length();
		int substringLength = str2.length();
		for (int i = 0; i <= minLength - substringLength; i++) {
			String sub = str1.substring(i, i + substringLength);
			int commonCharCount = 0;
			for (int j = 0; j < substringLength; j++) {
				if (sub.charAt(j) == str2.charAt(j)) {
					commonCharCount++;
				}
			}
			double matchPercentage = (commonCharCount * 100.0) / substringLength;
			if (matchPercentage >= minMatchPercentage) {
				return true;
			}
		}
		return false;
	}

	public static boolean compareStringsWithGivenPercentage(String str1, String str2, double requiredMatchPercentage) {
		String normalizedStr1 = str1.replaceAll(" ", "").toLowerCase();
		String normalizedStr2 = str2.replaceAll(" ", "").toLowerCase();

		double similarity = calculateJaroWinklerSimilarity(normalizedStr1, normalizedStr2);
		//System.out.println("Similarity: " + similarity + "%");
		return similarity >= requiredMatchPercentage;
	}

	private static double calculateJaroWinklerSimilarity(String str1, String str2) {
		JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();
		return jaroWinklerSimilarity.apply(str1, str2) * 100;
	}

}



















