package com.youtube.base;

import com.youtube.pages.YouTubeController;
import com.youtube.utils.AllureReportUtils;
import com.youtube.utils.LoggerManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.youtube.base.BasePage.driver;
import static com.youtube.tests.YouTubeVideoControlTest.formatter;
import static com.youtube.utils.ScreenShotUtil.takeScreenshot;
import static com.youtube.utils.TestConstants.*;

public abstract class BaseTest {

    protected YouTubeController youtubeController;
    public static Logger loggerManager = LoggerManager.getLogger(BaseTest.class);
    Long testStartTime;
    Long testEndTime;
    Long testDuration;

    @Parameters({"testUrl","browserType","headless"})
    @BeforeClass
    public void setUp(@Optional("https://www.youtube.com/watch?v=XuH-k-IE5yg&list=RDXuH-k-IE5yg&start_radio=1") String testUrl,@Optional("chrome") String browserType, boolean headless) {
        Allure.step("Setting up browser and YouTube controller");
        loggerManager.info("Starting browser setup for tests");
        driver = DriverFactory.createDriver(browserType, headless);
        DriverManager.setDriver(driver);
        waitForTimeout(10);
        loggerManager.info("Browser setup completed");

        loggerManager.info("Initializing YouTube controller");
        youtubeController = new YouTubeController(driver);
        loggerManager.info("YouTube controller initialized");
        loggerManager.info("Navigating to YouTube video: {}", TEST_URL_1);
        youtubeController.openYouTubeVideo(TEST_URL_1);
        loggerManager.info("Navigated to YouTube video: {}", TEST_URL_1);

        loggerManager.info(hyphens);
    }

    public static void deleteFiles(String directoryPath, boolean recursive) {

        Path dirPath = Paths.get(directoryPath);

        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            loggerManager.info("Directory does not exist: {}", directoryPath);
            return;
        }

        try (Stream<Path> paths = recursive ? Files.walk(dirPath) : Files.list(dirPath)) {

            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            Files.deleteIfExists(file);
                        } catch (IOException e) {
                            loggerManager.info("Failed to delete file: {}", file);
                        }
                    });

            loggerManager.info(
                    recursive
                            ? "All files deleted recursively (directory preserved): {}"
                            : "All files deleted in directory: {}",
                    directoryPath
            );

        } catch (IOException e) {
            loggerManager.error("Error cleaning directory: {} {}", directoryPath, e.getMessage());
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        loggerManager.info(stars);
        loggerManager.info("Starting test suite execution");

        testStartTime = System.nanoTime();
        loggerManager.info("Test execution start time : {} nanoseconds", testStartTime);

        AllureReportUtils.addEnvironmentVariables();
        loggerManager.info("Added environment variables to Allure report");
        Allure.addAttachment("Test Start Time", LocalDateTime.now().format(formatter));

        loggerManager.info("Cleaning up previous screenshots...");
        File screenshotDirectory = new File(SCREENSHOTS_DIR);
        deleteFiles(screenshotDirectory.getPath(), false);

        loggerManager.info("Cleaning up previous reports...");
        File reportDirectory = new File(REPORTS_DIR);
        deleteFiles(reportDirectory.getPath(), false);

        loggerManager.info("Cleaning up target/allure-results directory...");
        File targetDirectory = new File(ALLURE_RESULTS_DIR);
        deleteFiles(targetDirectory.getPath(), true);
    }

    @AfterMethod
    public void afterMethod(Method method) {
        /* Capture screenshot after each test method for debugging */
        youtubeController.moveMouseSmoothly(10, 10, 10, 10, 1);
        Allure.step("Capture screenshot after test execution");
        loggerManager.info("Capturing screenshot after test execution");
        takeScreenshot("after_test_execution", method.getName());
    }

    @AfterClass(alwaysRun = true)
    @Step("Close browser and perform cleanup")
    public void tearDown() {
        Allure.step("Close YouTube controller and browser");
        loggerManager.info("Tearing down browser instance after tests");
        if (driver != null){
            driver.close();
        } else {
            Allure.addAttachment("Tear down Warning", "WebDriver instance was null during tear down.");
            loggerManager.info("WebDriver instance was null during tear down.");
        }
        Allure.addAttachment("Test End Time", LocalDateTime.now().format(formatter));
        loggerManager.info(hyphens);
    }

    @AfterSuite
    public void afterSuite() {
        Allure.addAttachment("Total Execution Time", "Tests completed at: " + LocalDateTime.now().format(formatter));
        loggerManager.info("All tests completed. Total execution time logged.");
        testEndTime = System.nanoTime();
        loggerManager.info("Test execution start time : {} nanoseconds", testEndTime);
        testDuration = Duration.ofNanos(testEndTime - testStartTime).toSeconds();
        loggerManager.info("Total test execution time: {} seconds", testDuration);
        testDuration = Duration.ofNanos(testEndTime - testStartTime).toMinutes();
        loggerManager.info("Total test execution time: {} minutes", testDuration);
        loggerManager.info(stars);
    }

    public void waitForTimeout(int timeInSeconds) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
            loggerManager.info("Waiting for {} seconds, waiting to finish execution", timeInSeconds);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            loggerManager.error("Thread was interrupted during wait: {}", e.getMessage());
        }
    }

}
