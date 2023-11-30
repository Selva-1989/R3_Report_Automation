package com.r3.providerUtility;

import com.aventstack.extentreports.Status;
import com.r3.pageobjects.GoogleSearchKeywordPageObjects;
import com.r3.utility.CreateExcelFile;
import com.r3.utility.ExtentManager;
import com.r3.utility.R3ExcelReader;
import com.r3.utility.TestBaseClass;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class R3Phone extends TestBaseClass {
    WebDriver driver;
    List<Map<LinkedHashSet<String>, Map<String,String>>> OrgProvList;
    R3ExcelReader objR3ExcelReader;
    public R3Phone(WebDriver TestBaseClassDriver) throws IOException {
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
    public void Verify_Accurate_Bucket_ORG_PROV_Phone(int executedR3ExcelRowCount, String priority) throws IOException {
        OrgProvList = objR3ExcelReader.excelDataToList(executedR3ExcelRowCount);
        /*creating the copy of the Original R3_Report excel and provide the cloned Excel file path*/
        clonedR3File = CreateExcelFile.cloneR3Report();
        for(Map<LinkedHashSet<String>,Map<String,String>> eachOrgList:OrgProvList){
            r3RowCount++;
            for(Map.Entry<LinkedHashSet<String>,Map<String,String>> eachOrgMapEntry:eachOrgList.entrySet()){
                LinkedHashSet<String> orgNameKey = eachOrgMapEntry.getKey();
                String orgNameKeyWithORKeyword = String.join(" OR ", orgNameKey);
                Map<String,String> provDetail = eachOrgMapEntry.getValue();
                String firstName = provDetail.get("First Name");
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
                }

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

                Set<String> PV_Phone_Found_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("PV_Phone_Found_Websites").split("\\|")));
                Set<String> PV_Phone_Found_Organization_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("PV_Phone_Found_Organization_Websites").split("\\|")));
                Set<String> OV_Phone_Found_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("OV_Phone_Found_Websites").split("\\|")));
                Set<String> OV_Phone_Found_Organization_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("OV_Phone_Found_Organization_Websites").split("\\|")));

                Set<String> PV_Phone_Not_Found_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("PV_Phone_Not_Found_Websites").split("\\|")));
                Set<String> OV_Phone_Not_Found_Websites = new HashSet<>
                        (Arrays.asList(provDetail.get("OV_Phone_Not_Found_Websites").split("\\|")));


                if(provDetail.get("Provider_and_Org_Phone_Validation").contains("Accurate") &&
                        provDetail.get("Organization_Phone_Validation").contains("Accurate") &&
                        provDetail.get("Provider_Phone_Validation").contains("Accurate")) {
                    if(Integer.parseInt(provDetail.get("Phone_Validation_Priority"))>=1 && Integer.parseInt(provDetail.get("Phone_Validation_Priority"))<=3 && priority.equalsIgnoreCase("1 to 3")){
                        phoneValidationPriority = provDetail.get("Phone_Validation_Priority");
                        OrgPhoneValidation = provDetail.get("Organization_Phone_Validation");
                        providerPhoneValidation = provDetail.get("Provider_Phone_Validation");
                        ProviderAndOrgPhoneValidation = provDetail.get("Provider_and_Org_Phone_Validation");
                        ExtentManager.getExtentTest().log(Status.INFO,""+eachOrgList);
                        /*Creating the different combinations of search keyword*/
                        String combinedSearchKeyword_OrgProvPhone = phoneSearchKeyWord + " AND " + completeAddress + " OR " +
                                                                    "("+orgNameKeyWithORKeyword+")" + " AND " + phoneSearchKeyWord + " OR " +
                                                                    "("+providerNameWithMiddleName+")" + " AND " + "("+orgNameKeyWithORKeyword+")" + " AND " + phoneSearchKeyWord + " OR " +
                                                                    "("+providerNameWithMiddleName+")" + " AND " + phoneSearchKeyWord + " OR " +
                                                                    "("+providerNameWithMiddleName+")" + " AND " + "("+orgNameKeyWithORKeyword+")";

                        GoogleSearchKeywordPageObjects objGoogleSearchKeywordPageObjects = new GoogleSearchKeywordPageObjects(driver);
                        objGoogleSearchKeywordPageObjects.validateORGPROVInfo_WithPriority1to3(clonedR3File,r3RowCount,phoneValidationPriority,
                                                                                                ProviderAndOrgPhoneValidation,OrgPhoneValidation,providerPhoneValidation,
                                                                                                combinedSearchKeyword_OrgProvPhone,orgNameKey,areaCode,exchangeCode,lineNumber,
                                                                                                firstName,lastName,actualMiddleName,completeAddress,
                                                                                                PV_Phone_Found_Websites,PV_Phone_Found_Organization_Websites,OV_Phone_Found_Websites,OV_Phone_Found_Organization_Websites,
                                                                                                PV_Phone_Not_Found_Websites,OV_Phone_Not_Found_Websites);

                        break;
                    }
                    else if(Integer.parseInt(provDetail.get("Phone_Validation_Priority"))>=4 && Integer.parseInt(provDetail.get("Phone_Validation_Priority"))<=6){
                        // ExtentManager.getExtentTest().log(Status.INFO,(""+eachOrgList));

                    }else if(Integer.parseInt(provDetail.get("Phone_Validation_Priority"))>=7 && Integer.parseInt(provDetail.get("Phone_Validation_Priority"))<=9){
                        // ExtentManager.getExtentTest().log(Status.INFO,(""+eachOrgList));

                    }
                }
            }
        }
        driver.quit();
    }
}












