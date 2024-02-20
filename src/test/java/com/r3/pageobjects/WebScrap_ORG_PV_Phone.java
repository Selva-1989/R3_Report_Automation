package com.r3.pageobjects;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.r3.utility.*;
import com.r3.webelements.GoogleSearchKeywordPage_WebElements;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WebScrap_ORG_PV_Phone {
	WebDriver driver;
	String Url = null;
	Map<String,String> provPhoneMap = null;
	Map<String,String> provPhoneMapDummy = null;
	WebDriverWait wait;
	public WebScrap_ORG_PV_Phone(WebDriver TestBaseClassDriver) {
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
	public void check_ORG_PV_Phone_Accurate(String clonedR3File, int executingRowIndex, String phoneValidationPriority,
										 String PvPhoneValidation, String providerName, String credentials, String state,
										 LinkedHashSet<String> orgNameKey, String areaCode, String exchangeCode, String lineNumber,
										 Set<String> PV_Phone_Found_Websites, Set<String> PV_Phone_Found_Organization_Websites,
										 Set<String> PV_Phone_Not_Found_Websites, String address, String firstName,
										 String actualMiddleName, String lastName) {
		WriteR3TestResult objWriteR3TestResult = new WriteR3TestResult();
		wait = new WebDriverWait(driver, 30);
		try {
			driver.manage().deleteAllCookies();
			driver.get("chrome://settings/clearBrowserData");
			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
			driver.get("https://www.google.com");

			LinkedHashSet<String> orgNameMatchedURSetDataType = new LinkedHashSet<>();
			//1st Search String is "providerName + credentials + state AND phone number" P1
			String searchString1 = providerName+" "+credentials+" "+state+" AND "+areaCode+" "+exchangeCode+" "+lineNumber;

			//2nd Search string OrgNameStringWithoutORLast+" AND "+ phone number
			StringBuilder orgNameStringBuilder = new StringBuilder();
			String OrgNameStringWithoutORLast=null;
			for (String eachOrgNameKey : orgNameKey) {
				orgNameStringBuilder.append(eachOrgNameKey).append(" OR ");
			}
			if (orgNameStringBuilder.length() > 0) {
				OrgNameStringWithoutORLast = orgNameStringBuilder.substring(0, orgNameStringBuilder.length() - 4);
			}
			String searchString2 = OrgNameStringWithoutORLast+" AND "+areaCode+" "+exchangeCode+" "+lineNumber; //2nd Search String

			//Adding all Search String
			List<String> searchStringList = new ArrayList<>(Arrays.asList(searchString1, searchString2));
			StringBuilder result = new StringBuilder();
			for (String str : searchStringList) {
				result.append(str).append(" || ");
			}
			if (!searchStringList.isEmpty()) {
				result.delete(result.length() - 4, result.length());
			}
			// To print the All Search String in remarks column for failed case
			String allSearchString = result.toString();

			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
			boolean googleBackwardFlag=false;
			int eachWebLinkCount = 1;
			/** Multiple Search string operation starts here **/
			for(String eachSearchString : searchStringList) {
				if(googleBackwardFlag) {
					driver.navigate().back();
				}
				googleSearchTextFiled.sendKeys(eachSearchString, Keys.ENTER);
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
									&& !Url.toLowerCase().startsWith("https://www.healthgrades")
									&& !Url.toLowerCase().startsWith("https://www.healthcare6")
									&& !Url.toLowerCase().contains(".gov/")) {
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
												&& !Url.toLowerCase().startsWith("https://www.healthgrades")
												&& !Url.toLowerCase().startsWith("https://www.healthcare6")
												&& !Url.toLowerCase().contains(".gov/")) {
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
			/** Multiple Search string operation starts here **/

			/** Converting the SET to LIST to iterate. This LIST may have inappropriate URL too.
			 If orgNameMatchedURSetDataType is Empty, we have to  Use REVERSE Model to get the URL from PV_Phone_Found_Organization_Websites**/
			List<String> orgNameURListWithAllWebSites = new ArrayList<>(orgNameMatchedURSetDataType); // Stage 1 to Store Matched URL

			//if R3 ORG Name is not matching with search weblink header, we need to compare the websites list from PV_Phone_Found_Organization_Websites column reverse order
			//REVERSE Model URL match starts here
			boolean reverseURLMatchFlag=false;
			if(orgNameMatchedURSetDataType.size()==0){
				for(String eachSearchString : searchStringList) {
					driver.navigate().back();
					googleSearchTextFiled.sendKeys(eachSearchString, Keys.ENTER);
					wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
					linkHeaderList = googleSearchResultsList1;
					elementURLPath = "getORGPROVIDERNameURL1";
					if (linkHeaderList.size() == 0) {
						linkHeaderList = googleSearchResultsList2;
						elementURLPath = "getORGPROVIDERNameURL2";
					}
					List<String> allSearchURLsList = new ArrayList<>();
					for (int i = 1; i <= linkHeaderList.size(); i++) {
						if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL1")) {
							allSearchURLsList.add(driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL1(i))).getAttribute("href"));
						} else if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL2")) {
							allSearchURLsList.add(driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL2(i))).getAttribute("href"));
						}
					}
					for (String eachURL : allSearchURLsList) {
						// PV_Phone_Found_Organization_Websites columns for revers order
						if (get_AllMatchingORGWebsiteList(PV_Phone_Found_Organization_Websites, eachURL)) {
							orgNameURListWithAllWebSites.add(eachURL);
						}
					}
					reverseURLMatchFlag = true;
					if(orgNameMatchedURSetDataType.size()!=0){
						break;
					}
				}
			}
			//REVERSE Model URL match ends here
			ExtentManager.getExtentTest().log(Status.INFO, ("Search Keyword ->> " + allSearchString ));
			List<String> orgNameMatchedURList=null;

			if(orgNameURListWithAllWebSites.isEmpty()||orgNameURListWithAllWebSites.size()==0){
				ExtentManager.getExtentTest().log(Status.FAIL,("We are not able to find the ORG Web Site link with this search keyword ->> "+ allSearchString),
						MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
				objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,PvPhoneValidation);
				String ORGNameMatchingStatus = "FAIL";
				objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,Url);
				String ProviderNameMatchingStatus = "NOT APPLICABLE"; //new
				objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File,executingRowIndex,ProviderNameMatchingStatus,Url);//new
				String PhoneNumberMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,Url);

				String PV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
				String PV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
						PV_PhoneFoundWebsitesMatchingStatus,PV_PhoneFoundOrgWebsitesMatchingStatus);

				String PV_PhoneNOTWebsiteMatchingStatus = "NOT APPLICABLE";
				String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
				objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
						PV_PhoneNOTWebsiteMatchingStatus,PV_PhoneNOTFoundORGWebsitesMatchingStatus);

				PhoneNumberMatchingStatus = "No-Need";
				String PVPhoneFoundWebsitesMatchingStatus = "No-Need";
				String PVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
				String PVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
				PV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
                String remarkScope = "ORG-NAME";
                objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
                        ProviderNameMatchingStatus, PhoneNumberMatchingStatus,PVPhoneFoundWebsitesMatchingStatus,
                        PVPhoneFoundORGWebsitesMatchingStatus,PVPhoneNOTFoundWebsitesMatchingStatus,
                        reverseURLMatchFlag, remarkScope,allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
                        provPhoneMapDummy, "dummyPhoneNumber", Url);
				ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
			}
			else{
				//if the orgNameURListWithAllWebSites is NOT crossed the Reverse check process then you verify the URL with Domain Name or
				// should not start all the found URL s with below Domain name and URL check process
				if(!reverseURLMatchFlag) {
					/** Sometime ORG NAME words will be located in te URL HEADER. Because of that , we will get the inappropriate URL also.
					 To Resolve this, We have to Match the Selected URL List with R3 report ORG NAMEs
					 To Filter the correct URL the below code will be used **/
					Set<String> finalUniqueURLSet = new LinkedHashSet<>();
					for (String eachFirstRoundMatchedURL : orgNameURListWithAllWebSites) {
				    /*This If loop will automatically include URL from PV_Phone_Found_Organization_Websites,
					if PV_Phone_Found_Organization_Websites's URL and our orgNameURListWithAllWebSites URL is matching*/
						if (get_AllMatchingORGWebsiteList(PV_Phone_Found_Organization_Websites, eachFirstRoundMatchedURL)) {
							finalUniqueURLSet.add(eachFirstRoundMatchedURL);
						}
						String extractedURLString = eachFirstRoundMatchedURL
								.replaceAll("https?://(?:www\\.)?(\\w+\\.\\w+).*", "$1")
								.replaceAll("\\.\\w+", "");
						for (String eachOrgNameKeyR3 : orgNameKey) {
							String orgNameKeyWithoutSpace = eachOrgNameKeyR3.toLowerCase().replace(" ", "");
							if ((compareGivenStringsWithParticularPercentage(orgNameKeyWithoutSpace, extractedURLString, 75.0) ||
									compareGivenStringsWithParticularPercentage(extractedURLString, orgNameKeyWithoutSpace, 75.0))
									&& extractedURLString.length() != 0 && !extractedURLString.startsWith("facebook")
									&& !extractedURLString.startsWith("linkedin")
									&& !extractedURLString.startsWith("Healthgrades")
									&& !extractedURLString.startsWith(".gov")) {
								for (String finalURL : filterSimilarWebsites(orgNameURListWithAllWebSites, extractedURLString)) {
									finalUniqueURLSet.add(finalURL);
								}
								break;
							} else {
								String[] eachWordOfR3OrgNameList = eachOrgNameKeyR3.toLowerCase()
										.replace(" or ", "")
										.replace(" and ", "").split("\\s");
								extractedURLString = extractedURLString
										.replaceAll("https?://", "")
										.replaceAll("/$", "");
								String[] urlStringArray = extractedURLString.toLowerCase().replace("-", " ").split("\\s");
								for (String urlString : urlStringArray) {
									for (String eachWordORGNAme : eachWordOfR3OrgNameList) {
										if ((compareStringsWithGivenPercentage(urlString, eachWordORGNAme, 90.0) || urlString.contains(eachWordORGNAme))
												&& extractedURLString.length() != 0 && !extractedURLString.startsWith("facebook")
												&& !extractedURLString.startsWith("linkedin")
												&& !extractedURLString.startsWith("Healthgrades")
												&& !extractedURLString.startsWith(".gov")) {
											for (String finalURL : filterSimilarWebsites(orgNameURListWithAllWebSites, extractedURLString)) {
												finalUniqueURLSet.add(finalURL);
											}
											break;
										}
									}
								}
							}
						}
					}
					/**Because of Filtering the URL list with R3 report's ORG NAME (As per above code), We might not get any URL sometimes.
					 So again if ORG Name Match = Matched but ORG URL = Not Matched,
					 then DO THE REVERSE CHECK with PV_Phone_Found_Organization_Websites column value again**/
					if (finalUniqueURLSet.size() == 0) {
						for (String eachURL : orgNameURListWithAllWebSites) {
							if (get_AllMatchingORGWebsiteList(PV_Phone_Found_Organization_Websites, eachURL)) {
								finalUniqueURLSet.add(eachURL);
							}
						}
						/**Even After completing the above REVERSE step also, if you Get ORG URL size = 0,
						 Then use the URL list from old orgNameURListWithAllWebSites to get all URL**/
						if (finalUniqueURLSet.size() == 0) {
							finalUniqueURLSet.addAll(orgNameURListWithAllWebSites);
						}
					}

					// ############## This is FINAL MATCHED URL List if Reverse Flag true ############## //
					orgNameMatchedURList = new ArrayList<>(finalUniqueURLSet);
					// ############## This is FINAL MATCHED URL List if Reverse Flag true ############## //
				}else if(reverseURLMatchFlag==true){
					orgNameMatchedURList = new ArrayList<>(orgNameURListWithAllWebSites);
				}

				String writeBasicDataForValidationMethodStatus = "notChecked";
				String orgNameMethodStatus = "notChecked";
				String providerNameMethodStatus = "notChecked";
				int executingURLCount_ForProviderName = 1;
				String phoneNumberMethodStatus = "notChecked";
				int executingURLCount_ForPhoneNumber = 1;
				//String addressMethodStatus = "notChecked";
				//int executingURLCount_ForAddress = 1;
				//String extractedWebContentAddress="null";

				if(orgNameMatchedURList.size()==0){
					/*phoneValidationPriority,PvPhoneValidation values will be filled in to R3 report excel*/
					if (writeBasicDataForValidationMethodStatus.equalsIgnoreCase("notChecked")) {
						objWriteR3TestResult.writeBasicDataForValidation_PV(clonedR3File,executingRowIndex,phoneValidationPriority,PvPhoneValidation);
						writeBasicDataForValidationMethodStatus = "checked";
					}
					ExtentManager.getExtentTest().log(Status.FAIL,("Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report) with this search keyword ->> "+ allSearchString),
							MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
					String ORGNameMatchingStatus = "FAIL";
					objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
							 "Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");
					String ProviderNameMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File,executingRowIndex,ProviderNameMatchingStatus,
							"Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");//new
					String PhoneNumberMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,
							"Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");

					String PV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
					String PV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
							PV_PhoneFoundWebsitesMatchingStatus,PV_PhoneFoundOrgWebsitesMatchingStatus);

					String PV_PhoneNOTWebsiteMatchingStatus = "NOT APPLICABLE";
					String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
					objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
							PV_PhoneNOTWebsiteMatchingStatus,PV_PhoneNOTFoundORGWebsitesMatchingStatus);

					ProviderNameMatchingStatus = "No-Need";
					PhoneNumberMatchingStatus = "No-Need";
					String PVPhoneFoundWebsitesMatchingStatus = "No-Need";
					String PVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
					String PVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
					PV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";

					String remarkScope = "ORG-NAME";
					objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
							ProviderNameMatchingStatus, PhoneNumberMatchingStatus,PVPhoneFoundWebsitesMatchingStatus,
							PVPhoneFoundORGWebsitesMatchingStatus,PVPhoneNOTFoundWebsitesMatchingStatus,
							reverseURLMatchFlag, remarkScope,allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
							provPhoneMapDummy, "dummyPhoneNumber", Url);
					ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
				}

				for(int i=0; i<orgNameMatchedURList.size(); i++) {
					/*phoneValidationPriority,ProviderAndOrgPhoneValidation,PvPhoneValidation,ProviderPhoneValidation values will be filled in to R3 report excel*/
					if (writeBasicDataForValidationMethodStatus.equalsIgnoreCase("notChecked")) {
						objWriteR3TestResult.writeBasicDataForValidation_PV(clonedR3File,executingRowIndex,phoneValidationPriority,PvPhoneValidation);
						writeBasicDataForValidationMethodStatus = "checked";
					}

					/*ORG Name result will be written in to Test result excel*/
					if(orgNameMethodStatus.equalsIgnoreCase("notChecked")) {
						ExtentManager.getExtentTest().log(Status.PASS, ("ORG Name is Matching in R3 excel and Web site ->> " + orgNameMatchedURList.get(i) ));
						String ORGNameMatchingStatus = "PASS";
						objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File, executingRowIndex, ORGNameMatchingStatus,orgNameMatchedURList.get(i));

						String ProviderNameMatchingStatus = "No-Need";
						String PhoneNumberMatchingStatus = "No-Need";
						String PVPhoneFoundWebsitesMatchingStatus = "NOT-YET";
						String PVPhoneFoundORGWebsitesMatchingStatus = "NOT-YET";
						String PVPhoneNOTFoundWebsitesMatchingStatus = "NOT-YET";
						String PVPhoneNOTFoundORGWebsitesMatchingStatus = "NOT-YET";
						String remarkScope = "ORG-NAME";
						objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
								ProviderNameMatchingStatus,PhoneNumberMatchingStatus,PVPhoneFoundWebsitesMatchingStatus,
								PVPhoneFoundORGWebsitesMatchingStatus,PVPhoneNOTFoundWebsitesMatchingStatus,
								reverseURLMatchFlag, remarkScope,allSearchString, PVPhoneNOTFoundORGWebsitesMatchingStatus,
								provPhoneMapDummy,"dummyPhoneNumber", Url);
						orgNameMethodStatus = "checked";
					}

					String webContent = null;
					try {
						Url =orgNameMatchedURList.get(i);
						webContent = HTMLCode.get(Url);
						ExtentManager.getExtentTest().log(Status.INFO, ("Web site content is getting from HTTPClient ->> " + Url));
					}
					catch (SSLHandshakeException e) {
						ExtentManager.getExtentTest().log(Status.INFO, ("Web site content is getting from Website HTML Tag ->> " + Url));
						try {
							((RemoteWebDriver) driver).executeScript("window.location.href='" + Url +"';" + "setTimeout(function(){throw new Error('Timeout after 10 seconds');},15000);"); // Entering each URL
							if(webSiteContent.getText()!=null) {
								webContent = webSiteContent.getText();
							}
						}
						catch (TimeoutException ee) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Broken Website Issue  ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");
							String ProviderNameMatchingStatus = "NOT APPLICABLE"; //new
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File,executingRowIndex,ProviderNameMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");//new
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");

							String PV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_PhoneFoundWebsitesMatchingStatus,PV_PhoneFoundOrgWebsitesMatchingStatus);

							String PV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_Phone_WebsiteNOTMatchingStatus,PV_PhoneNOTFoundORGWebsitesMatchingStatus);

							ProviderNameMatchingStatus = "No-Need";
							PhoneNumberMatchingStatus = "No-Need";
							String PVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String PVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String PVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							PV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									ProviderNameMatchingStatus, PhoneNumberMatchingStatus,PVPhoneFoundWebsitesMatchingStatus,
									PVPhoneFoundORGWebsitesMatchingStatus,PVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
									provPhoneMapDummy, "dummyPhoneNumber", Url);
							break;
						}
						catch (NullPointerException ex) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Empty Website Content Issue ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");
							String ProviderNameMatchingStatus = "NOT APPLICABLE"; //new
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File,executingRowIndex,ProviderNameMatchingStatus,
									Url + " ->> ->> Not able to get the Web content due to Empty Website Content Issue");//new
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");

							String PV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_PhoneFoundWebsitesMatchingStatus,PV_PhoneFoundOrgWebsitesMatchingStatus);

							String PV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_Phone_WebsiteNOTMatchingStatus,PV_PhoneNOTFoundORGWebsitesMatchingStatus);

							ProviderNameMatchingStatus = "No-Need";
							PhoneNumberMatchingStatus = "No-Need";
							String PVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String PVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String PVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							PV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									ProviderNameMatchingStatus, PhoneNumberMatchingStatus,PVPhoneFoundWebsitesMatchingStatus,
									PVPhoneFoundORGWebsitesMatchingStatus,PVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
									provPhoneMapDummy, "dummyPhoneNumber", Url);
							break;
						}
					}
					catch (IOException e) {
						ExtentManager.getExtentTest().log(Status.INFO, ("Web site content is getting from Website HTML Tag ->> " + Url));
						try {
							((RemoteWebDriver) driver).executeScript("window.location.href='" + Url +"';" + "setTimeout(function(){throw new Error('Timeout after 10 seconds');},15000);"); // Entering each URL
							if(webSiteContent.getText()!=null) {
								webContent = webSiteContent.getText();
							}

						} catch (TimeoutException ee) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Broken Website Issue  ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");
							String ProviderNameMatchingStatus = "NOT APPLICABLE"; //new
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File,executingRowIndex,ProviderNameMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");//new
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");

							String PV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_PhoneFoundWebsitesMatchingStatus,PV_PhoneFoundOrgWebsitesMatchingStatus);

							String PV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_Phone_WebsiteNOTMatchingStatus,PV_PhoneNOTFoundORGWebsitesMatchingStatus);

							ProviderNameMatchingStatus = "No-Need";
							PhoneNumberMatchingStatus = "No-Need";
							String PVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String PVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String PVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							PV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									ProviderNameMatchingStatus, PhoneNumberMatchingStatus,PVPhoneFoundWebsitesMatchingStatus,
									PVPhoneFoundORGWebsitesMatchingStatus,PVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
									provPhoneMapDummy, "dummyPhoneNumber", Url);
							break;
						}
						catch (NullPointerException ex) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Empty Website Content Issue ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");
							String ProviderNameMatchingStatus = "NOT APPLICABLE"; //new
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File,executingRowIndex,ProviderNameMatchingStatus,
									Url + " ->> ->> Not able to get the Web content due to Empty Website Content Issue");//new
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");

							String PV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_PhoneFoundWebsitesMatchingStatus,PV_PhoneFoundOrgWebsitesMatchingStatus);

							String PV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_Phone_WebsiteNOTMatchingStatus,PV_PhoneNOTFoundORGWebsitesMatchingStatus);

							ProviderNameMatchingStatus = "No-Need";
							PhoneNumberMatchingStatus = "No-Need";
							String PVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String PVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String PVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							PV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									ProviderNameMatchingStatus, PhoneNumberMatchingStatus,PVPhoneFoundWebsitesMatchingStatus,
									PVPhoneFoundORGWebsitesMatchingStatus,PVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
									provPhoneMapDummy, "dummyPhoneNumber", Url);
							break;
						}
					}
				/*Searching the Provider Name in web content starts here*/
				if (providerNameMethodStatus.equalsIgnoreCase("notChecked")) {
					Document webContentDoc = Jsoup.parse(webContent);
					// Extract text content
					String textContent = webContentDoc.text();
					Map<Boolean,String> provMapResults = CheckProviderName.findProviderName(firstName,actualMiddleName,lastName,credentials,textContent);
					Boolean provNameFindingStatus = provMapResults.keySet().iterator().next();
					String pattern = provMapResults.get(provNameFindingStatus);
					if (provNameFindingStatus==true){
						ExtentManager.getExtentTest().log(Status.PASS, ("Provider Name is Matching in R3 excel and Web site " + Url + " >>> " + pattern),
								MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
						String ProviderNameMatchingStatus = "PASS";
						objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus, Url);

						/*Searching the Phone number in web content starts here*/
						if (phoneNumberMethodStatus.equalsIgnoreCase("notChecked")) {
							String phoneFormat1 = areaCode + "-" + exchangeCode + "-" + lineNumber;
							String phoneFormat2 = "(" + areaCode + ")" + "" + exchangeCode + "-" + lineNumber;
							String phoneFormat3 = "(" + areaCode + ")" + " " + exchangeCode + "-" + lineNumber;
							String phoneFormat4 = "(" + areaCode + ")" + "-" + exchangeCode + "-" + lineNumber;
							String phoneFormat5 = areaCode + "." + exchangeCode + "." + lineNumber;
							String phoneFormat6 = "(" + areaCode + ")" + "." + exchangeCode + "." + lineNumber;
							String phoneFormat7 = areaCode + " " + exchangeCode + " " + lineNumber;
							String phoneFormat8 = "+1" + areaCode + "" + exchangeCode + "" + lineNumber;
							String phoneFormat9 = "+1 " + areaCode + "." + exchangeCode + "." + lineNumber;
							String phoneFormat10 = "+1 " + areaCode + "-" + exchangeCode + "-" + lineNumber;
							String phoneFormat11 = "+" + areaCode + "-" + exchangeCode + "-" + lineNumber;
							String phoneFormat12 = "1-" + areaCode + "-" + exchangeCode + "-" + lineNumber;
							String phoneFormat13 = "+1-" + areaCode + "-" + exchangeCode + "-" + lineNumber;
							String phoneFormat14 = areaCode + exchangeCode + lineNumber;
							int count = 0;
							List<String> phoneFormatList = new ArrayList<>(
									Arrays.asList(phoneFormat1, phoneFormat2, phoneFormat3, phoneFormat4, phoneFormat5,
											phoneFormat6, phoneFormat7, phoneFormat8, phoneFormat9, phoneFormat10, phoneFormat11, phoneFormat12, phoneFormat13, phoneFormat14));
							for (String eachPhoneFormat : phoneFormatList) {
								count++;
								if (textContent != null && textContent.contains(textContent) &&
										!textContent.toLowerCase().contains("fax: " + eachPhoneFormat)) {
									//This is the method that will find the Nearest Provider matched phone number
									provPhoneMap = NearestProviderPhoneNumber.findProviderPhoneNumberLogic(pattern, eachPhoneFormat, webContent);
									String PhoneNumberMatchingStatus = "PASS";

									objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus, Url);
									String ORGNameMatchingStatus = "No-Need";
									ProviderNameMatchingStatus = "No-Need";
									PhoneNumberMatchingStatus = "No-Need";
									String PVPhoneFoundWebsitesMatchingStatus = "No-Need";
									String PVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
									String PVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
									String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
									String remarkScope = "ORG-PROV-PHONE";
									objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
											ProviderNameMatchingStatus, PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
											PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
											provPhoneMap, eachPhoneFormat, Url);

									if (Integer.parseInt(phoneValidationPriority) >= 1 && Integer.parseInt(phoneValidationPriority) <= 3.9) {
									} else {
										remarkScope = "ORG-PROV-PHONE_Priority";
										objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
												ProviderNameMatchingStatus, PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
												PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
												reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
												provPhoneMap, eachPhoneFormat, Url);
									}

									String PV_PhoneFoundWebsitesMatchingStatus = get_AllFoundWebsiteStatus(PV_Phone_Found_Websites, Url);
									String PV_PhoneFoundOrgWebsitesMatchingStatus = get_AllFoundWebsiteStatus(PV_Phone_Found_Organization_Websites, Url);
									objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File, executingRowIndex,
											PV_PhoneFoundWebsitesMatchingStatus, PV_PhoneFoundOrgWebsitesMatchingStatus);

									String PV_Phone_WebsiteNOTMatchingStatus = get_AllNOTFoundWebsiteStatus(PV_Phone_Not_Found_Websites, Url);
									PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
									objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File, executingRowIndex,
											PV_Phone_WebsiteNOTMatchingStatus, PV_PhoneNOTFoundORGWebsitesMatchingStatus);

									objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
											PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
											PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus);

									//PV_Phone_Found_Websites remarks
									if (PVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
										remarkScope = "PV_Phone_Found_Websites_Status";
										objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
												PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
												PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
												reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus);
									} else if (PVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
										remarkScope = "PV_Phone_Found_Websites_Status";
										objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
												PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
												PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
												reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus);
									}

									//PV_Phone_Found_Organization_Websites_Status remarks
									if (PVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
										remarkScope = "PV_Phone_Found_Organization_Websites_Status";
										objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
												PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
												PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
												reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus);
									} else if (PVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
										remarkScope = "PV_Phone_Found_Organization_Websites_Status";
										objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
												PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
												PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
												reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus);
									}

									//PV_Phone_Not_Found_Websites_Status remarks
									if (PVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")) {
										remarkScope = "PV_Phone_Not_Found_Websites_Status";
										objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
												PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
												PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
												reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus);
									} else if (PVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")) {
										remarkScope = "PV_Phone_Not_Found_Websites_Status";
										objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
												PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
												PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
												reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus);
									}
									//PV_PhoneNOTFoundORGWebsitesMatchingStatus is no need to mention here
									phoneNumberMethodStatus = "checked";
									providerNameMethodStatus = "checked";
									break;
								} else if (count == 14 && executingURLCount_ForPhoneNumber == orgNameMatchedURList.size() &&
										!webContent.contains(eachPhoneFormat) || webContent.toLowerCase().contains("fax: " + eachPhoneFormat)) { //newly added
									//This is the method that will find the Provider name and nearest phone number
									NearestProviderPhoneNumber.findProviderPhoneNumberLogic(pattern, eachPhoneFormat, webContent);
									ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Phone Number (" + eachPhoneFormat + ") is NOT found in these Web site list:  " + orgNameMatchedURList),
											MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
									String PhoneNumberMatchingStatus = "FAIL";
									objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus, orgNameMatchedURList); //newly addedd
									String PV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
									String PV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
									objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File, executingRowIndex,
											PV_PhoneFoundWebsitesMatchingStatus, PV_PhoneFoundOrgWebsitesMatchingStatus);

									String PV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
									String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
									objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File, executingRowIndex,
											PV_Phone_WebsiteNOTMatchingStatus, PV_PhoneNOTFoundORGWebsitesMatchingStatus);

									String ORGNameMatchingStatus = "No-Need";
									String PVPhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
									String PVPhoneFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
									String PVPhoneNOTFoundWebsitesMatchingStatus = "NOT APPLICABLE";
									String remarkScope = "ORG-PROV-PHONE";
									objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
											PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
											PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus);
								}
							}
							executingURLCount_ForPhoneNumber++;
						}
						/*Searching the Phone Number in web content ends here*/
					}
					else {
						if (provNameFindingStatus==false && executingURLCount_ForProviderName == orgNameMatchedURList.size()) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Provider Name " + pattern + " is NOT found in these Web site list:  " + orgNameMatchedURList),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ProviderNameMatchingStatus = "FAIL";
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus, Url);

							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,Url);

							String PV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_PhoneFoundWebsitesMatchingStatus,PV_PhoneFoundOrgWebsitesMatchingStatus);

							String PV_PhoneNOTWebsiteMatchingStatus = "NOT APPLICABLE";
							String PV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus_PV(clonedR3File,executingRowIndex,
									PV_PhoneNOTWebsiteMatchingStatus,PV_PhoneNOTFoundORGWebsitesMatchingStatus);

							String ORGNameMatchingStatus = "No-Need";
							PhoneNumberMatchingStatus = "No-Need";
							String PVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String PVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String PVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							PV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-PROV";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File, executingRowIndex, ORGNameMatchingStatus,
									ProviderNameMatchingStatus, PhoneNumberMatchingStatus, PVPhoneFoundWebsitesMatchingStatus,
									PVPhoneFoundORGWebsitesMatchingStatus, PVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope, allSearchString, PV_PhoneNOTFoundORGWebsitesMatchingStatus,
									provPhoneMap, "dummyPhoneNumber", Url);

							/****Here what only  the 3.2 code should start as provider name is not found in ORG Site *****/
						}
					}

					executingURLCount_ForProviderName++;
					}
				/*Searching the Provider Name in web content ends here*/

				/*Searching the Address in web content starts here*//*
				if(addressMethodStatus.equalsIgnoreCase("notChecked")) {
					Document document = Jsoup.parse(webContent);
					String stringWebContent = document.text()
												.replace(" ","")
												.replace(",","")
												.replace(".","")
												.replace("(","")
												.replace(")","")
												.toLowerCase();

					String stringAddressFromR3 = completeAddress.replace("(","").replace(")","");
					String[] addressArray = stringAddressFromR3.split("\\s");
					// Specify the start and end patterns
					String startPattern = addressArray[0];
					String endPattern = addressArray[addressArray.length-1].substring(1); // to remove the 0 from the zipcode
					// Create a regular expression pattern using the start and end patterns
					String pattern = "(?s)(?i)" + Pattern.quote(startPattern) + "(.*?)" + Pattern.quote(endPattern);
					// Create a pattern for the starting and ending substrings
					Pattern regexPattern = Pattern.compile(pattern);
					// Create a matcher and apply the pattern to the input
					Matcher matcher = regexPattern.matcher(stringWebContent);

					if(matcher.find()) {
						// Extract the substring between the last "startPattern" and the first "endPattern" after that
						extractedWebContentAddress =  startPattern + matcher.group(1) + endPattern;

						if ((compareStringsWithGivenPercentage(extractedWebContentAddress, stringAddressFromR3, 60.0) ||
								compareStringsWithGivenPercentage(stringAddressFromR3, extractedWebContentAddress, 60.0))
								&& webContent.length() != 0) {
							ExtentManager.getExtentTest().log(Status.INFO, ("Extracted Address from Web site " + Url + " >>> " + extractedWebContentAddress));
							ExtentManager.getExtentTest().log(Status.PASS, ("Address is Matching in R3 excel and Web site " + Url + " >>> " + completeAddress),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String AddressMatchingStatus = "PASS";
							objWriteR3TestResult.writeAddressMatchStatus(clonedR3File, executingRowIndex, AddressMatchingStatus,phoneValidationPriority,Url);
							addressMethodStatus = "checked";
						}else {
							if (executingURLCount_ForAddress == orgNameMatchedURList.size()) {
								ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Address " + completeAddress + " is NOT found in these Web site list:  " + orgNameMatchedURList),
										MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
								String AddressMatchingStatus = "FAIL";
								objWriteR3TestResult.writeAddressMatchStatus(clonedR3File, executingRowIndex, AddressMatchingStatus,phoneValidationPriority,Url);
							}
							executingURLCount_ForAddress++;
						}
					}

					else {
						if (executingURLCount_ForAddress == orgNameMatchedURList.size()) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Address " + completeAddress + " is NOT found in these Web site list:  " + orgNameMatchedURList),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String AddressMatchingStatus = "FAIL";
							objWriteR3TestResult.writeAddressMatchStatus(clonedR3File, executingRowIndex, AddressMatchingStatus,phoneValidationPriority,Url);
						}
						executingURLCount_ForAddress++;
					}
				}
				*//*Searching the Address in web content ends here*/

				/*All Pass condition validating starts here*/
				if(objWriteR3TestResult.getTestResult(clonedR3File, executingRowIndex) ){
					break;
				}
				/*All Pass condition validating ends here*/
			}
		}
		}catch (SSLHandshakeException e1){
			System.out.println("SSL certificate issue is found");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
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

    private static List<String> filterSimilarWebsites(List<String> urlList, String targetDomain) {
        List<String> similarWebsites = new ArrayList<>();
        for (String urlString : urlList) {
            try {
                URL url = new URL(urlString);
                String domain = url.getHost();

                // Check if the domain contains the target domain
                if (domain.replaceAll("\\.","").contains(targetDomain)) { //just removing the inner dots
                    similarWebsites.add(urlString);
                }
            } catch (Exception e) {
                // Handle malformed URLs or other exceptions
                e.printStackTrace();
            }
        }
        return similarWebsites;
    }

	/*utility method*/
	public String get_AllFoundWebsiteStatus(Set<String> websiteList1,String Url){
		String foundURL = Url.replace("https://", "").replace("http://", "").replace("www.","").split("/")[0];
		Set<String> foundWebsiteFilter1 = websiteList1.stream().map(s->s.split("\\|")[0]).collect(Collectors.toSet());
		String result = "FAIL";
		boolean foundWebsiteFilter1MatchingStatus = false;
		for(String eachWebSite : foundWebsiteFilter1){
			if(eachWebSite.toLowerCase().replace("https://", "")
					.replace("http://", "")
					.replace("www.","")
					.replace("/","")
					.equalsIgnoreCase(foundURL.toLowerCase())){
				foundWebsiteFilter1MatchingStatus = true;
				break;
			}
		}
		if(foundWebsiteFilter1MatchingStatus){
			result = "PASS";
		}
		return result;
	}

	public String get_AllFoundWebsiteStatus(Set<String> websiteList1,List<String> orgNameMatchedURList){
		boolean foundWebsiteFilter1MatchingStatus = false;
		String result=null;
		for(String Url:orgNameMatchedURList) {
			String foundURL = Url.replace("https://", "").replace("http://", "").replace("www.", "").split("/")[0];
			Set<String> foundWebsiteFilter1 = websiteList1.stream().map(s -> s.split("\\|")[0]).collect(Collectors.toSet());
			result = "FAIL";

			for (String eachWebSite : foundWebsiteFilter1) {
				if (eachWebSite.toLowerCase().replace("https://", "")
						.replace("http://", "")
						.replace("www.", "")
						.replace("/", "")
						.equalsIgnoreCase(foundURL.toLowerCase())) {
					foundWebsiteFilter1MatchingStatus = true;
					break;
				}
			}
		}
		if(foundWebsiteFilter1MatchingStatus){
			result = "PASS";
		}
		return result;
	} // Inaccurate case


	public String get_AllNOTFoundWebsiteStatus(Set<String> websiteList1, String Url){
		String foundURL = Url.replace("https://", "").replace("http://", "").replace("www.","").split("/")[0];
		Set<String> notFoundWebsiteFilter1 = websiteList1.stream().map(s->s.split("\\|")[0]).collect(Collectors.toSet());

		String result = "FAIL";
		boolean NotFoundWebsiteFilter1MatchingStatus = false;
		for(String eachWebSite : notFoundWebsiteFilter1){
			if(!eachWebSite.toLowerCase().replace("https://", "")
					.replace("http://", "")
					.replace("www.","")
					.replace("/","")
					.equalsIgnoreCase(foundURL.toLowerCase())){
				NotFoundWebsiteFilter1MatchingStatus = true;
				break;
			}
		}

		if(NotFoundWebsiteFilter1MatchingStatus){
			result = "PASS";
		}
		return result;
	}

	public boolean get_AllMatchingORGWebsiteList(Set<String> websiteList1, String Url) {
		String foundURL = Url.replace("https://", "").replace("http://", "").replace("www.", "").split("/")[0];
		Set<String> foundWebsiteFilter1 = websiteList1.stream().map(s -> s.split("\\|")[0]).collect(Collectors.toSet());
		List<String> matchingURLList = new ArrayList<>();
		boolean urlMatchFlag = false;
		for (String eachWebSite : foundWebsiteFilter1) {
			if (eachWebSite.toLowerCase().replace("https://", "")
					.replace("http://", "")
					.replace("www.", "")
					.replace("/", "")
					.equalsIgnoreCase(foundURL.toLowerCase())) {
				matchingURLList.add(Url);
				urlMatchFlag = true;
				break;
			}
		}
		return urlMatchFlag;
	}
}



















