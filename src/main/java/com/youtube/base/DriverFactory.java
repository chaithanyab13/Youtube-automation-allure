package com.youtube.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    public static WebDriver createDriver(String browser, boolean headless) {

        return switch (browser.toLowerCase()) {
            case "chrome" ->
                    new ChromeDriver(ChromeOptionsManager.getOptions(headless));
            case "edge" ->
                    new EdgeDriver(EdgeOptionsManager.getOptions(headless));
            case "firefox" ->
                    new FirefoxDriver(FirefoxOptionsManager.getOptions(headless));
            default ->
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

}
