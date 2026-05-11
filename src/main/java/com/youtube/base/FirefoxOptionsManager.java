package com.youtube.base;

import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxOptionsManager {

    public static FirefoxOptions getOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        options.setCapability("headless", headless);

        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.push.enabled", false);
        options.addPreference("privacy.trackingprotection.enabled", true);
        options.addPreference("media.volume_scale", "0.0");

        if (headless) {
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        return options;
    }

}
