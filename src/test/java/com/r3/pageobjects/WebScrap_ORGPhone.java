package com.r3.pageobjects;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.r3.utility.ExtentManager;
import com.r3.utility.HTMLCode;
import com.r3.utility.Screenshot;
import com.r3.utility.WriteR3TestResult;
import com.r3.webelements.GoogleSearchKeywordPage_WebElements;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class WebScrap_ORGPhone {
	WebDriver driver;
	String Url = null;
	WebDriverWait wait;
	public WebScrap_ORGPhone(WebDriver TestBaseClassDriver) {
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
	public void check_ORG_Phone_Accurate(String clonedR3File, int executingRowIndex, String phoneValidationPriority,
										 String OrganizationPhoneValidation, String combinedSearchKeyword_OrgProvPhone,
										 LinkedHashSet<String> orgNameKey, String areaCode, String exchangeCode, String lineNumber,
										 Set<String> OV_Phone_Found_Websites, Set<String> OV_Phone_Found_Organization_Websites,
										 Set<String> OV_Phone_Not_Found_Websites, String address) {
		WriteR3TestResult objWriteR3TestResult = new WriteR3TestResult();
		wait = new WebDriverWait(driver, 30);
		try {
			driver.manage().deleteAllCookies();
			driver.get("chrome://settings/clearBrowserData");
			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
			driver.get("https://www.google.com");

			LinkedHashSet<String> orgNameMatchedURSetDataType = new LinkedHashSet<>();
			//1st Search String is only Phone Number with space
			String phoneNumberSearchString = areaCode+" "+exchangeCode+" "+lineNumber; //3rd Search String
			//2nd Search string is combinedSearchKeyword_OrgProvPhone

			//3rd Search string OrgNamePhoneAddressSearchString
			String OrgNameStringWithOR=null;
			for(String eachOrgNameKey : orgNameKey){
				OrgNameStringWithOR = eachOrgNameKey +" OR ";
			}
			String phoneNumberWithourSpaceSearchString = areaCode+exchangeCode+lineNumber;
			String OrgNamePhoneAddressSearchString = OrgNameStringWithOR + phoneNumberWithourSpaceSearchString +" OR "+ address; //3rd Search String

			StringBuilder orgNameStringBuilder = new StringBuilder();
			String OrgNameStringWithoutORLast=null; //5th Search string
			for (String eachOrgNameKey : orgNameKey) {
				orgNameStringBuilder.append(eachOrgNameKey).append(" OR ");
			}
			if (orgNameStringBuilder.length() > 0) {
				OrgNameStringWithoutORLast = orgNameStringBuilder.substring(0, orgNameStringBuilder.length() - 4);
			}
			String OrgNamePhoneAddressSearchString4 = OrgNameStringWithoutORLast +" " +phoneNumberWithourSpaceSearchString; //4th Search String

			//Adding all Search String
			List<String> searchStringList = new ArrayList<>(Arrays.asList(phoneNumberSearchString, combinedSearchKeyword_OrgProvPhone,
					OrgNamePhoneAddressSearchString,OrgNamePhoneAddressSearchString4,
					OrgNameStringWithoutORLast));
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
									&& !Url.toLowerCase().startsWith("https://www.healthgrades")) {
								orgNameMatchedURSetDataType.add(Url);
								break;
							}
						} else {
							String[] eachWordOfR3OrgNameList = eachOrgNameKey.toLowerCase()
									.replace(" or ", "")
									.replace("  ", " ")//new
									.replace(" and ", "").split("\\s");
							List<String> wordList = new ArrayList<>(Arrays.asList("and", "or", "in", "for", "&", "of", "on", "is", "at", "has", "had", "a", "to"));// will have to update if any word //new
							String eachSearchLinkHeaderFirstWord = webElement.getText().toLowerCase().split("\\s")[0]; //new
							String s2 = eachSearchLinkHeaderFirstWord;
							for (String s1 : eachWordOfR3OrgNameList) {
								if (!wordList.contains(s1)) { //new
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
			/** Multiple Search string operation starts here **/

			/** Converting the SET to LIST to iterate. This LIST may have inappropriate URL too.
			 If orgNameMatchedURSetDataType is Empty, we have to  Use REVERSE Model to get the URL from OV_Phone_Found_Organization_Websites**/
			List<String> orgNameURListWithAllWebSites = new ArrayList<>(orgNameMatchedURSetDataType); // Stage 1 to Store Matched URL

			//if R3 ORG Name is not matching with search weblink header, we need to compare the websites list from OV_Phone_Found_Organization_Websites column reverse order
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
						// OV_Phone_Found_Organization_Websites columns for revers order
						if (get_AllMatchingORGWebsiteList(OV_Phone_Found_Organization_Websites, eachURL)) {
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

			if(orgNameURListWithAllWebSites.isEmpty()||orgNameURListWithAllWebSites.size()==0){
				ExtentManager.getExtentTest().log(Status.FAIL,("We are not able to find the ORG Web Site link with this search keyword ->> "+ combinedSearchKeyword_OrgProvPhone),
						MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
				objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,OrganizationPhoneValidation);
				String ORGNameMatchingStatus = "FAIL";
				objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,Url);
				String PhoneNumberMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,Url);

				String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
				String OV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
						OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

				String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
				String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
				objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
						OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

				PhoneNumberMatchingStatus = "No-Need";
				String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
				String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
				String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
				OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
				String remarkScope = "ORG-NAME";
				objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
						PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
						OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
						reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
				ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
			}
			else{
				/** Sometime ORG NAME words will be located in te URL HEADER. Because of that , we will get the inappropriate URL also.
				 To Resolve this, We have to Match the Selected URL List with R3 report ORG NAMEs
				 To Filter the correct URL the below code will be used **/
				Set<String> finalUniqueURLSet = new LinkedHashSet<>();
				for(String eachFirstRoundMatchedURL : orgNameURListWithAllWebSites) {
					String extractedURLString = eachFirstRoundMatchedURL
							.replaceAll("https?://(?:www\\.)?(\\w+\\.\\w+).*", "$1")
							.replaceAll("\\.\\w+", "");
					for (String eachOrgNameKeyR3 : orgNameKey) {
						String orgNameKeyWithoutSpace = eachOrgNameKeyR3.toLowerCase().replace(" ", "");
						if ((compareGivenStringsWithParticularPercentage(orgNameKeyWithoutSpace, extractedURLString, 75.0) ||
								compareGivenStringsWithParticularPercentage(extractedURLString, orgNameKeyWithoutSpace, 75.0))
								&& extractedURLString.length() != 0 && !extractedURLString.startsWith("facebook")
								&& !extractedURLString.startsWith("linkedin")
								&& !extractedURLString.startsWith("Healthgrades")) {
							for(String finalURL : filterSimilarWebsites(orgNameURListWithAllWebSites, extractedURLString)) {
								finalUniqueURLSet.add(finalURL);
							}
							break;
						}else{
							String[] eachWordOfR3OrgNameList = eachOrgNameKeyR3.toLowerCase()
									.replace(" or ", "")
									.replace(" and ", "").split("\\s");
							extractedURLString = extractedURLString
									.replaceAll("https?://", "")
									.replaceAll("/$", "");
							String[] urlStringArray = extractedURLString.toLowerCase().replace("-", " ").split("\\s");
							for(String urlString:urlStringArray) {
								for (String eachWordORGNAme : eachWordOfR3OrgNameList) {
									if ((compareStringsWithGivenPercentage(urlString, eachWordORGNAme, 90.0) ||urlString.contains(eachWordORGNAme))
											&& extractedURLString.length() != 0 && !extractedURLString.startsWith("facebook")
											&& !extractedURLString.startsWith("linkedin")
											&& !extractedURLString.startsWith("Healthgrades")) {
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
				 then DO THE REVERSE CHECK with OV_Phone_Found_Organization_Websites column value again**/
				if(finalUniqueURLSet.size()==0){
					for(String eachURL: orgNameURListWithAllWebSites) {
						if(get_AllMatchingORGWebsiteList(OV_Phone_Found_Organization_Websites,eachURL)){
							finalUniqueURLSet.add(eachURL);
						}
					}
					/**Even After completing the above REVERSE step also, if you Get ORG URL size = 0,
					 Then use the URL list from old orgNameURListWithAllWebSites to get all URL**/
					if(finalUniqueURLSet.size()==0){
						finalUniqueURLSet.addAll(orgNameURListWithAllWebSites);
					}
				}

				// ############## This is FINAL MATCHED URL List ############## //
				List<String> orgNameMatchedURList = new ArrayList<>(finalUniqueURLSet);

				String writeBasicDataForValidationMethodStatus = "notChecked";
				String orgNameMethodStatus = "notChecked";
				//String providerNameMethodStatus = "notChecked";
				//int executingURLCount_ForProviderName = 1;
				String phoneNumberMethodStatus = "notChecked";
				int executingURLCount_ForPhoneNumber = 1;
				//String addressMethodStatus = "notChecked";
				//int executingURLCount_ForAddress = 1;
				//String extractedWebContentAddress="null";

				if(orgNameMatchedURList.size()==0){
					/*phoneValidationPriority,OrganizationPhoneValidation values will be filled in to R3 report excel*/
					if (writeBasicDataForValidationMethodStatus.equalsIgnoreCase("notChecked")) {
						objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,OrganizationPhoneValidation);
						writeBasicDataForValidationMethodStatus = "checked";
					}
					ExtentManager.getExtentTest().log(Status.FAIL,("Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report) with this search keyword ->> "+ combinedSearchKeyword_OrgProvPhone),
							MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
					String ORGNameMatchingStatus = "FAIL";
					objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
							 "Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");
					String PhoneNumberMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,
							"Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");

					String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
					String OV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
							OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

					String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
					String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
					objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
							OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

					PhoneNumberMatchingStatus = "No-Need";
					String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
					String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
					String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
					OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
					String remarkScope = "ORG-NAME";
					objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
							PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
							OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
							reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
					ExtentManager.getExtentTest().log(Status.FAIL,("======================================================================================================="));
				}

				for(int i=0; i<orgNameMatchedURList.size(); i++) {
					/*phoneValidationPriority,ProviderAndOrgPhoneValidation,OrganizationPhoneValidation,ProviderPhoneValidation values will be filled in to R3 report excel*/
					if (writeBasicDataForValidationMethodStatus.equalsIgnoreCase("notChecked")) {
						objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,OrganizationPhoneValidation);
						writeBasicDataForValidationMethodStatus = "checked";
					}

					/*ORG Name result will be written in to Test result excel*/
					if(orgNameMethodStatus.equalsIgnoreCase("notChecked")) {
						ExtentManager.getExtentTest().log(Status.PASS, ("ORG Name is Matching in R3 excel and Web site ->> " + orgNameMatchedURList.get(i) ));
						String ORGNameMatchingStatus = "PASS";
						objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File, executingRowIndex, ORGNameMatchingStatus,orgNameMatchedURList.get(i));

						String PhoneNumberMatchingStatus = "No-Need";
						String OVPhoneFoundWebsitesMatchingStatus = "NOT-YET";
						String OVPhoneFoundORGWebsitesMatchingStatus = "NOT-YET";
						String OVPhoneNOTFoundWebsitesMatchingStatus = "NOT-YET";
						String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT-YET";
						String remarkScope = "ORG-NAME";
						objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
								PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
								OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
								reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
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

						} catch (TimeoutException ee) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Broken Website Issue  ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");

							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							PhoneNumberMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							break;
						}
						catch (NullPointerException ex) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Empty Website Content Issue ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");

							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							PhoneNumberMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
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
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");

							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							PhoneNumberMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							break;
						}
						catch (NullPointerException ex) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Empty Website Content Issue ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");

							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							PhoneNumberMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							break;
						}
					}

				/*Searching the Provider Name in web content starts here*//*
				if (providerNameMethodStatus.equalsIgnoreCase("notChecked")) {
					if(actualMiddleName.equalsIgnoreCase("null")) {
						String providerNameFormat0 = firstName + " " + lastName;
						Pattern pattern0 = Pattern.compile(Pattern.quote(providerNameFormat0), Pattern.CASE_INSENSITIVE);
						Matcher matcher0 = pattern0.matcher(webContent);

						String providerNameFormat1 = firstName + lastName;
						Pattern pattern1 = Pattern.compile(Pattern.quote(providerNameFormat1), Pattern.CASE_INSENSITIVE);
						Matcher matcher1 = pattern1.matcher(webContent);

						if (matcher0.find() || matcher1.find()) {
							ExtentManager.getExtentTest().log(Status.PASS, ("Provider Name is Matching in R3 excel and Web site " + Url + " >>> " + providerNameFormat0),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ProviderNameMatchingStatus = "PASS";
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus,phoneValidationPriority,Url);
							providerNameMethodStatus = "checked";
						} else {
							if(executingURLCount_ForProviderName==orgNameMatchedURList.size()) {
								ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Provider Name "+ providerNameFormat0 + " is NOT found in these Web site list:  " + orgNameMatchedURList),
										MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
								String ProviderNameMatchingStatus = "FAIL";
								objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus,phoneValidationPriority,Url);
							}
							executingURLCount_ForProviderName++;
						}
					}
					else {
						String providerNameFormat0_1 = firstName + " " + lastName;
						Pattern pattern0_1 = Pattern.compile(Pattern.quote(providerNameFormat0_1), Pattern.CASE_INSENSITIVE);
						Matcher matcher0_1 = pattern0_1.matcher(webContent);

						String providerNameFormat1_1 = firstName + lastName;
						Pattern pattern1_1 = Pattern.compile(Pattern.quote(providerNameFormat1_1), Pattern.CASE_INSENSITIVE);
						Matcher matcher1_1 = pattern1_1.matcher(webContent);

						String providerNameFormat2_1 = firstName + " " + actualMiddleName + " " + lastName;
						Pattern pattern2_1 = Pattern.compile(Pattern.quote(providerNameFormat2_1), Pattern.CASE_INSENSITIVE);
						Matcher matcher2_1 = pattern2_1.matcher(webContent);

						String providerNameFormat2 = firstName + " " + actualMiddleName + ". " + lastName;
						Pattern pattern2 = Pattern.compile(Pattern.quote(providerNameFormat2), Pattern.CASE_INSENSITIVE);
						Matcher matcher2 = pattern2.matcher(webContent);

						String providerNameFormat3 = firstName + "-" + actualMiddleName + ".-" + lastName;
						Pattern pattern3 = Pattern.compile(Pattern.quote(providerNameFormat3), Pattern.CASE_INSENSITIVE);
						Matcher matcher3 = pattern3.matcher(webContent);

						String providerNameFormat4 = firstName + "-" + actualMiddleName + "-" + lastName;
						Pattern pattern4 = Pattern.compile(Pattern.quote(providerNameFormat4), Pattern.CASE_INSENSITIVE);
						Matcher matcher4 = pattern4.matcher(webContent);

						String providerNameFormat5 = firstName + "_" + actualMiddleName + "_" + lastName;
						Pattern pattern5 = Pattern.compile(Pattern.quote(providerNameFormat5), Pattern.CASE_INSENSITIVE);
						Matcher matcher5 = pattern5.matcher(webContent);

						String providerOnlyMiddleNameNameFormat6 = "\\b" + Pattern.quote(firstName) + " " + Pattern.quote(actualMiddleName) + "\\S* " + Pattern.quote(lastName) + "\\b";
						Pattern pattern6 = Pattern.compile(providerOnlyMiddleNameNameFormat6);
						Matcher matcher6 = pattern6.matcher(webContent);

						if (matcher1_1.find() || matcher0_1.find() ||
							matcher2_1.find() || matcher2.find() ||
							matcher3.find() || matcher4.find() ||
							matcher5.find() || matcher6.find()) {
							ExtentManager.getExtentTest().log(Status.PASS, ("Provider Name is Matching in R3 excel and Web site " + Url + " >>> " + providerNameFormat2_1),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ProviderNameMatchingStatus = "PASS";
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus,phoneValidationPriority,Url);
							providerNameMethodStatus = "checked";
						} else {
							if(executingURLCount_ForProviderName==orgNameMatchedURList.size()) {
								ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Provider Name "+ providerNameFormat2_1 + " is NOT found in these Web site list:  " + orgNameMatchedURList),
										MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
								String ProviderNameMatchingStatus = "FAIL";
								objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus,phoneValidationPriority,Url);
							}
							executingURLCount_ForProviderName++;
						}
					}
				}
				*//*Searching the Provider Name in web content ends here*/

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

				/*Searching the Phone number in web content starts here*/
				if(phoneNumberMethodStatus.equalsIgnoreCase("notChecked")) {
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
						if (webContent!=null && webContent.contains(eachPhoneFormat) &&
								!webContent.toLowerCase().contains("fax: "+eachPhoneFormat)) { //newly added
							ExtentManager.getExtentTest().log(Status.PASS, ("Phone Number is Matching in R3 excel and Web site " + Url + " >>> " + eachPhoneFormat),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());

							String PhoneNumberMatchingStatus = "PASS";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,Url);

							String OV_PhoneFoundWebsitesMatchingStatus = get_AllFoundWebsiteStatus(OV_Phone_Found_Websites,Url);
							String OV_PhoneFoundOrgWebsitesMatchingStatus = get_AllFoundWebsiteStatus(OV_Phone_Found_Organization_Websites,Url);
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = get_AllNOTFoundWebsiteStatus(OV_Phone_Not_Found_Websites,Url);
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							String ORGNameMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = OV_PhoneFoundWebsitesMatchingStatus;
							String OVPhoneFoundORGWebsitesMatchingStatus = OV_PhoneFoundOrgWebsitesMatchingStatus;
							String OVPhoneNOTFoundWebsitesMatchingStatus = OV_Phone_WebsiteNOTMatchingStatus;
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							String remarkScope = "ORG-PHONE";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							//OV_Phone_Found_Websites remarks
							if(OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")){
								remarkScope = "OV_Phone_Found_Websites_Status";
								objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
										PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
										OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
										reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							}else if(OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")){
								remarkScope = "OV_Phone_Found_Websites_Status";
								objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
										PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
										OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
										reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							}

							//OV_Phone_Found_Organization_Websites_Status remarks
							if(OVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("FAIL")){
								remarkScope = "OV_Phone_Found_Organization_Websites_Status";
								objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
										PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
										OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
										reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							}else if(OVPhoneFoundORGWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")){
								remarkScope = "OV_Phone_Found_Organization_Websites_Status";
								objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
										PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
										OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
										reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							}

							//OV_Phone_Not_Found_Websites_Status remarks
							if(OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")){
								remarkScope = "OV_Phone_Not_Found_Websites_Status";
								objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
										PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
										OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
										reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							}else if(OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")){
								remarkScope = "OV_Phone_Not_Found_Websites_Status";
								objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
										PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
										OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
										reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							}
							//OV_PhoneNOTFoundORGWebsitesMatchingStatus is no need to mention here
							phoneNumberMethodStatus="checked";
							break;
						}
						else if (count == 14 && executingURLCount_ForPhoneNumber==orgNameMatchedURList.size() &&
								!webContent.contains(eachPhoneFormat) || webContent.toLowerCase().contains("fax: "+eachPhoneFormat)) { //newly added
							ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Phone Number ("+ eachPhoneFormat + ") is NOT found in these Web site list:  " + orgNameMatchedURList),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String PhoneNumberMatchingStatus = "FAIL";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,orgNameMatchedURList); //newly addedd
							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							String ORGNameMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OVPhoneFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String remarkScope = "ORG-PHONE";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
						}
					}
					executingURLCount_ForPhoneNumber++;
				}
                /*Searching the Phone Number in web content ends here*/

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

	public void check_ORG_Phone_Inaccurate(String clonedR3File, int executingRowIndex, String phoneValidationPriority,
										 String OrganizationPhoneValidation, String combinedSearchKeyword_OrgProvPhone,
										 LinkedHashSet<String> orgNameKey, String areaCode, String exchangeCode, String lineNumber,
										 Set<String> OV_Phone_Found_Websites, Set<String> OV_Phone_Not_Found_Organization_Websites,
										 Set<String> OV_Phone_Not_Found_Websites, String address) {
		WriteR3TestResult objWriteR3TestResult = new WriteR3TestResult();
		wait = new WebDriverWait(driver, 30);
		try {
			driver.manage().deleteAllCookies();
			driver.get("chrome://settings/clearBrowserData");
			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
			driver.get("https://www.google.com");

			LinkedHashSet<String> orgNameMatchedURSetDataType = new LinkedHashSet<>();
			//1st Search String is only Phone Number with space
			String phoneNumberSearchString = areaCode+" "+exchangeCode+" "+lineNumber; //3rd Search String
			//2nd Search string is combinedSearchKeyword_OrgProvPhone

			//3rd Search string OrgNamePhoneAddressSearchString
			String OrgNameStringWithOR=null;
			for(String eachOrgNameKey : orgNameKey){
				OrgNameStringWithOR = eachOrgNameKey +" OR ";
			}
			String phoneNumberWithourSpaceSearchString = areaCode+exchangeCode+lineNumber;
			String OrgNamePhoneAddressSearchString = OrgNameStringWithOR + phoneNumberWithourSpaceSearchString +" OR "+ address; //3rd Search String

			StringBuilder orgNameStringBuilder = new StringBuilder();
			String OrgNameStringWithoutORLast=null; //5th Search string
			for (String eachOrgNameKey : orgNameKey) {
				orgNameStringBuilder.append(eachOrgNameKey).append(" OR ");
			}
			if (orgNameStringBuilder.length() > 0) {
				OrgNameStringWithoutORLast = orgNameStringBuilder.substring(0, orgNameStringBuilder.length() - 4);
			}
			String OrgNamePhoneAddressSearchString4 = OrgNameStringWithoutORLast +" " +phoneNumberWithourSpaceSearchString; //4th Search String

			//Adding all Search String
			List<String> searchStringList = new ArrayList<>(Arrays.asList(phoneNumberSearchString, combinedSearchKeyword_OrgProvPhone,
																		OrgNamePhoneAddressSearchString,OrgNamePhoneAddressSearchString4,
																		OrgNameStringWithoutORLast));
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
									&& !Url.toLowerCase().startsWith("https://www.healthgrades")) {
								orgNameMatchedURSetDataType.add(Url);
								break;
							}
						} else {
							String[] eachWordOfR3OrgNameList = eachOrgNameKey.toLowerCase()
									.replace(" or ", "")
									.replace("  ", " ")//new
									.replace(" and ", "").split("\\s");
							List<String> wordList = new ArrayList<>(Arrays.asList("and", "or", "in", "for", "&", "of", "on", "is", "at", "has", "had", "a", "to"));// will have to update if any word //new
							String eachSearchLinkHeaderFirstWord = webElement.getText().toLowerCase().split("\\s")[0]; //new
							String s2 = eachSearchLinkHeaderFirstWord;
							for (String s1 : eachWordOfR3OrgNameList) {
								if (!wordList.contains(s1)) { //new
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
			/** Multiple Search string operation starts here **/

			/** Converting the SET to LIST to iterate. This LIST may have inappropriate URL too.
			If orgNameMatchedURSetDataType is Empty, we have to  Use REVERSE Model to get the URL from OV_Phone_Not_Found_Organization_Websites**/
			List<String> orgNameURListWithAllWebSites = new ArrayList<>(orgNameMatchedURSetDataType); // Stage 1 to Store Matched URL

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
						// OV_Phone_Not_Found_Organization_Websites columns for revers order
						if (get_AllMatchingORGWebsiteList(OV_Phone_Not_Found_Organization_Websites, eachURL)) {
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

			if(orgNameURListWithAllWebSites.isEmpty()||orgNameURListWithAllWebSites.size()==0){
				ExtentManager.getExtentTest().log(Status.FAIL,("We are not able to find the ORG Web Site link with this search keyword ->> "+ combinedSearchKeyword_OrgProvPhone),
						MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
				objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,OrganizationPhoneValidation);
				String ORGNameMatchingStatus = "FAIL";
				objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,Url);
				String PhoneNumberMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,Url);

				String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
				String OV_PhoneFoundOrgWebsitesMatchingStatus = "NO NEED";
				objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
						OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

				String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
				String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
						OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

				PhoneNumberMatchingStatus = "No-Need";
				String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
				String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
				String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
				OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
				String remarkScope = "ORG-NAME";
				objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
						PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
						OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
						reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
				ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
			}
			else{
				/** Sometime ORG NAME words will be located in te URL HEADER. Because of that , we will get the inappropriate URL also.
				 To Resolve this, We have to Match the Selected URL List with R3 report ORG NAMEs
					To Filter the correct URL the below code will be used **/
				Set<String> finalUniqueURLSet = new LinkedHashSet<>();
				for(String eachFirstRoundMatchedURL : orgNameURListWithAllWebSites) {
					String extractedURLString = eachFirstRoundMatchedURL
							.replaceAll("https?://(?:www\\.)?(\\w+\\.\\w+).*", "$1")
							.replaceAll("\\.\\w+", "");
					for (String eachOrgNameKeyR3 : orgNameKey) {
						String orgNameKeyWithoutSpace = eachOrgNameKeyR3.toLowerCase().replace(" ", "");
						if ((compareGivenStringsWithParticularPercentage(orgNameKeyWithoutSpace, extractedURLString, 75.0) ||
								compareGivenStringsWithParticularPercentage(extractedURLString, orgNameKeyWithoutSpace, 75.0))
								&& extractedURLString.length() != 0 && !extractedURLString.startsWith("facebook")
								&& !extractedURLString.startsWith("linkedin")
								&& !extractedURLString.startsWith("Healthgrades")) {
							for(String finalURL : filterSimilarWebsites(orgNameURListWithAllWebSites, extractedURLString)) {
								finalUniqueURLSet.add(finalURL);
							}
							break;
						}else{
							String[] eachWordOfR3OrgNameList = eachOrgNameKeyR3.toLowerCase()
									.replace(" or ", "")
									.replace(" and ", "").split("\\s");
							 extractedURLString = extractedURLString
									.replaceAll("https?://", "")
									.replaceAll("/$", "");
							String[] urlStringArray = extractedURLString.toLowerCase().replace("-", " ").split("\\s");
							for(String urlString:urlStringArray) {
								for (String eachWordORGNAme : eachWordOfR3OrgNameList) {
									if ((compareStringsWithGivenPercentage(urlString, eachWordORGNAme, 90.0) ||urlString.contains(eachWordORGNAme))
											&& extractedURLString.length() != 0 && !extractedURLString.startsWith("facebook")
											&& !extractedURLString.startsWith("linkedin")
											&& !extractedURLString.startsWith("Healthgrades")) {
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
				 then DO THE REVERSE CHECK with OV_Phone_Not_Found_Organization_Websites column value again**/
				if(finalUniqueURLSet.size()==0){
					for(String eachURL: orgNameURListWithAllWebSites) {
						if(get_AllMatchingORGWebsiteList(OV_Phone_Not_Found_Organization_Websites,eachURL)){
							finalUniqueURLSet.add(eachURL);
						}
					}
					/**Even After completing the above REVERSE step also, if you Get ORG URL size = 0,
					 Then use the URL list from old orgNameURListWithAllWebSites to get all URL**/
					if(finalUniqueURLSet.size()==0){
						finalUniqueURLSet.addAll(orgNameURListWithAllWebSites);
					}
				}
				// ############## This is FINAL MATCHED URL List ############## //
				List<String> orgNameMatchedURList = new ArrayList<>(finalUniqueURLSet);

				String writeBasicDataForValidationMethodStatus = "notChecked";
				String orgNameMethodStatus = "notChecked";
				//String providerNameMethodStatus = "notChecked";
				//int executingURLCount_ForProviderName = 1;
				String phoneNumberMethodStatus = "notChecked";
				int executingURLCount_ForPhoneNumber = 1;
				//String addressMethodStatus = "notChecked";
				//int executingURLCount_ForAddress = 1;
				//String extractedWebContentAddress="null";

				if(orgNameMatchedURList.size()==0){
					/*phoneValidationPriority,OrganizationPhoneValidation values will be filled in to R3 report excel*/
					if (writeBasicDataForValidationMethodStatus.equalsIgnoreCase("notChecked")) {
						objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,OrganizationPhoneValidation);
						writeBasicDataForValidationMethodStatus = "checked";
					}
					ExtentManager.getExtentTest().log(Status.FAIL,("Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report) with this search keyword ->> "+ combinedSearchKeyword_OrgProvPhone),
							MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
					String ORGNameMatchingStatus = "FAIL";
					objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
							"Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");
					String PhoneNumberMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,
							"Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");

					String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
					String OV_PhoneFoundOrgWebsitesMatchingStatus = "NO NEED";
					objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
							OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

					String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
					String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
							OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

					PhoneNumberMatchingStatus = "No-Need";
					String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
					String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
					String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
					OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
					String remarkScope = "ORG-NAME";
					objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
							PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
							OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
							reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
					ExtentManager.getExtentTest().log(Status.FAIL,("======================================================================================================="));
				}

				for(int i=0; i<orgNameMatchedURList.size(); i++) {
					/*phoneValidationPriority,ProviderAndOrgPhoneValidation,OrganizationPhoneValidation,ProviderPhoneValidation values will be filled in to R3 report excel*/
					if (writeBasicDataForValidationMethodStatus.equalsIgnoreCase("notChecked")) {
						objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,OrganizationPhoneValidation);
						writeBasicDataForValidationMethodStatus = "checked";
					}

					/*ORG Name result will be written in to Test result excel*/
					if(orgNameMethodStatus.equalsIgnoreCase("notChecked")) {
						ExtentManager.getExtentTest().log(Status.PASS, ("ORG Name is Matching in R3 excel and Web site ->> " + orgNameMatchedURList.get(i) ));
						String ORGNameMatchingStatus = "PASS";
						objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File, executingRowIndex, ORGNameMatchingStatus,orgNameMatchedURList.get(i));

						String PhoneNumberMatchingStatus = "No-Need";
						String OVPhoneFoundWebsitesMatchingStatus = "NOT-YET";
						String OVPhoneFoundORGWebsitesMatchingStatus = "NOT-YET";
						String OVPhoneNOTFoundWebsitesMatchingStatus = "NOT-YET";
						String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT-YET";
						String remarkScope = "ORG-NAME";
						objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
								PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
								OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
								reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
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

						} catch (TimeoutException ee) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Broken Website Issue  ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");

							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							PhoneNumberMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							break;
						}
						catch (NullPointerException ex) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Empty Website Content Issue ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");

							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							PhoneNumberMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
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
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");

							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							PhoneNumberMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							break;
						}
						catch (NullPointerException ex) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Empty Website Content Issue ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ORGNameMatchingStatus = "FAIL-BROKEN-WEBSITE";
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");

							String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneFoundOrgWebsitesMatchingStatus = "NO NEED";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

							String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
									OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

							PhoneNumberMatchingStatus = "No-Need";
							String OVPhoneFoundWebsitesMatchingStatus = "No-Need";
							String OVPhoneFoundORGWebsitesMatchingStatus = "No-Need";
							String OVPhoneNOTFoundWebsitesMatchingStatus = "No-Need";
							OV_PhoneNOTFoundORGWebsitesMatchingStatus = "No-Need";
							String remarkScope = "ORG-NAME";
							objWriteR3TestResult.writeRemarks_Accurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
									PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
									OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
									reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
							break;
						}
					}

					/*Searching the Provider Name in web content starts here*//*
				if (providerNameMethodStatus.equalsIgnoreCase("notChecked")) {
					if(actualMiddleName.equalsIgnoreCase("null")) {
						String providerNameFormat0 = firstName + " " + lastName;
						Pattern pattern0 = Pattern.compile(Pattern.quote(providerNameFormat0), Pattern.CASE_INSENSITIVE);
						Matcher matcher0 = pattern0.matcher(webContent);

						String providerNameFormat1 = firstName + lastName;
						Pattern pattern1 = Pattern.compile(Pattern.quote(providerNameFormat1), Pattern.CASE_INSENSITIVE);
						Matcher matcher1 = pattern1.matcher(webContent);

						if (matcher0.find() || matcher1.find()) {
							ExtentManager.getExtentTest().log(Status.PASS, ("Provider Name is Matching in R3 excel and Web site " + Url + " >>> " + providerNameFormat0),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ProviderNameMatchingStatus = "PASS";
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus,phoneValidationPriority,Url);
							providerNameMethodStatus = "checked";
						} else {
							if(executingURLCount_ForProviderName==orgNameMatchedURList.size()) {
								ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Provider Name "+ providerNameFormat0 + " is NOT found in these Web site list:  " + orgNameMatchedURList),
										MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
								String ProviderNameMatchingStatus = "FAIL";
								objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus,phoneValidationPriority,Url);
							}
							executingURLCount_ForProviderName++;
						}
					}
					else {
						String providerNameFormat0_1 = firstName + " " + lastName;
						Pattern pattern0_1 = Pattern.compile(Pattern.quote(providerNameFormat0_1), Pattern.CASE_INSENSITIVE);
						Matcher matcher0_1 = pattern0_1.matcher(webContent);

						String providerNameFormat1_1 = firstName + lastName;
						Pattern pattern1_1 = Pattern.compile(Pattern.quote(providerNameFormat1_1), Pattern.CASE_INSENSITIVE);
						Matcher matcher1_1 = pattern1_1.matcher(webContent);

						String providerNameFormat2_1 = firstName + " " + actualMiddleName + " " + lastName;
						Pattern pattern2_1 = Pattern.compile(Pattern.quote(providerNameFormat2_1), Pattern.CASE_INSENSITIVE);
						Matcher matcher2_1 = pattern2_1.matcher(webContent);

						String providerNameFormat2 = firstName + " " + actualMiddleName + ". " + lastName;
						Pattern pattern2 = Pattern.compile(Pattern.quote(providerNameFormat2), Pattern.CASE_INSENSITIVE);
						Matcher matcher2 = pattern2.matcher(webContent);

						String providerNameFormat3 = firstName + "-" + actualMiddleName + ".-" + lastName;
						Pattern pattern3 = Pattern.compile(Pattern.quote(providerNameFormat3), Pattern.CASE_INSENSITIVE);
						Matcher matcher3 = pattern3.matcher(webContent);

						String providerNameFormat4 = firstName + "-" + actualMiddleName + "-" + lastName;
						Pattern pattern4 = Pattern.compile(Pattern.quote(providerNameFormat4), Pattern.CASE_INSENSITIVE);
						Matcher matcher4 = pattern4.matcher(webContent);

						String providerNameFormat5 = firstName + "_" + actualMiddleName + "_" + lastName;
						Pattern pattern5 = Pattern.compile(Pattern.quote(providerNameFormat5), Pattern.CASE_INSENSITIVE);
						Matcher matcher5 = pattern5.matcher(webContent);

						String providerOnlyMiddleNameNameFormat6 = "\\b" + Pattern.quote(firstName) + " " + Pattern.quote(actualMiddleName) + "\\S* " + Pattern.quote(lastName) + "\\b";
						Pattern pattern6 = Pattern.compile(providerOnlyMiddleNameNameFormat6);
						Matcher matcher6 = pattern6.matcher(webContent);

						if (matcher1_1.find() || matcher0_1.find() ||
							matcher2_1.find() || matcher2.find() ||
							matcher3.find() || matcher4.find() ||
							matcher5.find() || matcher6.find()) {
							ExtentManager.getExtentTest().log(Status.PASS, ("Provider Name is Matching in R3 excel and Web site " + Url + " >>> " + providerNameFormat2_1),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String ProviderNameMatchingStatus = "PASS";
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus,phoneValidationPriority,Url);
							providerNameMethodStatus = "checked";
						} else {
							if(executingURLCount_ForProviderName==orgNameMatchedURList.size()) {
								ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Provider Name "+ providerNameFormat2_1 + " is NOT found in these Web site list:  " + orgNameMatchedURList),
										MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
								String ProviderNameMatchingStatus = "FAIL";
								objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus,phoneValidationPriority,Url);
							}
							executingURLCount_ForProviderName++;
						}
					}
				}
				*//*Searching the Provider Name in web content ends here*/

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

					/*Searching the Phone number in web content starts here*/
					if(phoneNumberMethodStatus.equalsIgnoreCase("notChecked")) {
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
							if (webContent!=null && (webContent.contains(eachPhoneFormat) && !webContent.toLowerCase().contains("fax: "+eachPhoneFormat))) { //new
								ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Phone Number is Still Matching in ORG Web site. R3 says OV is Inaccurate " + Url + " >>> " + eachPhoneFormat),
										MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
								String PhoneNumberMatchingStatus = "FAIL";
								objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,Url);

								String OV_PhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
								String OV_PhoneFoundOrgWebsitesMatchingStatus = "NO NEED";
								objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
										OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

								String OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
								String OV_PhoneNOTFoundORGWebsitesMatchingStatus = "NOT APPLICABLE";
								objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
										OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

								String ORGNameMatchingStatus = "No-Need";
								String OVPhoneFoundWebsitesMatchingStatus = "NOT APPLICABLE";
								String OVPhoneFoundORGWebsitesMatchingStatus = OV_PhoneFoundOrgWebsitesMatchingStatus;
								String OVPhoneNOTFoundWebsitesMatchingStatus = "NOT APPLICABLE";
								String remarkScope = "ORG-PHONE";
								objWriteR3TestResult.writeRemarks_Inaccurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
										PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
										OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
										reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
								break;

							}
							else if ((webContent!=null && !webContent.contains(eachPhoneFormat) ||
									webContent.toLowerCase().contains("fax: "+eachPhoneFormat)) &&
									(count == 14 && executingURLCount_ForPhoneNumber==orgNameMatchedURList.size())) { //new
								ExtentManager.getExtentTest().log(Status.PASS, ("R3 Report's Phone Number ("+ eachPhoneFormat + ") is NOT found in ORG Web site list:  " + orgNameMatchedURList),
										MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
								String PhoneNumberMatchingStatus = "PASS";
								objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,orgNameMatchedURList); //new

								String OV_PhoneFoundWebsitesMatchingStatus = get_AllNOTFoundWebsiteStatus(OV_Phone_Found_Websites,Url);
								String OV_PhoneFoundOrgWebsitesMatchingStatus = "NO NEED";
								objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,
										OV_PhoneFoundWebsitesMatchingStatus,OV_PhoneFoundOrgWebsitesMatchingStatus);

								String OV_Phone_WebsiteNOTMatchingStatus = get_AllFoundWebsiteStatus(OV_Phone_Not_Found_Websites,orgNameMatchedURList);
								String OV_PhoneNOTFoundORGWebsitesMatchingStatus = get_AllFoundWebsiteStatus(OV_Phone_Not_Found_Organization_Websites,orgNameMatchedURList);
								objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,
										OV_Phone_WebsiteNOTMatchingStatus,OV_PhoneNOTFoundORGWebsitesMatchingStatus);

								String ORGNameMatchingStatus = "No-Need";
								String OVPhoneFoundWebsitesMatchingStatus = OV_PhoneFoundWebsitesMatchingStatus;
								String OVPhoneFoundORGWebsitesMatchingStatus = OV_PhoneFoundOrgWebsitesMatchingStatus;
								String OVPhoneNOTFoundWebsitesMatchingStatus = OV_Phone_WebsiteNOTMatchingStatus;
								String remarkScope = "ORG-PHONE";
								objWriteR3TestResult.writeRemarks_Inaccurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
										PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
										OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
										reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);

								//OV_Phone_Not_Found_Websites_Status remarks
								if(OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")){
									remarkScope = "OV_Phone_Not_Found_Websites_Status";
									objWriteR3TestResult.writeRemarks_Inaccurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
											PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
											OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
								}else if(OVPhoneNOTFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")){
									remarkScope = "OV_Phone_Not_Found_Websites_Status";
									objWriteR3TestResult.writeRemarks_Inaccurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
											PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
											OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
								}

								//OV_Phone_Not_Found_Organization_Websites_Status remarks
								if(OV_PhoneNOTFoundORGWebsitesMatchingStatus.equalsIgnoreCase("FAIL")){
									remarkScope = "OV_Phone_Not_Found_Organization_Websites_Status";
									objWriteR3TestResult.writeRemarks_Inaccurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
											PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
											OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
								}else if(OV_PhoneNOTFoundORGWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")){
									remarkScope = "OV_Phone_Not_Found_Organization_Websites_Status";
									objWriteR3TestResult.writeRemarks_Inaccurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
											PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
											OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
								}

								//OV_Phone_Found_Websites remarks
								if(OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("FAIL")){
									remarkScope = "OV_Phone_Found_Websites_Status";
									objWriteR3TestResult.writeRemarks_Inaccurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
											PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
											OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
								}else if(OVPhoneFoundWebsitesMatchingStatus.equalsIgnoreCase("NOT APPLICABLE")){
									remarkScope = "OV_Phone_Found_Websites_Status";
									objWriteR3TestResult.writeRemarks_Inaccurate(clonedR3File,executingRowIndex,ORGNameMatchingStatus,
											PhoneNumberMatchingStatus,OVPhoneFoundWebsitesMatchingStatus,
											OVPhoneFoundORGWebsitesMatchingStatus,OVPhoneNOTFoundWebsitesMatchingStatus,
											reverseURLMatchFlag, remarkScope,allSearchString, OV_PhoneNOTFoundORGWebsitesMatchingStatus);
								}
								//OV_Phone_Found_Organization_Websites_Status is no need to mention here
								phoneNumberMethodStatus="checked";
							}
						}
						executingURLCount_ForPhoneNumber++;
					}
					/*Searching the Phone Number in web content ends here*/

					/*Any FAIL condition validating starts here*/
					if(objWriteR3TestResult.getTestResultInAccurate(clonedR3File, executingRowIndex)){
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






























