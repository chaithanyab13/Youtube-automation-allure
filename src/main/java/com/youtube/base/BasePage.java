package com.youtube.base;

import com.youtube.utils.LoggerManager;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected static WebDriver driver;
    protected WebDriverWait wait;
    protected static JavascriptExecutor javascriptExecutor;
    protected static Logger loggerManager = LoggerManager.getLogger(BasePage.class);

    public void YouTubeController(WebDriver driver) {
        BasePage.driver = driver;
        loggerManager.info("BasePage WebDriver set to {}", driver);
    }

    public BasePage(WebDriver driver){
        BasePage.driver = driver;
        if(driver != null) {
            PageFactory.initElements(new AppiumFieldDecorator(driver), this);
            this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));
            javascriptExecutor = (JavascriptExecutor) driver;
            loggerManager.info("Initialized BasePage with WebDriver: {} and WebDriverWait: {}", driver, wait);
        }
    }

    public static void clickUsingJavaScript(String script) {
        loggerManager.info("Clicking using JavaScript: {}", script);
        javascriptExecutor.executeScript(script);
    }

    public static void executeJavaScript(String script) {
        loggerManager.info("Executing JavaScript: {}", script);
        javascriptExecutor.executeScript(script);
    }

    // Generic JavaScript executor method
    public <T> T executeGenericJavaScript(String script, Class<T> returnType, Object... args) {
        Object result = javascriptExecutor.executeScript(script, args);

        // void support
        if (returnType == Void.class) {
            return null;
        }

        if (result == null) {
            return null;
        }

        // 🔑 HANDLE NUMBERS SAFELY
        if (Number.class.isAssignableFrom(returnType) && result instanceof Number) {
            Number number = (Number) result;

            if (returnType == Double.class) {
                return returnType.cast(number.doubleValue());
            }
            if (returnType == Long.class) {
                return returnType.cast(number.longValue());
            }
            if (returnType == Integer.class) {
                return returnType.cast(number.intValue());
            }
        }

        return returnType.cast(result);
    }

    public static void waitForTimeout(int timeInSeconds) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
            loggerManager.info("Waiting for {} seconds, waiting to finish execution", timeInSeconds);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            loggerManager.error("Thread was interrupted during wait: {}", e.getMessage());
        }
    }

}
