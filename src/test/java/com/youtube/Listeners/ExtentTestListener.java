package com.youtube.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.youtube.base.DriverManager;
import com.youtube.utils.ExtentReportManager;
import com.youtube.utils.ScreenShotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class ExtentTestListener implements ITestListener {

    private static final ExtentReports extent = ExtentReportManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    WebDriver driver = DriverManager.getDriver();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String screenShotPath = ScreenShotUtil.takeScreenshot(String.valueOf(driver),result.getMethod().getMethodName());
        test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenShotPath = ScreenShotUtil.takeScreenshot(String.valueOf(driver),result.getMethod().getMethodName());
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String screenShotPath = ScreenShotUtil.takeScreenshot(String.valueOf(driver),result.getMethod().getMethodName());
        test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.createTest(context.getName());
        extent.flush();
    }
}

