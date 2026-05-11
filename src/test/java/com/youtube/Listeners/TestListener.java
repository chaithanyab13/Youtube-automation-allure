package com.youtube.Listeners;

import com.youtube.utils.ScreenShotUtil;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.youtube.base.BaseTest.loggerManager;
import static com.youtube.utils.TestConstants.hyphens;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        loggerManager.error("Test FAILED: {}", testName);
        ScreenShotUtil.takeScreenshot("Test_case_failed", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        loggerManager.info("Test PASSED: {}", methodName);
        loggerManager.info(hyphens);
        ScreenShotUtil.takeScreenshot("Test_case_passed", result.getMethod().getMethodName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        loggerManager.info(hyphens);
        loggerManager.info("Test STARTED: {}", methodName);
    }

    @Override
    public void onTestSkipped(ITestResult result){
        String methodName = result.getMethod().getMethodName();
        loggerManager.info("Test Skipped {}", methodName);
        ScreenShotUtil.takeScreenshot("Test_case_skipped", result.getMethod().getMethodName());
    }

}
