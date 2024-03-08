package com.r3.providerUtility;

import com.aventstack.extentreports.Status;
import com.r3.pageobjects.WebScrap_ORG_PV_Phone;
import com.r3.pageobjects.WebScrap_ORG_Website_Cache;
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
public class ORG_WEBSITE_CACHE extends TestBaseClass {
    WebDriver driver;
    List<Map<LinkedHashSet<String>, Map<String,String>>> OrgProvList;
    R3ExcelReader objR3ExcelReader;
    public ORG_WEBSITE_CACHE(WebDriver TestBaseClassDriver) {
        driver = TestBaseClassDriver;
        PageFactory.initElements(TestBaseClassDriver, this);
        objR3ExcelReader = new R3ExcelReader();
    }
    String phoneValidationPriority;
    String ProviderAndOrgPhoneValidation;
    String PvPhoneValidation;
    String clonedR3File=null;
    int r3RowCount=0;
    public void Verify_ORG_WEBSITE_CACHE(int executedR3ExcelRowCount) throws IOException, InterruptedException {
        OrgProvList = objR3ExcelReader.excelDataToList_ORGWebsiteCache(executedR3ExcelRowCount);
        /*creating the copy of the Original R3_Report excel with Output columns and provide the cloned Excel file path*/
        clonedR3File = CreateExcelFile.cloneR3Report("Verify_ORG_WEBSITE_CACHE");
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
                String State = provDetail.get("State");
                String Sites = provDetail.get("Sites");
                /***taking all found and not found URL columns values ends here*/

                //Provider Phone Accurate bucket validation starts here
                if(!orgNameKey.isEmpty()) {
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

                    WebScrap_ORG_Website_Cache objWebScrapping = new WebScrap_ORG_Website_Cache(driver);
                    objWebScrapping.check_ORG_Website_Cache_Accuracy(clonedR3File,r3RowCount, orgNameKey, State, Sites);
                    ExtentManager.getExtentTest().log(Status.INFO,("======================================================================================================="));
                    break;
                    }
                }
                 //PV Phone Inaccurate bucket validation ends here
            }
        driver.quit();
        }

    }













