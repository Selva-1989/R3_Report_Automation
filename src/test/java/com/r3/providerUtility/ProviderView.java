package com.r3.providerUtility;

import com.aventstack.extentreports.Status;
import com.r3.pageobjects.WebScrap_ORG_PV_Phone;
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
public class ProviderView extends TestBaseClass {
    WebDriver driver;
    List<Map<LinkedHashSet<String>, Map<String,String>>> OrgProvList;
    R3ExcelReader objR3ExcelReader;
    public ProviderView(WebDriver TestBaseClassDriver) {
        driver = TestBaseClassDriver;
        PageFactory.initElements(TestBaseClassDriver, this);
        objR3ExcelReader = new R3ExcelReader();
    }
    String phoneValidationPriority;
    String ProviderAndOrgPhoneValidation;
    String PvPhoneValidation;
    String clonedR3File=null;
    int r3RowCount=0;
    public void Verify_All_Buckets_ORG_PV_PHONE(int executedR3ExcelRowCount) throws IOException {
        OrgProvList = objR3ExcelReader.excelDataToList(executedR3ExcelRowCount);
        /*creating the copy of the Original R3_Report excel with Output columns and provide the cloned Excel file path*/
        clonedR3File = CreateExcelFile.cloneR3Report("Verify_All_Buckets_ORG_PV_PHONE");
        for(Map<LinkedHashSet<String>,Map<String,String>> eachOrgList:OrgProvList){
            r3RowCount++;
            for(Map.Entry<LinkedHashSet<String>,Map<String,String>> eachOrgMapEntry:eachOrgList.entrySet()){
                LinkedHashSet<String> orgNameKey = eachOrgMapEntry.getKey();
                orgNameKey.removeIf(String::isEmpty);
               /* String orgNameKeyWithORKeyword = "";
                if(orgNameKey.size()==1){
                    orgNameKeyWithORKeyword = orgNameKeyWithORKeyword+ String.join(", ", orgNameKey);
                } else if (orgNameKey.size()>1) {
                    orgNameKeyWithORKeyword = "("+orgNameKeyWithORKeyword + String.join(" OR ", orgNameKey)+")";
                }*/
                Map<String,String> provDetail = eachOrgMapEntry.getValue();
                String firstName = provDetail.get("First Name");
                String lastName = provDetail.get("Last_Name");
                String middleName = null;
                String credentials = provDetail.get("Credentials");
                String providerName;
                try {
                     middleName = provDetail.get("Middle_Name");
                     if(middleName.equalsIgnoreCase("null")) {
                         providerName = firstName + " " + lastName;
                     }else {
                         providerName = firstName + " " + middleName + " " + lastName;
                     }
                } catch (NullPointerException e) {
                    providerName = firstName + " " + lastName;
                }
                String address = provDetail.get("Address").toLowerCase();
                address = address.replace(" n ", " north ")
                                 .replace(" s ", " south ")
                                 .replace(" e ", " east ")
                                 .replace(" w ", " west ")
                                 .replace(" st ", " street ")
                                 .replace(" rd ", " road ");
                String state = provDetail.get("State");
                /*String city = provDetail.get("City");
                String zip = "0"+provDetail.get("Zip").replace(".0","");
                String completeAddress = "("+address+" "+city+" "+state+" "+zip+")".toLowerCase();*/

                String phoneBasicFormat = provDetail.get("Phone");
                String areaCode = phoneBasicFormat.substring(0,3);
                String exchangeCode = phoneBasicFormat.substring(3,6);
                String lineNumber = phoneBasicFormat.substring(6);
                /***taking all found and not found URL columns values starts here*/
                //PV_Phone_Found_Websites
                Set<String> PV_Phone_Found_Websites = null;
                try {
                    String websites = provDetail.get("PV_Phone_Found_Websites");
                    if (websites != null && !websites.isEmpty()) {
                        PV_Phone_Found_Websites = new HashSet<>
                                (Arrays.asList(websites.split("\\|")));
                    } else {
                        PV_Phone_Found_Websites = new HashSet<>();
                        PV_Phone_Found_Websites.add("No websites");
                    }
                } catch (NullPointerException e) {
                    PV_Phone_Found_Websites.add("No websites");
                }

                //PV_Phone_Found_Organization_Websites
                Set<String> PV_Phone_Found_Organization_Websites = null;
                try {
                    String websites = provDetail.get("PV_Phone_Found_Organization_Websites");
                    if (websites != null && !websites.isEmpty()) {
                        PV_Phone_Found_Organization_Websites = new HashSet<>
                                (Arrays.asList(websites.split("\\|")));
                    } else {
                        PV_Phone_Found_Organization_Websites = new HashSet<>();
                        PV_Phone_Found_Organization_Websites.add("No websites");
                    }
                } catch (NullPointerException e) {
                    PV_Phone_Found_Organization_Websites.add("No websites");
                }

                //PV_Phone_Not_Found_Websites
                Set<String> PV_Phone_Not_Found_Websites = null;
                try {
                    String websites = provDetail.get("PV_Phone_Not_Found_Websites");
                    if (websites != null && !websites.isEmpty()) {
                        PV_Phone_Not_Found_Websites = new HashSet<>
                                (Arrays.asList(websites.split("\\|")));
                    } else {
                        PV_Phone_Not_Found_Websites = new HashSet<>();
                        PV_Phone_Not_Found_Websites.add("No websites");
                    }
                } catch (NullPointerException e) {
                    PV_Phone_Not_Found_Websites.add("No websites");
                }

                //PV_Phone_Not_Found_Organization_Websites
                Set<String> PV_Phone_Not_Found_Organization_Websites = null;
                try {
                    String websites = provDetail.get("PV_Phone_Not_Found_Organization_Websites");
                    if (websites != null && !websites.isEmpty()) {
                        PV_Phone_Not_Found_Organization_Websites = new HashSet<>
                                (Arrays.asList(websites.split("\\|")));
                    } else {
                        PV_Phone_Not_Found_Organization_Websites = new HashSet<>();
                        PV_Phone_Not_Found_Organization_Websites.add("No websites");
                    }

                } catch (NullPointerException e) {
                    PV_Phone_Not_Found_Organization_Websites.add("No websites");
                }
                /***taking all found and not found URL columns values ends here*/

                //Provider Phone Accurate bucket validation starts here
                if(provDetail.get("Provider_Phone_Validation").contains("Accurate")) {
                    phoneValidationPriority = provDetail.get("Phone_Validation_Priority");
                    PvPhoneValidation = provDetail.get("Provider_Phone_Validation");

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

                    WebScrap_ORG_PV_Phone objWebScrapping = new WebScrap_ORG_PV_Phone(driver);
                    objWebScrapping.check_ORG_PV_Phone_Accurate(clonedR3File,r3RowCount,phoneValidationPriority, PvPhoneValidation,
                                                            providerName,credentials,state,orgNameKey,areaCode,exchangeCode,lineNumber,
                                                            PV_Phone_Found_Websites,PV_Phone_Found_Organization_Websites,
                                                            PV_Phone_Not_Found_Websites,address,firstName,middleName,lastName);
                    ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
                    break;
                    }
                //PV Phone Accurate bucket validation ends here
                //PV Phone Inaccurate bucket validation starts here
                else  if(provDetail.get("Organization_Phone_Validation").equalsIgnoreCase("Inaccurate - Remove Record")) {
                    phoneValidationPriority = provDetail.get("Phone_Validation_Priority");
                    PvPhoneValidation = provDetail.get("Organization_Phone_Validation");

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

                    WebScrap_ORG_PV_Phone objWebScrapping = new WebScrap_ORG_PV_Phone(driver);
                   /* objWebScrapping.check_ORG_PV_Phone_Inaccurate(clonedR3File,r3RowCount,phoneValidationPriority,PvPhoneValidation,
                                                            providerName,credentials,state,orgNameKey,areaCode,exchangeCode,lineNumber,
                                                            PV_Phone_Found_Websites,PV_Phone_Not_Found_Organization_Websites,
                                                            PV_Phone_Not_Found_Websites, address);*/
                    ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
                    break;
                    }
                }
                 //PV Phone Inaccurate bucket validation ends here
            }
        driver.quit();
        }

    }













