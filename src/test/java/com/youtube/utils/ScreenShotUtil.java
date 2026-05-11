package com.youtube.utils;

import com.youtube.base.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.youtube.base.BaseTest.loggerManager;
import static com.youtube.utils.TestConstants.SCREENSHOTS_DIR;

public class ScreenShotUtil {

    static WebDriver driver = DriverManager.getDriver();

    @Attachment(value = "{screenshotName}", type = "image/png")
    public static String takeScreenshot(String testCaseName, String screenshotName) {
        String timestamp = null;
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File destination = new File(SCREENSHOTS_DIR + "/screenshot_" + timestamp + "_" + testCaseName + "_" + screenshotName + ".png");

            FileUtils.copyFile(screenshot, destination);
            loggerManager.info("Screenshot taken for test case: {}", testCaseName);
            loggerManager.info("Screenshot captured: {}", screenshotName);
            loggerManager.info("Screenshot saved to: {}", destination.getAbsolutePath());
        } catch (Exception e) {
            Allure.addAttachment("Screenshot Error",
                    "Failed to capture screenshot: " + e.getMessage());
        }
        return "screenshot_" + timestamp + "_" + testCaseName + "_" + screenshotName + ".png";
    }

}
