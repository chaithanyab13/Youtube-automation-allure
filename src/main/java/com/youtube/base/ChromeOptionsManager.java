package com.youtube.base;

import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeOptionsManager {

    public static ChromeOptions getOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments(CommonBrowserOptions.getCommonArguments(headless));
        return options;
    }
}
