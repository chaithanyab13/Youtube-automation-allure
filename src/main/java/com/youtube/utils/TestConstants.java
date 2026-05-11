package com.youtube.utils;

public class TestConstants {

    public static final String TEST_URL_1 = "https://www.youtube.com/watch?v=XuH-k-IE5yg&list=RDXuH-k-IE5yg&start_radio=1";
    public static final String TEST_URL_2 = "https://www.youtube.com/watch?v=fclPhO1FsOY&list=RDfclPhO1FsOY&start_radio=1";
    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 15;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    public static final String SYSTEM_DIR = System.getProperty("user.dir");
    public static final String REPORTS_DIR = SYSTEM_DIR + "/Reports/";
    public static final String SCREENSHOTS_DIR = REPORTS_DIR;
    public static final String ALLURE_RESULTS_DIR = SYSTEM_DIR + "/target/allure-results";
    public static final String stars = "*".repeat(150);
    public static final String hyphens = "-".repeat(150);
    public static final String plus = "+".repeat(150);
}
