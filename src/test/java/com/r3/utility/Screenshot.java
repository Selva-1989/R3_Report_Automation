package com.r3.utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {

    public static String getScreenshot() throws IOException {
       return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BASE64);
    }
    private static String getDateTime(){
        return new SimpleDateFormat("DD_MM_YYYY_hh_mm_ss").format(new Date());
    }
}
