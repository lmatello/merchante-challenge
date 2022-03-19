package com.merchante.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

/*
    This class is related to configuration for extentReport report
*/

public class ExtentManager {

    private static ExtentReports extent;
    private static String reportFileName = "reports/merchante-automation-report.html";

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
    public static ExtentReports createInstance() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(new File(reportFileName));
        htmlReporter.config().setReportName("Merchante Automation Report");
        htmlReporter.config().setDocumentTitle("Merchante Automation Report");
        htmlReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
}
