package com.merchante.core.selenium;

import com.merchante.core.TestBase;
import com.merchante.pages.selenium.desktop.SiteMap;
import com.merchante.webdriver.ExtendedWebDriver;
import com.merchante.webdriver.WebDriverBuilder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

public class TestBaseFrontEnd extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TestBaseFrontEnd.class);

    protected ExtendedWebDriver driver;
    protected SiteMap siteMap;

    @Parameters({"environment"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, @Optional("qa") String environment) {
        driver = new ExtendedWebDriver(new WebDriverBuilder(this.environment).build());
        try{ driver.maximize();}catch (WebDriverException e){driver.manage().window().setSize(new Dimension(1600,900));}
        siteMap = new SiteMap(driver, this.environment);
        logger.info("Setup done.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        driver.closeDriver();
        logger.info("Driver closed");
    }

}


