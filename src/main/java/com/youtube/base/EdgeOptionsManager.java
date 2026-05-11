package com.youtube.base;

import org.openqa.selenium.edge.EdgeOptions;

public class EdgeOptionsManager {

    public static EdgeOptions getOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(CommonBrowserOptions.getCommonArguments(headless));
        return options;
    }

}
