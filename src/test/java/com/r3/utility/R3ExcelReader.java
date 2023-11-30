package com.r3.utility;

import com.aventstack.extentreports.Status;
import com.r3.datareader.PropertiesFileReader;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Data
public class R3ExcelReader  {
    public XSSFSheet sheet;
    String FirstNameSearchKeyword;
    String MiddleNameSearchKeyword;
    String LastNameSearchKeyword;
    String CredentialsSearchKeyword;
    String AddressSearchKeyword;
    String CitySearchKeyword;
    String ZipSearchKeyword;
    String StateSearchKeyword;
    String PhoneSearchKeyword;
    LinkedHashSet<String> ProvOrgNameSearchKeywordList;
    String ProvOrgNameSearchKeyword;
    String PhoneValidationPriority;
    String ProviderAndOrgPhoneValidationResult;
    String OrgPhoneValidationResult;
    String ProvPhoneValidationResult;
    String PVPhoneFoundWebsites;
    String PVPhoneNotFoundWebsites;
    String PVPhoneFoundOrganizationWebsites;
    String OVPhoneFoundWebsites;
    String OVPhoneNotFoundWebsites;
    String OVPhoneFoundOrganizationWebsites;
    String OrgNameSearchKeyword;
    Map<String, String> ProvMap;
    Map<LinkedHashSet<String>, Map<String, String>> OrgMap;
    List<Map<LinkedHashSet<String>, Map<String,String>>> OrgProvList;
    int r3ExecuteRowCount;

