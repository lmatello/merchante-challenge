package com.merchante.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.merchante.webdriver.ExtendedWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class BaseHTMLReporter implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHTMLReporter.class);

    private static ExtentReports extent = ExtentManager.createInstance();
    private static ExtentTest test;
    private ExtendedWebDriver extendedWebDriver;

    @Override
    public synchronized void onTestStart(ITestResult result) {
        LOGGER.info((result.getMethod().getMethodName() + " started!"));
        extendedWebDriver = (ExtendedWebDriver) result.getAttribute("driver");
        test = extent.createTest(result.getTestClass().getRealClass().getSimpleName() + " :: " + result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
    }

    @Override
    public synchronized void onStart(ITestContext context) {
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        LOGGER.info((result.getMethod().getMethodName() + " passed!"));
        test.log(Status.PASS, MarkupHelper.createLabel("Test Case PASSED", ExtentColor.GREEN));
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        LOGGER.error(methodName + " failed!");
        //test.fail("Test fail");
        test.log(Status.FAIL, MarkupHelper.createLabel("Test case FAILED due to below issues:", ExtentColor.RED));
        test.fail(result.getThrowable());

    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        LOGGER.info((result.getMethod().getMethodName() + " skipped!"));
        test.log(Status.SKIP, MarkupHelper.createLabel("Test Case SKIPPED", ExtentColor.ORANGE));
        test.skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LOGGER.info(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

}