package com.youtube.utils;

import io.qameta.allure.Allure;

import java.util.HashMap;
import java.util.Map;

public class AllureReportUtils {

    public static void addEnvironmentVariables() {
        Map<String, String> environment = new HashMap<>();
        environment.put("OS", System.getProperty("os.name"));
        environment.put("User Name", System.getProperty("user.name"));
        environment.put("Java Version", System.getProperty("java.version"));
        environment.put("Browser", "Chrome");

        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setHistoryId(null); // Reset history id to avoid test duplication
        });
    }

    public static void addCustomAttachment(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }

    public static void addVideoDurationInfo(double currentTime, double duration) {
        String videoInfo = String.format("Current Time: %.2f seconds\nTotal Duration: %.2f seconds",
                currentTime, duration);
        addCustomAttachment("Video Timeline", videoInfo);
    }
}