    /*Read the Excel before execution*/
    public List<Map<LinkedHashSet<String>, Map<String,String>>> excelDataToList(int executedR3ExcelRowsCount) throws IOException {
        r3ExecuteRowCount = executedR3ExcelRowsCount;
        ProvOrgNameSearchKeywordList = new LinkedHashSet<>();
        ProvMap  = new LinkedHashMap<>();
        OrgMap  = new LinkedHashMap<>();
        OrgProvList = new ArrayList<>();

        try {
            try {
                FileInputStream fis = new FileInputStream(PropertiesFileReader.getProperty("R3TestReportExcelPath"));
                XSSFWorkbook workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheet("R3_Phone");
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i <executedR3ExcelRowsCount; i++) {
                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++){
                    //Fetch the First Name from R3 excel
                    try {
                        String FirstNameColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(FirstNameColHead.equalsIgnoreCase("First Name")){
                            FirstNameSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("First Name",FirstNameSearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        FirstNameSearchKeyword = "null";
                        ProvMap.put("First Name",FirstNameSearchKeyword);
                        continue;
                    }

                    //Fetch the Middle Name from R3 excel
                    try {
                        String MiddleNameColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(MiddleNameColHead.equalsIgnoreCase("Middle_Name")){
                            MiddleNameSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("Middle_Name",MiddleNameSearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        MiddleNameSearchKeyword = "null";
                        ProvMap.put("Middle_Name",MiddleNameSearchKeyword);
                        continue;
                    }

                    //Fetch the Last Name from R3 excel
                    try {
                        String LastNameColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(LastNameColHead.equalsIgnoreCase("Last_Name")){
                            LastNameSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("Last_Name",LastNameSearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        LastNameSearchKeyword = "null";
                        ProvMap.put("Last_Name",LastNameSearchKeyword);
                        continue;
                    }

                    //Fetch the Credential from R3 excel
                    try {
                        String CredentialsColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(CredentialsColHead.equalsIgnoreCase("Credentials")){
                            CredentialsSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("Credentials",CredentialsSearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        CredentialsSearchKeyword = "null";
                        ProvMap.put("Credentials",CredentialsSearchKeyword);
                        continue;
                    }

                    //Fetch the Address from R3 excel
                    try {
                        String AddressColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(AddressColHead.equalsIgnoreCase("Address")){
                            AddressSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("Address",AddressSearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        AddressSearchKeyword = "null";
                        ProvMap.put("Address",AddressSearchKeyword);
                        continue;
                    }
                    //Fetch the City from R3 excel
                    try {
                        String CityColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(CityColHead.equalsIgnoreCase("City")){
                            CitySearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("City",CitySearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        CitySearchKeyword = "null";
                        ProvMap.put("City",CitySearchKeyword);
                        continue;
                    }
                    //Fetch the Zip from R3 excel
                    try {
                        String ZipColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(ZipColHead.equalsIgnoreCase("Zip")){
                            ZipSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("Zip",ZipSearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        ZipSearchKeyword = "null";
                        ProvMap.put("Zip",ZipSearchKeyword);
                        continue;
                    }

                    //Fetch the State from R3 excel
                    try {
                        String StateColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(StateColHead.equalsIgnoreCase("State")){
                            StateSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("State",StateSearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        StateSearchKeyword = "null";
                        ProvMap.put("Address",StateSearchKeyword);
                        continue;
                    }

                    //Fetch the Phone from R3 excel
                    try {
                        String PhoneColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(PhoneColHead.equalsIgnoreCase("Phone")){
                            PhoneSearchKeyword = sheet.getRow(i + 1).getCell(j).getRawValue().trim();
                            ProvMap.put("Phone",PhoneSearchKeyword);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        PhoneSearchKeyword = "null";
                        ProvMap.put("Phone",PhoneSearchKeyword);
                        continue;
                    }

                    /*Extracting the Proper Org name task starts here*/
                    //Fetch the Organization_Name from R3 excel
                    try {
                        String OrgNameColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(OrgNameColHead.equalsIgnoreCase("Organization_Name")){
                            OrgNameSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                            String[] orgNameArray1 = OrgNameSearchKeyword.split("\\|\\|?");
                            for(String eachOrgName: orgNameArray1){
                                try {
                                    if(eachOrgName!=null){
                                        ProvOrgNameSearchKeywordList.add(eachOrgName.trim());
                                    }
                                } catch (NullPointerException e) {
                                    ExtentManager.getExtentTest().log(Status.INFO,("Organization_Name column values are taken from the R3 Report"));
                                }
                            }
                        }
                        //Fetch the Provider_Organization_Name from R3 excel
                        String ProvOrgNameColHead = sheet.getRow(0).getCell(j+1).toString().trim();
                        if(ProvOrgNameColHead.equalsIgnoreCase("Provider_Organization_Name")) {
                            try {
                                ProvOrgNameSearchKeyword = sheet.getRow(i + 1).getCell(j+1).toString().trim();
                                String[] orgNameArray2 = ProvOrgNameSearchKeyword.split("\\|\\|?");
                                for (String eachOrgName : orgNameArray2) {
                                    if (eachOrgName != null) {
                                        ProvOrgNameSearchKeywordList.add(eachOrgName.trim());
                                    }
                                }
                            } catch (NullPointerException e) {
                                ExtentManager.getExtentTest().log(Status.INFO, ("Provider_Organization_Name column values are taken from the R3 Report"));
                                continue;
                            }
                            continue;
                        }
                    } catch (NullPointerException ignored2) {
                        ExtentManager.getExtentTest().log(Status.INFO,("Organization_Name column values are taken from the R3 Report"));
                        continue;
                    }



                    /*Extracting the Proper Org name task ends here*/

                    //Fetch the Phone_Validation_Priority from R3 excel
                    try {
                        String PhoneValidationPriorityColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(PhoneValidationPriorityColHead.equalsIgnoreCase("Phone_Validation_Priority")){
                            PhoneValidationPriority = sheet.getRow(i + 1).getCell(j).getRawValue().trim();
                            ProvMap.put("Phone_Validation_Priority",PhoneValidationPriority);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        PhoneValidationPriority = "null";
                        ProvMap.put("Phone_Validation_Priority",PhoneValidationPriority);
                        continue;
                    }

                    //Fetch the Provider_and_Org_Phone_Validation from R3 excel
                    try {
                        String ProviderAndOrgPhoneValidationColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(ProviderAndOrgPhoneValidationColHead.equalsIgnoreCase("Provider_and_Org_Phone_Validation")){
                            ProviderAndOrgPhoneValidationResult= sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("Provider_and_Org_Phone_Validation",ProviderAndOrgPhoneValidationResult);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        ProviderAndOrgPhoneValidationResult = "null";
                        ProvMap.put("Provider_and_Org_Phone_Validation",ProviderAndOrgPhoneValidationResult);
                        continue;
                    }

                    //Fetch the Organization_Phone_Validation from R3 excel
                    try {
                        String OrgPhoneValidationColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(OrgPhoneValidationColHead.equalsIgnoreCase("Organization_Phone_Validation")){
                            OrgPhoneValidationResult= sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("Organization_Phone_Validation",OrgPhoneValidationResult);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        OrgPhoneValidationResult = "null";
                        ProvMap.put("Organization_Phone_Validation",OrgPhoneValidationResult);
                        continue;
                    }

                    //Fetch the Provider_Phone_Validation from R3 excel
                    try {
                        String ProvPhoneValidationColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(ProvPhoneValidationColHead.equalsIgnoreCase("Provider_Phone_Validation")){
                            ProvPhoneValidationResult= sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("Provider_Phone_Validation",ProvPhoneValidationResult);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        ProvPhoneValidationResult = "null";
                        ProvMap.put("Provider_Phone_Validation",ProvPhoneValidationResult);
                        continue;
                    }

                    //Fetch the PV_Phone_Found_Websites from R3 excel
                    try {
                        String PVPhoneFoundWebsitesColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(PVPhoneFoundWebsitesColHead.equalsIgnoreCase("PV_Phone_Found_Websites")){
                            PVPhoneFoundWebsites= sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("PV_Phone_Found_Websites",PVPhoneFoundWebsites);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        PVPhoneFoundWebsites = "null";
                        ProvMap.put("PV_Phone_Found_Websites",PVPhoneFoundWebsites);
                        continue;
                    }

                    //Fetch the PV_Phone_Not_Found_Websites from R3 excel
                    try {
                        String PVPhoneNotFoundWebsitesColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(PVPhoneNotFoundWebsitesColHead.equalsIgnoreCase("PV_Phone_Not_Found_Websites")){
                            PVPhoneNotFoundWebsites= sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("PV_Phone_Not_Found_Websites",PVPhoneNotFoundWebsites);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        PVPhoneNotFoundWebsites = "null";
                        ProvMap.put("PV_Phone_Not_Found_Websites",PVPhoneNotFoundWebsites);
                        continue;
                    }

                    //Fetch the PV_Phone_Found_Organization_Websites from R3 excel
                    try {
                        String PVPhoneFoundOrganizationWebsitesColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(PVPhoneFoundOrganizationWebsitesColHead.equalsIgnoreCase("PV_Phone_Found_Organization_Websites")){
                            PVPhoneFoundOrganizationWebsites= sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("PV_Phone_Found_Organization_Websites",PVPhoneFoundOrganizationWebsites);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        PVPhoneFoundOrganizationWebsites = "null";
                        ProvMap.put("PV_Phone_Found_Organization_Websites",PVPhoneFoundOrganizationWebsites);
                        continue;
                    }

                    //Fetch the OV_Phone_Found_Websites from R3 excel
                    try {
                        String OVPhoneFoundWebsitesColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(OVPhoneFoundWebsitesColHead.equalsIgnoreCase("OV_Phone_Found_Websites")){
                            OVPhoneFoundWebsites = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("OV_Phone_Found_Websites",OVPhoneFoundWebsites);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        OVPhoneFoundWebsites = "null";
                        ProvMap.put("OV_Phone_Found_Websites",OVPhoneFoundWebsites);
                        continue;
                    }

                    //Fetch the OV_Phone_Not_Found_Websites from R3 excel
                    try {
                        String OVPhoneNotFoundWebsitesColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(OVPhoneNotFoundWebsitesColHead.equalsIgnoreCase("OV_Phone_Not_Found_Websites")){
                            OVPhoneNotFoundWebsites = sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("OV_Phone_Not_Found_Websites",OVPhoneNotFoundWebsites);
                            continue;
                        }
                    } catch (NullPointerException ignored) {
                        OVPhoneNotFoundWebsites = "null";
                        ProvMap.put("OV_Phone_Not_Found_Websites",OVPhoneNotFoundWebsites);
                        continue;
                    }

                    //Fetch the OV_Phone_Found_Organization_Websites from R3 excel
                    try {
                        String OVPhoneFoundOrganizationWebsitesColHead = sheet.getRow(0).getCell(j).toString().trim();
                        if(OVPhoneFoundOrganizationWebsitesColHead.equalsIgnoreCase("OV_Phone_Found_Organization_Websites")){
                            OVPhoneFoundOrganizationWebsites= sheet.getRow(i + 1).getCell(j).toString().trim();
                            ProvMap.put("OV_Phone_Found_Organization_Websites",OVPhoneFoundOrganizationWebsites);
                            OrgMap.put(new LinkedHashSet<>(ProvOrgNameSearchKeywordList), new LinkedHashMap<>(ProvMap));
                            OrgProvList.add(new LinkedHashMap<>(OrgMap));
                            OrgMap.clear();
                            ProvMap.clear();
                            ProvOrgNameSearchKeywordList.clear();
                            break;
                        }
                    } catch (NullPointerException ignored) {
                        OVPhoneFoundOrganizationWebsites = "null";
                        ProvMap.put("OV_Phone_Found_Organization_Websites",OVPhoneFoundOrganizationWebsites);
                        OrgMap.put(new LinkedHashSet<>(ProvOrgNameSearchKeywordList), new LinkedHashMap<>(ProvMap));
                        OrgProvList.add(new LinkedHashMap<>(OrgMap));
                        OrgMap.clear();
                        ProvMap.clear();
                        ProvOrgNameSearchKeywordList.clear();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return OrgProvList;
    }

}













