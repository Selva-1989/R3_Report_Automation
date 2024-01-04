package com.r3.providerUtility;

import com.aventstack.extentreports.Status;
import com.r3.pageobjects.WebScrap_ORGPhone;
import com.r3.utility.CreateExcelFile;
import com.r3.utility.ExtentManager;
import com.r3.utility.R3ExcelReader;
import com.r3.utility.TestBaseClass;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.*;

@Data
public class ORGView extends TestBaseClass {
    WebDriver driver;
    List<Map<LinkedHashSet<String>, Map<String,String>>> OrgProvList;
    R3ExcelReader objR3ExcelReader;
    public ORGView(WebDriver TestBaseClassDriver) throws IOException {
        driver = TestBaseClassDriver;
        PageFactory.initElements(TestBaseClassDriver, this);
        objR3ExcelReader = new R3ExcelReader();
    }
    String phoneValidationPriority;
    String ProviderAndOrgPhoneValidation;
    String OrgPhoneValidation;
    String providerPhoneValidation;
    String clonedR3File=null;
    int r3RowCount=0;
    public void Verify_All_Buckets_ORG_PHONE(int executedR3ExcelRowCount) throws IOException {
        OrgProvList = objR3ExcelReader.excelDataToList(executedR3ExcelRowCount);
        /*creating the copy of the Original R3_Report excel with Output columns and provide the cloned Excel file path*/
        clonedR3File = CreateExcelFile.cloneR3Report("Verify_All_Buckets_ORG_PHONE");
        for(Map<LinkedHashSet<String>,Map<String,String>> eachOrgList:OrgProvList){
            r3RowCount++;
            for(Map.Entry<LinkedHashSet<String>,Map<String,String>> eachOrgMapEntry:eachOrgList.entrySet()){
                LinkedHashSet<String> orgNameKey = eachOrgMapEntry.getKey();
                String orgNameKeyWithORKeyword = String.join(" OR ", orgNameKey);
                Map<String,String> provDetail = eachOrgMapEntry.getValue();
               /* String firstName = provDetail.get("First Name");
                String lastName = provDetail.get("Last_Name");
                String actualMiddleName = provDetail.get("Middle_Name");
                String middleName; // this is for providing the search key word string
                String providerNameWithMiddleName;
                try {
                     middleName = provDetail.get("Middle_Name");
                     if(middleName.equalsIgnoreCase("null")) {
                         providerNameWithMiddleName = firstName + " " + lastName;
                     }else {
                         providerNameWithMiddleName = firstName + " " + middleName + " " + lastName;
                     }
                } catch (NullPointerException e) {
                    middleName="";
                    providerNameWithMiddleName = firstName +" "+ lastName;
                }*/
                String address = provDetail.get("Address").toLowerCase();
                address = address.replace(" n ", " north ")
                                 .replace(" s ", " south ")
                                 .replace(" e ", " east ")
                                 .replace(" w ", " west ")
                                 .replace(" st ", " street ")
                                 .replace(" rd ", " road ");
                String city = provDetail.get("City");
                String zip = "0"+provDetail.get("Zip").replace(".0","");
                String state = provDetail.get("State");
                String completeAddress = "("+address+" "+city+" "+state+" "+zip+")".toLowerCase();

                String phoneBasicFormat = provDetail.get("Phone");
                String areaCode = phoneBasicFormat.substring(0,3);
                String exchangeCode = phoneBasicFormat.substring(3,6);
                String lineNumber = phoneBasicFormat.substring(6);
                String phoneSearchKeyWord = "("+areaCode+" "+exchangeCode+" "+lineNumber+")";
                /*Set<String> PV_Phone_Found_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("PV_Phone_Found_Websites").split("\\|")));
                Set<String> PV_Phone_Found_Organization_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("PV_Phone_Found_Organization_Websites").split("\\|")));*/
                /***taking all found and not found URL columns values starts here*/
                //OV_Phone_Found_Websites
                Set<String> OV_Phone_Found_Websites = null;
                try {
                    String websites = provDetail.get("OV_Phone_Found_Websites");
                    if (websites != null && !websites.isEmpty()) {
                        OV_Phone_Found_Websites = new HashSet<>
                                (Arrays.asList(websites.split("\\|")));
                    } else {
                        OV_Phone_Found_Websites = new HashSet<>();
                        OV_Phone_Found_Websites.add("No websites");
                    }
                } catch (NullPointerException e) {
                    OV_Phone_Found_Websites.add("No websites");
                }

                //OV_Phone_Found_Organization_Websites
                Set<String> OV_Phone_Found_Organization_Websites = null;
                try {
                    String websites = provDetail.get("OV_Phone_Found_Organization_Websites");
                    if (websites != null && !websites.isEmpty()) {
                        OV_Phone_Found_Organization_Websites = new HashSet<>
                                (Arrays.asList(websites.split("\\|")));
                    } else {
                        OV_Phone_Found_Organization_Websites = new HashSet<>();
                        OV_Phone_Found_Organization_Websites.add("No websites");
                    }
                } catch (NullPointerException e) {
                    OV_Phone_Found_Organization_Websites.add("No websites");
                }

                /*Set<String> PV_Phone_Not_Found_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("PV_Phone_Not_Found_Websites").split("\\|")));*/
                //OV_Phone_Not_Found_Websites
                Set<String> OV_Phone_Not_Found_Websites = null;
                try {
                    String websites = provDetail.get("OV_Phone_Not_Found_Websites");
                    if (websites != null && !websites.isEmpty()) {
                        OV_Phone_Not_Found_Websites = new HashSet<>
                                (Arrays.asList(websites.split("\\|")));
                    } else {
                        OV_Phone_Not_Found_Websites = new HashSet<>();
                        OV_Phone_Not_Found_Websites.add("No websites");
                    }
                } catch (NullPointerException e) {
                    OV_Phone_Not_Found_Websites.add("No websites");
                }

                //OV_Phone_Not_Found_Organization_Websites
                Set<String> OV_Phone_Not_Found_Organization_Websites = null;
                try {
                    String websites = provDetail.get("OV_Phone_Not_Found_Organization_Websites");
                    if (websites != null && !websites.isEmpty()) {
                        OV_Phone_Not_Found_Organization_Websites = new HashSet<>
                                (Arrays.asList(websites.split("\\|")));
                    } else {
                        OV_Phone_Not_Found_Organization_Websites = new HashSet<>();
                        OV_Phone_Not_Found_Organization_Websites.add("No websites");
                    }

                } catch (NullPointerException e) {
                    OV_Phone_Not_Found_Organization_Websites.add("No websites");
                }
                /***taking all found and not found URL columns values ends here*/

                //ORG Phone Accurate bucket validation starts here
                if(provDetail.get("Organization_Phone_Validation").contains("Accurate")) {
                    phoneValidationPriority = provDetail.get("Phone_Validation_Priority");
                    OrgPhoneValidation = provDetail.get("Organization_Phone_Validation");

                    StringBuilder tableHtml = new StringBuilder();
                    tableHtml.append("<table border=\"1\">");
                    for (Map.Entry<LinkedHashSet<String>, Map<String, String>> entry : eachOrgList.entrySet()) {
                        tableHtml.append("<tr>");
                        // Display the organization name as a single cell with colspan
                        tableHtml.append("<td colspan=\"2\">").append("ORG NAME->> "+ entry.getKey()).append("</td>");
                        tableHtml.append("</tr>");
                        Map<String, String> orgData = entry.getValue();
                        for (Map.Entry<String, String> dataEntry : orgData.entrySet()) {
                            tableHtml.append("<tr><td>").append(dataEntry.getKey()).append("</td><td>").append(dataEntry.getValue()).append("</td></tr>");
                        }
                    }
                    tableHtml.append("</table>");
                    ExtentManager.getExtentTest().log(Status.INFO, tableHtml.toString());

                    /*Creating the different combinations of search keyword*/
                    String searchKeyword_OrgPhone = "("+orgNameKeyWithORKeyword+")" + " AND " + phoneSearchKeyWord + " OR " +
                                                                phoneSearchKeyWord + " AND " + completeAddress + " OR " +
                                                                "("+orgNameKeyWithORKeyword+")" + " AND " + completeAddress;
                    WebScrap_ORGPhone objWebScrapping = new WebScrap_ORGPhone(driver);
                    objWebScrapping.check_ORG_Phone_Accurate(clonedR3File,r3RowCount,phoneValidationPriority, OrgPhoneValidation,
                                                            searchKeyword_OrgPhone,orgNameKey,areaCode,exchangeCode,lineNumber,
                                                            OV_Phone_Found_Websites,OV_Phone_Found_Organization_Websites,
                                                            OV_Phone_Not_Found_Websites);
                    ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
                    break;
                    }
                //ORG Phone Accurate bucket validation ends here
                //ORG Phone Inaccurate bucket validation starts here
                else  if(provDetail.get("Organization_Phone_Validation").equalsIgnoreCase("Inaccurate - Remove Record")) {
                    phoneValidationPriority = provDetail.get("Phone_Validation_Priority");
                    OrgPhoneValidation = provDetail.get("Organization_Phone_Validation");

                    StringBuilder tableHtml = new StringBuilder();
                    tableHtml.append("<table border=\"1\">");
                    for (Map.Entry<LinkedHashSet<String>, Map<String, String>> entry : eachOrgList.entrySet()) {
                        tableHtml.append("<tr>");
                        // Display the organization name as a single cell with colspan
                        tableHtml.append("<td colspan=\"2\">").append("ORG NAME->> "+ entry.getKey()).append("</td>");
                        tableHtml.append("</tr>");
                        Map<String, String> orgData = entry.getValue();
                        for (Map.Entry<String, String> dataEntry : orgData.entrySet()) {
                            tableHtml.append("<tr><td>").append(dataEntry.getKey()).append("</td><td>").append(dataEntry.getValue()).append("</td></tr>");
                        }
                    }
                    tableHtml.append("</table>");
                    ExtentManager.getExtentTest().log(Status.INFO, tableHtml.toString());

                    /*Creating the different combinations of search keyword*/
                    String searchKeyword_OrgPhone = "("+orgNameKeyWithORKeyword+")" + " AND " + phoneSearchKeyWord + " OR " +
                            phoneSearchKeyWord + " AND " + completeAddress + " OR " +
                            "("+orgNameKeyWithORKeyword+")" + " AND " + completeAddress;

                    WebScrap_ORGPhone objWebScrapping = new WebScrap_ORGPhone(driver);
                    objWebScrapping.check_ORG_Phone_Inaccurate(clonedR3File,r3RowCount,phoneValidationPriority, OrgPhoneValidation,
                                                            searchKeyword_OrgPhone,orgNameKey,areaCode,exchangeCode,lineNumber,
                                                            OV_Phone_Found_Websites,OV_Phone_Not_Found_Organization_Websites,
                                                            OV_Phone_Not_Found_Websites);
                    ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
                    break;
                    }
                }
                 //ORG Phone Inaccurate bucket validation ends here
            }
        driver.quit();
        }

    }













