package com.youtube.base;

import java.util.ArrayList;
import java.util.List;

public class CommonBrowserOptions {

    private CommonBrowserOptions() {}

    public static List<String> getCommonArguments(boolean headless) {
        List<String> args = new ArrayList<>();

        if (headless) {
            args.add("headless=new");
            args.add("window-size=1920,1080");
            args.add("autoplay-policy=no-user-gesture-required");
            args.add("mute-audio");
            args.add("disable-features=MediaRouter");
            args.add("disable-backgrounding-occluded-windows");
            args.add("disable-background-timer-throttling");
            args.add("disable-renderer-backgrounding");
        }

        args.add("disable-notifications");
        args.add("disable-popup-blocking");
        args.add("disable-infobars");
        args.add("disable-extensions");
        args.add("disable-gpu");
        args.add("no-sandbox");
        args.add("incognito");
        args.add("ignore-certificate-errors");
        args.add("disable-blink-features=AutomationControlled");
        args.add("disable-background-timer-throttling");
        args.add("disable-backgrounding-occluded-windows");
        args.add("disable-renderer-backgrounding");

        return args;
    }


}
