package com.r3.pageobjects;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.r3.utility.ExtentManager;
import com.r3.utility.HTMLCode;
import com.r3.utility.Screenshot;
import com.r3.utility.WriteR3TestResult;
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

public class GoogleSearchKeywordPageObjects {
	WebDriver driver;
	String Url = null;
	WebDriverWait wait;
	public GoogleSearchKeywordPageObjects(WebDriver TestBaseClassDriver) {
		driver = TestBaseClassDriver;
		PageFactory.initElements(TestBaseClassDriver, this);
	}

	@FindBy(xpath= GoogleSearchKeywordPage_WebElements.Const_googleSearchTextFiled)
	public WebElement googleSearchTextFiled;
	@FindBy(xpath= GoogleSearchKeywordPage_WebElements.Const_googleSearchResultsList)
	public List<WebElement> googleSearchResultsList;
	@FindBy(tagName= GoogleSearchKeywordPage_WebElements.Const_webSiteBodyContent)
	public WebElement webSiteBodyContent;


	public void validateORGPROVInfo_WithPriority1to3(String clonedR3File, int executingRowIndex, String phoneValidationPriority,
													 String ProviderAndOrgPhoneValidation, String OrganizationPhoneValidation,
													 String ProviderPhoneValidation, String combinedSearchKeyword_OrgProvPhone,
													 LinkedHashSet<String> orgNameKey, String areaCode, String exchangeCode, String lineNumber,
													 String firstName, String lastName, String actualMiddleName, String completeAddress,
													 Set<String> PV_Phone_Found_Websites,Set<String> PV_Phone_Found_Organization_Websites,
													 Set<String> OV_Phone_Found_Websites,Set<String> OV_Phone_Found_Organization_Websites,
													 Set<String> PV_Phone_Not_Found_Websites,Set<String> OV_Phone_Not_Found_Websites) throws IOException {
		WriteR3TestResult objWriteR3TestResult = new WriteR3TestResult();
		wait = new WebDriverWait(driver, 30);
		try {
			driver.get("https://www.google.com");
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
			googleSearchTextFiled.sendKeys(combinedSearchKeyword_OrgProvPhone, Keys.ENTER);
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
			List<WebElement> linkHeaderList = googleSearchResultsList;
			LinkedHashSet<String> orgNameMatchedURSetDataType = new LinkedHashSet<>();
			int eachWebLinkCount=1;
			for (WebElement webElement : linkHeaderList) {
				String eachSearchLinkHeaderWithoutSpace = webElement.getText().toLowerCase().replace(" ", "");
				for (String eachOrgNameKey : orgNameKey){
					String orgNameKeyWithoutSpace = eachOrgNameKey.toLowerCase().replace(" ", "");
					if ((compareGivenStringsWithParticularPercentage(orgNameKeyWithoutSpace, eachSearchLinkHeaderWithoutSpace, 60.0) ||
							compareGivenStringsWithParticularPercentage(eachSearchLinkHeaderWithoutSpace, orgNameKeyWithoutSpace, 60.0))
							&& eachSearchLinkHeaderWithoutSpace.length() != 0 && !eachSearchLinkHeaderWithoutSpace.startsWith("facebook")
							&& !eachSearchLinkHeaderWithoutSpace.startsWith("linkedin")) {
						Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL(eachWebLinkCount))).getAttribute("href");
						if(!Url.toLowerCase().startsWith("https://www.facebook.") && !Url.toLowerCase().startsWith("https://www.linkedin.")) {
							orgNameMatchedURSetDataType.add(Url);
							break;
						}
					}else{
						String[] eachWordOfR3OrgNameList = eachOrgNameKey.toLowerCase()
								.replace(" or ", "")
								.replace(" and ", "").split("\\s");
						String[] eachSearchLinkHeaderList = webElement.getText().toLowerCase().split("\\s");
						for(String s1 : eachWordOfR3OrgNameList){
							for(String s2: eachSearchLinkHeaderList){
								if (compareStringsWithGivenPercentage(s1, s2, 75.0)) {
									Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL(eachWebLinkCount))).getAttribute("href");
									if(!Url.toLowerCase().startsWith("https://www.facebook.") && !Url.toLowerCase().startsWith("https://www.linkedin.")) {
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
			// Convert the set to list to traverse. This list may have some in appropriate URL also. We have a code in downside to filter
			List<String> orgNameURListWithAllWebSites = new ArrayList<>(orgNameMatchedURSetDataType );

            ExtentManager.getExtentTest().log(Status.INFO, ("Search Keyword ->> " + combinedSearchKeyword_OrgProvPhone ));

			if(orgNameURListWithAllWebSites.isEmpty()||orgNameURListWithAllWebSites.size()==0){
				ExtentManager.getExtentTest().log(Status.FAIL,("We are not able to find the ORG Web Site link with this search keyword ->> "+ combinedSearchKeyword_OrgProvPhone),
						MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
				objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,ProviderAndOrgPhoneValidation,
						OrganizationPhoneValidation,ProviderPhoneValidation);
				String ORGNameMatchingStatus = "FAIL";
				objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,phoneValidationPriority,Url);
				String PhoneNumberMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,phoneValidationPriority,Url);
				String ProviderNameMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File,executingRowIndex,ProviderNameMatchingStatus,phoneValidationPriority,Url);
				String AddressMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writeAddressMatchStatus(clonedR3File, executingRowIndex, AddressMatchingStatus,phoneValidationPriority,Url);
				String PV_OV_Phone_WebsiteMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteMatchingStatus);
				String PV_OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
				objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteNOTMatchingStatus);
			}
			else{
				String extractedURLString = orgNameURListWithAllWebSites.get(0)
						.replaceAll("https?://(?:www\\.)?(\\w+\\.\\w+).*", "$1")
						.replaceAll("\\.\\w+", "");

				List<String> orgNameMatchedURList = filterSimilarWebsites(orgNameURListWithAllWebSites, extractedURLString); //Final URL List

				String writeBasicDataForValidationMethodStatus = "notChecked";
				String orgNameMethodStatus = "notChecked";
				String providerNameMethodStatus = "notChecked";
				int executingURLCount_ForProviderName = 1;
				String phoneNumberMethodStatus = "notChecked";
				int executingURLCount_ForPhoneNumber = 1;
				String addressMethodStatus = "notChecked";
				int executingURLCount_ForAddress = 1;
				String extractedWebContentAddress="null";

				if(orgNameMatchedURList.size()==0){
					/*phoneValidationPriority,ProviderAndOrgPhoneValidation,OrganizationPhoneValidation,ProviderPhoneValidation values will be filled in to R3 report excel*/
					if (writeBasicDataForValidationMethodStatus.equalsIgnoreCase("notChecked")) {
						objWriteR3TestResult.writeBasicDataForValidation(clonedR3File, executingRowIndex, phoneValidationPriority, ProviderAndOrgPhoneValidation,
								OrganizationPhoneValidation, ProviderPhoneValidation);
						writeBasicDataForValidationMethodStatus = "checked";
					}
					ExtentManager.getExtentTest().log(Status.FAIL,("Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report) with this search keyword ->> "+ combinedSearchKeyword_OrgProvPhone),
							MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
					objWriteR3TestResult.writeBasicDataForValidation(clonedR3File,executingRowIndex,phoneValidationPriority,ProviderAndOrgPhoneValidation,
							OrganizationPhoneValidation,ProviderPhoneValidation);
					String ORGNameMatchingStatus = "FAIL";
					objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File,executingRowIndex,ORGNameMatchingStatus,phoneValidationPriority,
							 "Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");
					String PhoneNumberMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File,executingRowIndex,PhoneNumberMatchingStatus,phoneValidationPriority,
							"Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");
					String ProviderNameMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File,executingRowIndex,ProviderNameMatchingStatus,phoneValidationPriority,
							"Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");
					String AddressMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writeAddressMatchStatus(clonedR3File, executingRowIndex, AddressMatchingStatus,phoneValidationPriority,
							"Not able to find the ORG Web Site link since none of the ORG NAME WEB SITE LIKE HEADER is matching in R3 Test Report (Provide the valid ORG Name in R3 Test Report)");
					String PV_OV_Phone_WebsiteMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteMatchingStatus);
					String PV_OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
					objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteNOTMatchingStatus);
				}

				for(int i=0; i<orgNameMatchedURList.size(); i++) {
					/*phoneValidationPriority,ProviderAndOrgPhoneValidation,OrganizationPhoneValidation,ProviderPhoneValidation values will be filled in to R3 report excel*/
					if (writeBasicDataForValidationMethodStatus.equalsIgnoreCase("notChecked")) {
						objWriteR3TestResult.writeBasicDataForValidation(clonedR3File, executingRowIndex, phoneValidationPriority, ProviderAndOrgPhoneValidation,
								OrganizationPhoneValidation, ProviderPhoneValidation);
						writeBasicDataForValidationMethodStatus = "checked";
					}

					/*ORG Name result will be written in to Test result excel*/
					if(orgNameMethodStatus.equalsIgnoreCase("notChecked")) {
						ExtentManager.getExtentTest().log(Status.PASS, ("ORG Name is Matching in R3 excel and Web site ->> " + orgNameMatchedURList.get(i) ));
						String ORGNameMatchingStatus = "PASS";
						objWriteR3TestResult.writeORGNameMatchStatus(clonedR3File, executingRowIndex, ORGNameMatchingStatus,phoneValidationPriority,orgNameMatchedURList.get(i));
						orgNameMethodStatus = "checked";
					}

					String webContent = null;
					try {
						Url =orgNameMatchedURList.get(i);
						webContent = HTMLCode.get(Url);
						ExtentManager.getExtentTest().log(Status.INFO, ("Web site content is getting from HTTPClient ->> " + Url));
					} catch (SSLHandshakeException e) {
						ExtentManager.getExtentTest().log(Status.INFO, ("Web site content is getting from Website Body Tag ->> " + Url));
						try {
							((RemoteWebDriver) driver).executeScript("window.location.href='" + Url +"';" + "setTimeout(function(){throw new Error('Timeout after 10 seconds');},15000);"); // Entering each URL
							if(webSiteBodyContent.getText()!=null) {
								webContent = webSiteBodyContent.getText();
							}

						} catch (TimeoutException ee) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Broken Website Issue  ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus, phoneValidationPriority,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");
							String ProviderNameMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus, phoneValidationPriority,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");
							String AddressMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writeAddressMatchStatus(clonedR3File, executingRowIndex, AddressMatchingStatus, phoneValidationPriority,
									Url + " ->> Not able to get the Web content due to Broken Website Issue");
							String PV_OV_Phone_WebsiteMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteMatchingStatus);
							String PV_OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteNOTMatchingStatus);
							break;
						}
						catch (NullPointerException ex) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("Not able to get the Web content due to Empty Website Content Issue ->> " + Url),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String PhoneNumberMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus, phoneValidationPriority,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");
							String ProviderNameMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writeProviderNameMatchStatus(clonedR3File, executingRowIndex, ProviderNameMatchingStatus, phoneValidationPriority,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");
							String AddressMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writeAddressMatchStatus(clonedR3File, executingRowIndex, AddressMatchingStatus, phoneValidationPriority,
									Url + " ->> Not able to get the Web content due to Empty Website Content Issue");
							String PV_OV_Phone_WebsiteMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteMatchingStatus);
							String PV_OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteNOTMatchingStatus);
							break;
						}
					}

				/*Searching the Provider Name in web content starts here*/
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
				/*Searching the Provider Name in web content ends here*/

				/*Searching the Address in web content starts here*/
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
				/*Searching the Address in web content ends here*/

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
						if (webContent!=null && webContent.contains(eachPhoneFormat)) {
							ExtentManager.getExtentTest().log(Status.PASS, ("Phone Number is Matching in R3 excel and Web site " + Url + " >>> " + eachPhoneFormat),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());

							String PhoneNumberMatchingStatus = "PASS";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,phoneValidationPriority,Url);

							String PV_OV_Phone_WebsiteMatchingStatus = get_AllFoundWebsiteStatus(PV_Phone_Found_Websites,PV_Phone_Found_Organization_Websites,
									OV_Phone_Found_Websites,OV_Phone_Found_Organization_Websites,Url);
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteMatchingStatus);

							String PV_OV_Phone_WebsiteNOTMatchingStatus = get_AllNOTFoundWebsiteStatus(PV_Phone_Not_Found_Websites,OV_Phone_Not_Found_Websites,Url);
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteNOTMatchingStatus);

							phoneNumberMethodStatus="checked";
							break;
						}
						else if (count == 14 && executingURLCount_ForPhoneNumber==orgNameMatchedURList.size()) {
							ExtentManager.getExtentTest().log(Status.FAIL, ("R3 Report's Phone Number ("+ eachPhoneFormat + ") is NOT found in these Web site list:  " + orgNameMatchedURList),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							String PhoneNumberMatchingStatus = "FAIL";
							objWriteR3TestResult.writePhoneNumberMatchStatus(clonedR3File, executingRowIndex, PhoneNumberMatchingStatus,phoneValidationPriority,Url);
							String PV_OV_Phone_WebsiteMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteMatchingStatus);
							String PV_OV_Phone_WebsiteNOTMatchingStatus = "NOT APPLICABLE";
							objWriteR3TestResult.writePhoneNumberNOTFoundURLMatchStatus(clonedR3File,executingRowIndex,PV_OV_Phone_WebsiteNOTMatchingStatus);
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
	public String get_AllFoundWebsiteStatus(Set<String> websiteList1, Set<String> websiteList2,
								Set<String> websiteList3, Set<String> websiteList4, String Url){
		String foundURL = Url.replace("https://", "").replace("http://", "").replace("www.","").split("/")[0];
		Set<String> foundWebsiteFilter1 = websiteList1.stream().map(s->s.split("\\|")[0]).collect(Collectors.toSet());
		Set<String> foundWebsiteFilter2 = websiteList2.stream().map(s->s.split("\\|")[0]).collect(Collectors.toSet());
		Set<String> foundWebsiteFilter3 = websiteList3.stream().map(s->s.split("\\|")[0]).collect(Collectors.toSet());
		Set<String> foundWebsiteFilter4 = websiteList4.stream().map(s->s.split("\\|")[0]).collect(Collectors.toSet());

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

		boolean foundWebsiteFilter2MatchingStatus = false;
		for(String eachWebSite : foundWebsiteFilter2){
			if(eachWebSite.toLowerCase().replace("https://", "")
					.replace("http://", "")
					.replace("www.","")
					.replace("/","")
					.equalsIgnoreCase(foundURL.toLowerCase())){
				foundWebsiteFilter2MatchingStatus = true;
				break;
			}
		}

		boolean foundWebsiteFilter3MatchingStatus = false;
		for(String eachWebSite : foundWebsiteFilter3){
			if(eachWebSite.toLowerCase().replace("https://", "")
					.replace("http://", "")
					.replace("www.","")
					.replace("/","")
					.equalsIgnoreCase(foundURL.toLowerCase())){
				foundWebsiteFilter3MatchingStatus = true;
				break;
			}
		}
		boolean foundWebsiteFilter4MatchingStatus = false;
		for(String eachWebSite : foundWebsiteFilter4){
			if(eachWebSite.toLowerCase().replace("https://", "")
					.replace("http://", "")
					.replace("www.","")
					.replace("/","")
					.equalsIgnoreCase(foundURL.toLowerCase())){
				foundWebsiteFilter4MatchingStatus = true;
				break;
			}
		}

		if(foundWebsiteFilter1MatchingStatus && foundWebsiteFilter2MatchingStatus &&
				foundWebsiteFilter3MatchingStatus && foundWebsiteFilter4MatchingStatus){
			result = "PASS";
		}
		return result;
	}
	public String get_AllNOTFoundWebsiteStatus(Set<String> websiteList1, Set<String> websiteList2, String Url){
		String foundURL = Url.replace("https://", "").replace("http://", "").replace("www.","").split("/")[0];
		Set<String> notFoundWebsiteFilter1 = websiteList1.stream().map(s->s.split("\\|")[0]).collect(Collectors.toSet());
		Set<String> notFoundWebsiteFilter2 = websiteList2.stream().map(s->s.split("\\|")[0]).collect(Collectors.toSet());

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

		boolean NotFoundWebsiteFilter2MatchingStatus = false;
		for(String eachWebSite : notFoundWebsiteFilter2){
			if(!eachWebSite.toLowerCase().replace("https://", "")
					.replace("http://", "")
					.replace("www.","")
					.replace("/","")
					.equalsIgnoreCase(foundURL.toLowerCase())){
				NotFoundWebsiteFilter2MatchingStatus = true;
				break;
			}
		}

		if(NotFoundWebsiteFilter1MatchingStatus && NotFoundWebsiteFilter2MatchingStatus){
			result = "PASS";
		}
		return result;
	}


}






























