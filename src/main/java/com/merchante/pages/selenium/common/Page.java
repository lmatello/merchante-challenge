package com.merchante.pages.selenium.common;

import com.merchante.utils.PageUrlUtils;
import com.merchante.webdriver.ExtendedWebDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public abstract class Page {

    private static final int TIMEOUT_FOR_URL_LOADING = 25;
    private static final int TIMEOUT_FOR_DOCUMENT_READY = 40;
    protected ExtendedWebDriver webDriver;
    protected Environment environment;
    protected Map<String, Object> pageContext;

    protected Page(ExtendedWebDriver webDriver, Environment environment, Map pageContext) {
        this.webDriver = webDriver;
        this.pageContext = pageContext;
        this.environment = environment;
        PageFactory.initElements(webDriver, this);
        waitUntilPageIsLoaded();
    }

    protected Page(ExtendedWebDriver webDriver, Environment environment){
        this(webDriver, environment, new HashMap());
    }

    protected void waitUntilPageIsLoaded() {
        waitUntilTargetUrlIsLoaded();
        waitUntilDocumentReadyStateIsComplete();
    }

    private boolean appliesToGivenUrl(String url) {
        return url.startsWith(targetUrl());
    }

    private String targetUrl() {
        return removeHttpFromUrl(PageUrlUtils.urlFor(this.getClass(), environment, pageContext));
    }

    public String url() {
        return webDriver.getCurrentUrl();
    }

    protected void waitUntilTargetUrlIsLoaded() {
        try {
            webDriver.waitUntil(TIMEOUT_FOR_URL_LOADING, wd -> appliesToGivenUrl(removeHttpFromUrl(wd.getCurrentUrl())));
        } catch (TimeoutException e) {
            throw new IllegalArgumentException("The Current URL is not supported by " + this.targetUrl());
        }
    }

    private String removeHttpFromUrl(String currentUrl){
        return currentUrl.replaceFirst("(https://|http://)","");
    }

    protected void waitUntilDocumentReadyStateIsComplete() {
        webDriver.waitUntil(TIMEOUT_FOR_DOCUMENT_READY, wd -> webDriver.executeScript("return document.readyState").equals("complete"));
    }

    public String getValueOfCookieNamed(String name) {
        return webDriver.getValueOfCookieNamed(name);
    }

}
