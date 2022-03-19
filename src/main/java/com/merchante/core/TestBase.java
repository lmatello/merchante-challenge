package com.merchante.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merchante.config.BaseConf;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.*;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {BaseConf.class})
public class TestBase extends AbstractTestNGSpringContextTests  {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBase.class);

    @Autowired
    protected Environment environment;

    @Autowired
    private ObjectMapper customObjectMapper;

    protected SoftAssertions softAssert;

    @Parameters({"customParams"})
    @BeforeSuite(alwaysRun = true)
    public void setUpSuite(@Optional("") String customParams) throws Exception {
        System.setProperty("customParams",customParams);
        configureRestAssuredObjectMapper();
        super.springTestContextPrepareTestInstance();
    }

    @BeforeMethod(alwaysRun = true)
    protected void logStartTest(){
        logInfo("TEST-START.");
        softAssert = new SoftAssertions();
    }

    @AfterMethod(alwaysRun = true)
    protected void logEndTest(){
        logInfo("TEST-END.");
    }

    @Parameters({"submitReport"})
    @AfterSuite(alwaysRun = true)
    protected void submitReport(@Optional("false") String submitReport, ITestContext context){
        logInfo("Suite execution FINISHED.");
    }

    protected void logInfo(String message){
        LOGGER.info(message);
    }

    private void configureRestAssuredObjectMapper(){
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (type, s) -> {
                    ObjectMapper objectMapper = customObjectMapper;
                    return objectMapper;
                }
        ));
    }

    private boolean failedTest(ITestContext context){
        return context.getFailedTests().size() > 0;
    }


}
