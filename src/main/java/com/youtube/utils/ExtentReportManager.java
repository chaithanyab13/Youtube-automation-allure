package com.youtube.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.youtube.utils.TestConstants.REPORTS_DIR;

public class ExtentReportManager {

    public static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

            String reportPath = REPORTS_DIR + "YouTubeReport_" + timestamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            /* Setting view order to:
               1. Dashboard
               2. Test
               3. Category
               4. Exception
               5. Author
               6. Device
            */
            spark.viewConfigurer()
                    .viewOrder()
                    .as(new ViewName[] {
                            ViewName.DASHBOARD,
                            ViewName.TEST,
                            ViewName.CATEGORY,
                            ViewName.EXCEPTION,
                            ViewName.AUTHOR,
                            ViewName.DEVICE,
                            ViewName.LOG
                    });
            spark.config().setTheme(Theme.DARK);
            spark.config().setEncoding("utf-8");
            spark.config().setReportName("YouTube Automation Test Report");
            spark.config().setDocumentTitle("Automation Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Project", "YouTube Automation");
            extent.setSystemInfo("Tester", "Chaithanya B");
            extent.setSystemInfo("Browser", "Chrome");

            extent.flush();
        }
        return extent;
    }
}

