package com.r3.pageobjects;

import com.r3.utility.Fuzzy;
import com.r3.utility.JaccardLogic;
import com.r3.utility.OrgWebsiteFinder;
import com.r3.utility.WriteR3TestResult;
import com.r3.webelements.GoogleSearchKeywordPage_WebElements;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WebScrap_ORG_Website_Cache {
	WebDriver driver;
	String Url = null;
	WebDriverWait wait;

	public WebScrap_ORG_Website_Cache(WebDriver TestBaseClassDriver) {
		driver = TestBaseClassDriver;
		PageFactory.initElements(TestBaseClassDriver, this);
	}

	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSideBarORGTitle)
	public WebElement googleSideBarORGTitle;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSideBarORGWebSiteURL)
	public WebElement googleSideBarORGWebSiteURL;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSideBarStyle2RGTitle)
	public WebElement googleSideBarStyle2RGTitle;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSideBarStyle2ORGWebSiteURL)
	public WebElement googleSideBarStyle2RGWebSiteURL;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googlePlacesORGLinkHeader)
	public List<WebElement> googlePlacesORGLinkHeader;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSearchTextFiled)
	public WebElement googleSearchTextFiled;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSearchResultsList1)
	public List<WebElement> googleSearchResultsList1;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSearchResultsList2)
	public List<WebElement> googleSearchResultsList2;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSearchResultsList3)
	public List<WebElement> googleSearchResultsList3;
	@FindBy(xpath = GoogleSearchKeywordPage_WebElements.Const_googleSearchResultsList4)
	public List<WebElement> googleSearchResultsList4;
	@FindBy(tagName = GoogleSearchKeywordPage_WebElements.Const_webSiteContent)
	public WebElement webSiteContent;

	List<WebElement> linkHeaderList = null;
	String elementURLPath = null;
	String OrgURLFoundStatus = null;
	String finalORGUrl = null;

	public void check_ORG_Website_Cache_Accuracy(String clonedR3File, int executingRowIndex, LinkedHashSet<String> orgNameKey, String State, String Sites) throws IOException, InterruptedException {
		WriteR3TestResult objWriteR3TestResult = new WriteR3TestResult();
		wait = new WebDriverWait(driver, 30);
		driver.manage().deleteAllCookies();
		driver.get("chrome://settings/clearBrowserData");
		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		driver.get("https://www.google.com");
		boolean isUrlGotFromSiderBarOrPlacesLogic=false;

		LinkedHashSet<String> orgNameMatchedURSetDataType = new LinkedHashSet<>();
		//1st Search String is "providerName + credentials + state AND phone number" P1
		if(State.equalsIgnoreCase("CA")){
			State = State.replace("CA", "California");
		}
		String orgNameAndState = orgNameKey.toString() + " " + State;
		String orgName = orgNameKey.toString();
		//Adding all Search String
		List<String> searchStringList = new ArrayList<>(Arrays.asList(orgNameAndState, orgName));
		List<String> searchStringListOnlyOne = new ArrayList<>(Arrays.asList(orgName));
		List<String> cleanedList = new ArrayList<>();
		for (String str : searchStringList) {
			// Remove the opening and closing brackets
			String cleanedString = str.substring(1, str.indexOf("]")) + str.substring(str.indexOf("]") + 1);
			cleanedList.add(cleanedString);
		}
		/*ORG Name Various combinations of Test data - Start*/
		String orgFullName = cleanedList.get(1).toLowerCase().replace(".", "").replace("-", " ");; //First Input
		String firstWordOfOrgName = orgFullName.split("\\s")[0]; //Second Input

		// Word list to remove from the string
		List<String> wordList = new ArrayList<>(Arrays.asList("and", "or", "in", "for", "&", "of", "on", "is", "at", "has", "had", "a", "to"));
		// Remove words from the given string
		String cleanedORGNameString = removeWords(orgFullName, wordList);
		// first character of each word in the ORG Name
		StringBuilder stringFirstCharOfEachWordBuilder = new StringBuilder();
		String[] wordsFCEA = cleanedORGNameString.split("\\s+");
		if(wordsFCEA.length >1) {
			for (String word : wordsFCEA) {
				stringFirstCharOfEachWordBuilder.append(word.charAt(0));
			}
		}else {
			stringFirstCharOfEachWordBuilder.append("########");
		}
		String firstCharOfEachWordOrgName = stringFirstCharOfEachWordBuilder.toString();//Expected string

		// Split the cleaned string into words
		String[] wordsFourOrThree = cleanedORGNameString.split("\\s+");
		// Initialize a StringBuilder to store the first characters of each word
		StringBuilder stringFirstFourOrThreeWordsBuilder = new StringBuilder();
		// Iterate over the words and append the first character of each word
		for (int i = 0; i < Math.min(wordsFourOrThree.length, 4); i++) {
			if (wordsFourOrThree[i].length() >= 4) {
				stringFirstFourOrThreeWordsBuilder.append(wordsFourOrThree[i].charAt(0));
			} else if (wordsFourOrThree[i].length() == 3) {
				stringFirstFourOrThreeWordsBuilder.append(wordsFourOrThree[i].charAt(0));
			}
		}
		// Get the resulting string
		String firstCharOfFirstFourOrThreeWordsOrgName = stringFirstFourOrThreeWordsBuilder.toString();

		String orgFullNameWithOnlyMainWord = orgFullName.replace(".com", "").
				replace(".gov", "").
				replace(".edu", "").
				replace(".", "").
				replace("-", "").
				replace(" ", "").
				replace("https://www.", "").
				replace("www.", "");

		String[] twoWordsArrayOrgName = orgFullName.split("\\s");
		String firstTwoWordsOfOrgName=null; //fourth Input
		if(twoWordsArrayOrgName.length>=2) {
			firstTwoWordsOfOrgName = twoWordsArrayOrgName[0]+" " + twoWordsArrayOrgName[1];
		}else if(twoWordsArrayOrgName.length<=1){
			firstTwoWordsOfOrgName = twoWordsArrayOrgName[0];
		}
		/*ORG Name Various combinations of Test data - End*/

		/** Search Area 1: Google Side Bar **/
		boolean googleBackwardFlag1 = false;
		if (googleBackwardFlag1) {
			driver.navigate().back();
		}
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		googleSearchTextFiled.sendKeys(cleanedList.get(0), Keys.ENTER);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		wait = new WebDriverWait(driver, 5);

		/** Search Area 1.1: Google Side Bar Style 1 Starts here**/
		if(orgNameMatchedURSetDataType.size()==0) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(GoogleSearchKeywordPage_WebElements.Const_googleSideBarORGTitle)));
				/*Link Header Various combinations of Test data - Start*/
				String linkHeader = googleSideBarORGTitle.getText().toLowerCase();
				String firstWordOfLinkHeader = linkHeader.split("\\s")[0];
				// first character of each word in the Link Header
				StringBuilder stringToBuilderLinkHeader = new StringBuilder();
				String[] wordsArray = linkHeader.toLowerCase().split("\\s+");
				for (String eachWord : wordsArray) {
					stringToBuilderLinkHeader.append(eachWord.charAt(0));
				}
				String firstCharOfEachWordLinkHeader = stringToBuilderLinkHeader.toString();

				String linkHeadereWithOnlyMainWord = linkHeader.replace(".com", "").
						replace(".gov", "").
						replace(".edu", "").
						replace(".", "").
						replace("-", "").
						replace(" ", "").
						replace("https://www.", "").
						replace("www.", "");
				/*Link Header Various combinations of Test data - End*/
				String URLName = googleSideBarORGWebSiteURL.getAttribute("href");
				Set<String> ORGUrl1 = OrgWebsiteFinder.findUniqueUrls(URLName);
				URLName = ORGUrl1.stream().findFirst().orElse(null);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(GoogleSearchKeywordPage_WebElements.Const_googleSideBarORGWebSiteURL)));
				if (Fuzzy.searchLogic(75, orgFullName, linkHeader, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(60, firstWordOfOrgName, firstCharOfEachWordLinkHeader, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(75, orgFullNameWithOnlyMainWord, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstWordOfOrgName, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, firstCharOfEachWordLinkHeader, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, firstWordOfLinkHeader, orgFullName, linkHeader)) {
					if (!URLName.startsWith("https://www.google.com") && !URLName.contains(".gov")  && !URLName.contains(".edu")) {
						orgNameMatchedURSetDataType.add(googleSideBarORGWebSiteURL.getAttribute("href"));
						isUrlGotFromSiderBarOrPlacesLogic = true;
						System.out.println("**** Search Area 1.1  -> Google Sider Bar Style 1: Got the ORG Name and Added the URL *****");
					}
				}else if (googleSideBarORGWebSiteURL.isDisplayed() && !URLName.startsWith("https://www.google.com") && !URLName.contains(".gov")  && !URLName.contains(".edu")){
					orgNameMatchedURSetDataType.add(googleSideBarORGWebSiteURL.getAttribute("href"));
					isUrlGotFromSiderBarOrPlacesLogic = true;
					System.out.println("**** Search Area 1.1  -> Google Sider Bar Style 1: Note: Got the URL without checking the ORG Name. Please check the URL Once*****");
				}
			}
			/** Search Area 1.1: Google Side Bar Style 1 Ends here**/
			catch (Exception e) {
				/** Search Area 1.2: Google Side Bar Style 2 Starts here**/
				try {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(GoogleSearchKeywordPage_WebElements.Const_googleSideBarStyle2RGTitle)));
					/*Link Header Various combinations of Test data - Start*/
					String linkHeader = googleSideBarStyle2RGTitle.getText().toLowerCase();
					String firstWordOfLinkHeader = linkHeader.split("\\s")[0];
					// first character of each word in the Link Header
					StringBuilder stringWordBuilderGoogleSideBar = new StringBuilder();
					String[] wordGoogleSideBarArray = linkHeader.toLowerCase().split("\\s+");
					if(wordGoogleSideBarArray.length>1) {
						for (String wordGoogleSideBar : wordGoogleSideBarArray) {
							stringWordBuilderGoogleSideBar.append(wordGoogleSideBar.charAt(0));
						}
					}else {
						stringWordBuilderGoogleSideBar.append("@@@@@@@@@@");
					}
					String firstCharOfEachWordLinkHeader = stringWordBuilderGoogleSideBar.toString();
					String linkHeadereWithOnlyMainWord = linkHeader.replace(".com", "").
							replace(".gov", "").
							replace(".edu", "").
							replace(".", "").
							replace("-", "").
							replace(" ", "").
							replace("https://www.", "").
							replace("www.", "");
					/*Link Header Various combinations of Test data - End*/
					String URLName2 = googleSideBarStyle2RGWebSiteURL.getAttribute("href");
					Set<String> ORGUrl2 = OrgWebsiteFinder.findUniqueUrls(URLName2);
					URLName2 = ORGUrl2.stream().findFirst().orElse(null);
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(GoogleSearchKeywordPage_WebElements.Const_googleSideBarStyle2ORGWebSiteURL)));
					if (Fuzzy.searchLogic(75, orgFullName, linkHeader, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(65, firstWordOfOrgName, firstCharOfEachWordLinkHeader, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(75, orgFullNameWithOnlyMainWord, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(70, firstWordOfOrgName, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, firstCharOfEachWordLinkHeader, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, firstWordOfLinkHeader, orgFullName, linkHeader)) {
						if (!URLName2.startsWith("https://www.google.com") && !URLName2.contains(".gov")  && !URLName2.contains(".edu")) {
							orgNameMatchedURSetDataType.add(googleSideBarStyle2RGWebSiteURL.getAttribute("href"));
							isUrlGotFromSiderBarOrPlacesLogic = true;
							System.out.println("**** Search Area 1.2 -> Google Sider Bar Style 2: Got the ORG Name and Added the URL *****");
						}
					} else if (googleSideBarStyle2RGWebSiteURL.isDisplayed() && !URLName2.startsWith("https://www.google.com") &&
							!URLName2.contains(".gov")  && !URLName2.contains(".edu")) {
						orgNameMatchedURSetDataType.add(googleSideBarStyle2RGWebSiteURL.getAttribute("href"));
						isUrlGotFromSiderBarOrPlacesLogic = true;
						System.out.println("**** Search Area 1.2 -> Google Sider Bar Style 2: Got the URL without checking the ORG Name. Please check the URL Once *****");
					}
				} catch (Exception ex) {
					System.out.println("Search Area 1.1 & 1.2 -> Google Sider Bar Style 1,2 > NOT Able to see the Google Side Bar Or Not fine the ORG Name in Sider bars");
				}
				/** Search Area 1.2: Google Side Bar Style 2 Starts here**/
			}
		}

		/** Search Area 3: List of Search results link header starts here **/
		if(orgNameMatchedURSetDataType.size()==0) {
			if (!isUrlGotFromSiderBarOrPlacesLogic) {
				wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
				boolean googleBackwardFlag2 = true;
				int eachWebLinkCount = 1;
				for (String eachSearchString : searchStringListOnlyOne) {
					if (googleBackwardFlag2) {
						driver.navigate().back();
					}
					googleSearchTextFiled.sendKeys(cleanedList.get(0), Keys.ENTER);
					wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
					int count1 = 1;
					while (count1 <= 15) {
						((JavascriptExecutor) driver).executeScript("window.scrollBy(0,2000000);");
						Thread.sleep(300);
						linkHeaderList = googleSearchResultsList4;
						count1++;
					}

					//Link Header string compare stage 1
					linkHeaderList = googleSearchResultsList1;
					elementURLPath = "getORGPROVIDERNameURL1";
					if (linkHeaderList.size() == 0) {
						linkHeaderList = googleSearchResultsList2;
						elementURLPath = "getORGPROVIDERNameURL2";
					}
					WebScrap_ORG_Website_Cache.findORGUsingLinkHeader(linkHeaderList, orgNameKey, orgFullName, firstWordOfOrgName,
							firstCharOfEachWordOrgName, orgFullNameWithOnlyMainWord, elementURLPath,
							Url, eachWebLinkCount, driver, orgNameMatchedURSetDataType, firstTwoWordsOfOrgName,
							firstCharOfFirstFourOrThreeWordsOrgName);
					//linkHeaderList = null;
					eachWebLinkCount = 1;
					if (orgNameMatchedURSetDataType.size() != 0) {
						break;
					}
				}
				//Link Header string compare stage 2
				if (orgNameMatchedURSetDataType.size() == 0) {
					linkHeaderList = null;
					linkHeaderList = googleSearchResultsList3;
					elementURLPath = "getORGPROVIDERNameURL3";
					WebScrap_ORG_Website_Cache.findORGUsingLinkHeader(linkHeaderList, orgNameKey, orgFullName, firstWordOfOrgName,
							firstCharOfEachWordOrgName, orgFullNameWithOnlyMainWord, elementURLPath,
							Url, eachWebLinkCount, driver, orgNameMatchedURSetDataType, firstTwoWordsOfOrgName,
							firstCharOfFirstFourOrThreeWordsOrgName);
				}


				//Link Header string compare stage 3
				if (orgNameMatchedURSetDataType.size()==0) {
					linkHeaderList = null;
					linkHeaderList = googleSearchResultsList4;
					int count = 1;
					while (count <= 8) {
						((JavascriptExecutor) driver).executeScript("window.scrollBy(0,2000000);");
						Thread.sleep(500);
						linkHeaderList = googleSearchResultsList4;
						count++;
					}
					elementURLPath = "getORGPROVIDERNameURL4";
					WebScrap_ORG_Website_Cache.findORGUsingLinkHeader(linkHeaderList, orgNameKey, orgFullName, firstWordOfOrgName,
							firstCharOfEachWordOrgName, orgFullNameWithOnlyMainWord, elementURLPath,
							Url, eachWebLinkCount, driver, orgNameMatchedURSetDataType, firstTwoWordsOfOrgName,
							firstCharOfFirstFourOrThreeWordsOrgName);
				}
			}
		}

		/** Search Area 2: Google Places Bar or Locations Bar Starts here**/
		if(orgNameMatchedURSetDataType.size()==0) {
			if (!googlePlacesORGLinkHeader.isEmpty()) {
				double highestScore = 0;
				String maxScoreURL = null;
				for (int i = 0; i < googlePlacesORGLinkHeader.size(); i++) {
					/*Link Header Various combinations of Test data - Start*/
					String linkHeader = googlePlacesORGLinkHeader.get(i).getText().toLowerCase();
					String firstWordOfLinkHeader = linkHeader.split("\\s")[0];
					// first character of each word in the Link Header
					StringBuilder stringPlaceBuilder = new StringBuilder();
					String[] wordsPlaceArray = linkHeader.toLowerCase().split("\\s+");
					if(wordsPlaceArray.length>1) {
						for (String wordArray : wordsPlaceArray) {
							stringPlaceBuilder.append(wordArray.charAt(0));
						}
					}else{
						stringPlaceBuilder.append("@@@@@@@@");
					}
					String firstCharOfEachWordLinkHeader = stringPlaceBuilder.toString();
					String linkHeadereWithOnlyMainWord = linkHeader.replace(".com", "").
							replace(".gov", "").
							replace(".edu", "").
							replace(".", "").
							replace("-", "").
							replace(" ", "").
							replace("https://www.", "").
							replace("www.", "");
					/*Link Header Various combinations of Test data - End*/
					if (Fuzzy.searchLogic(75, orgFullName, linkHeader, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(90, firstWordOfOrgName, firstCharOfEachWordLinkHeader, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(75, orgFullNameWithOnlyMainWord, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(70, firstWordOfOrgName, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, firstCharOfEachWordLinkHeader, orgFullName, linkHeader) ||
							Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, firstWordOfLinkHeader, orgFullName, linkHeader)) {
						double afterStringCompareScore = Fuzzy.maxScore;
						if (afterStringCompareScore > highestScore) {
							highestScore = afterStringCompareScore;
							maxScoreURL = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getGooglePlacesORGWebsiteURL(i + 1))).getAttribute("href");
						}
					}
				}
				if (maxScoreURL != null) {
					Set<String> finalORGUrl = OrgWebsiteFinder.findUniqueUrls(maxScoreURL);
					maxScoreURL = finalORGUrl.stream().findFirst().orElse(null);
					//System.out.println("Max Score: " + highestScore);
					if (!maxScoreURL.startsWith("https://www.google.com") && !maxScoreURL.endsWith(".gov")){
						orgNameMatchedURSetDataType.add(maxScoreURL);
						isUrlGotFromSiderBarOrPlacesLogic = true;
						System.out.println("**** Search Area 2 -> Places Or Locations: Got the ORG Name and Added the URL *****");
					}
				}
			}
		}
		/** Search Area 2: Google Places Bar or Locations Bar Ends here**/

		if(orgNameMatchedURSetDataType.size()>2){
			LinkedHashSet<String> firstTwoElementsSet = orgNameMatchedURSetDataType.stream().limit(2)
					.collect(Collectors.toCollection(LinkedHashSet::new));
			orgNameMatchedURSetDataType.clear();
			orgNameMatchedURSetDataType.addAll(firstTwoElementsSet);
		}

		List<String> finalWebSiteList = new ArrayList<>(orgNameMatchedURSetDataType);

		if (finalWebSiteList.size() != 0) {
			try {
				OrgURLFoundStatus = "PASS";
				Set<String> finalORGUrl = OrgWebsiteFinder.WebsiteFinder_OrgCache(finalWebSiteList);
				objWriteR3TestResult.writeORGURLCache(clonedR3File, executingRowIndex, finalORGUrl, OrgURLFoundStatus);
			} catch (Exception e) {
			}
		} else if (finalWebSiteList.size() == 0) {
			OrgURLFoundStatus = "FAIL";
			Set<String> finalORGUrl = new HashSet<>();
			finalORGUrl.add("No ORG URL Found");
			objWriteR3TestResult.writeORGURLCache(clonedR3File, executingRowIndex, finalORGUrl, OrgURLFoundStatus);
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

	private static String extractFirstWordTwoCharAndFirstCharOfEachWord(String input) {
		StringBuilder abbreviation = new StringBuilder();
		// Split the input string into words
		String[] words = input.split("\\s+");
		// Extract the first two characters of the first word
		if (words.length > 0) {
			abbreviation.append(words[0].substring(0, Math.min(2, words[0].length())).toLowerCase());
			// Concatenate the first character of subsequent words
			for (int i = 1; i < words.length; i++) {
				abbreviation.append(words[i].charAt(0));
			}
		}
		return abbreviation.toString();
	}
	private static String removeWords(String input, List<String> wordsToRemove) {
		for (String word : wordsToRemove) {
			input = input.replaceAll("\\b" + word + "\\b", "");
		}
		return input.trim();
	}


	public static void findORGUsingLinkHeader(List<WebElement> linkHeaderList, LinkedHashSet<String> orgNameKey, String orgFullName,
											  String firstWordOfOrgName, String firstCharOfEachWordOrgName, String orgFullNameWithOnlyMainWord,
											  String elementURLPath, String Url, int eachWebLinkCount, WebDriver driver,
											  LinkedHashSet<String> orgNameMatchedURSetDataType, String firstTwoWordsOfOrgName,
											  String firstCharOfFirstFourOrThreeWordsOrgName) {
		for (WebElement webElement : linkHeaderList) {
			/*Link Header Various combinations of Test data - Start*/
			String linkHeader = webElement.getText().toLowerCase().replace(".", "").replace("-", " ");
			String firstWordOfLinkHeader = linkHeader.split("\\s")[0].
														replace(".gov", "").
														replace(".edu", "").
														replace(".com", "").
														replace(".", " ").
														replace("-", " ").
														replace(" ", "").
														replace("https://www.", "").
														replace("www.", "");

			String[] twoWordsArray = linkHeader.split("\\s");
			String firstTwoWordsOfLinkHeader=null;
			if(twoWordsArray.length>=2) {
				firstTwoWordsOfLinkHeader = twoWordsArray[0]+" " + twoWordsArray[1];
			}else if(twoWordsArray.length<=1){
				firstTwoWordsOfLinkHeader = twoWordsArray[0];
			}

			// first character of each word in the Link Header
			StringBuilder string2BuilderLinkHeader = new StringBuilder();
			String[] wordsLinkHeaderArray = linkHeader.toLowerCase().split("\\s+");
			if(wordsLinkHeaderArray.length>1) {
				for (String word : wordsLinkHeaderArray) {
					string2BuilderLinkHeader.append(word.charAt(0));
				}
			}else{
				string2BuilderLinkHeader.append("@@@@@@@@@");
			}
			String firstCharOfEachWordLinkHeader = string2BuilderLinkHeader.toString();
			String firstFourCharactersLinkHeader = firstWordOfLinkHeader.substring(0, Math.min(firstWordOfLinkHeader.length(), 4));

			String linkHeadereWithOnlyMainWord = linkHeader.replace(".com", "").
					replace(".gov", "").
					replace(".in", "").
					replace(".org", "").
					replace(".edu", "").
					replace(".net", "").
					replace(".", "").
					replace("-", "").
					replace(" ", "").
					replace("https://www.", "").
					replace("www.", "");
			/*Link Header Various combinations of Test data - End*/
			String eachSearchLinkHeaderWithoutSpace = webElement.getText().toLowerCase().replace(" ", "").replace("-", "");
			String eachSearchLinkHeaderActualName = webElement.getText().toLowerCase().replace("-", "");

			String FirstWordTwoCharAndFirstCharOfEachWord = extractFirstWordTwoCharAndFirstCharOfEachWord(linkHeader);

			for (String eachOrgNameKey : orgNameKey) {
				if (Fuzzy.searchLogic(75, orgFullName, linkHeader, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, firstWordOfLinkHeader, orgFullName, linkHeader)||
						Fuzzy.searchLogic(70, firstTwoWordsOfOrgName, firstTwoWordsOfLinkHeader, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(75, firstWordOfOrgName, firstCharOfEachWordLinkHeader, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(65, firstWordOfOrgName, FirstWordTwoCharAndFirstCharOfEachWord, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, firstCharOfEachWordLinkHeader, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(75, orgFullNameWithOnlyMainWord, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstCharOfEachWordOrgName, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstWordOfOrgName, linkHeadereWithOnlyMainWord, orgFullName, linkHeader) ||
						Fuzzy.searchLogic(70, firstCharOfFirstFourOrThreeWordsOrgName, firstFourCharactersLinkHeader, orgFullName, linkHeader)
								&& eachSearchLinkHeaderWithoutSpace.length() != 0 && !eachSearchLinkHeaderWithoutSpace.startsWith("facebook")
								&& !eachSearchLinkHeaderWithoutSpace.startsWith("linkedin") && !eachSearchLinkHeaderWithoutSpace.startsWith("Healthgrades")) {
					if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL1")) {
						Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL1(eachWebLinkCount))).getAttribute("href");
					} else if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL2")) {
						Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL2(eachWebLinkCount))).getAttribute("href");
					} else if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL3")) {
						Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL3(eachWebLinkCount))).getAttribute("href");
					} else if (elementURLPath.equalsIgnoreCase("getORGPROVIDERNameURL4")) {
						Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL4(eachWebLinkCount))).getAttribute("href");
					}

					try {
						Set<String> ORGUrl3 = OrgWebsiteFinder.findUniqueUrls(Url);
						Url = ORGUrl3.stream().findFirst().orElse(null);
						if (!Url.toLowerCase().startsWith("https://www.facebook.")
								&& !Url.toLowerCase().startsWith("https://www.linkedin.")
								&& !Url.toLowerCase().startsWith("https://www.healthgrades")
								&& !Url.toLowerCase().startsWith("https://www.healthalliance.org")
								&& !Url.toLowerCase().startsWith("https://www.google.com")
								&& !Url.toLowerCase().startsWith("https://www.youtube.com")
								&& !Url.toLowerCase().endsWith(".gov")
								&& !Url.toLowerCase().endsWith(".edu")) {
							orgNameMatchedURSetDataType.add(Url);
							System.out.println("**** Search Area 3 -> Search Link Header: Got the ORG Name and Added the URL *****");
						}
					} catch (NullPointerException e) {
						System.out.println("");
					}
				}
			}
			eachWebLinkCount++;
		}

	}
}




















