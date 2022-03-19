package com.merchante.webdriver;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.core.env.Environment;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.getProperty;

/**
 * Created by Lucas.
 */
public class WebDriverBuilder {

    private Environment environment;
    private URL serverUrl;
    private static final List<String> BROWSER_LIST = Arrays.asList("chrome","ie","firefox","chrome-headless", "chrome-mobile","appium-safari-ios", "bs-safari-ios-mobile");
    private final String BROWSER = Optional.ofNullable(getProperty("browser")).orElse("safari-mobile");

    public WebDriverBuilder(Environment environment){
        this(environment, null);
        setBrowserDriverPath();
    }

    public WebDriverBuilder(Environment environment, URL serverUrl) {
        this.environment = environment;
        this.serverUrl = serverUrl;
        setBrowserDriverPath();
        validateBrowser();
    }

    private void validateBrowser() {
        if (BROWSER.isEmpty() || !BROWSER_LIST.contains(BROWSER)){
            throw new RuntimeException("Not supported browser value: " + BROWSER);
        }
    }

    public WebDriver build(){
        switch (BROWSER){
            case "chrome":
                return new ChromeDriver(CapabilitiesFiller.buildChrome());
            case "firefox":
                return new FirefoxDriver(CapabilitiesFiller.buildFirefox());
            case "chrome-headless":
                return new ChromeDriver(CapabilitiesFiller.buildChromeHeadless());
            case "appium-safari-ios":
                return new IOSDriver(serverUrl, CapabilitiesFiller.buildiOS());
            case "bs-safari-ios-mobile":
                return new RemoteWebDriver(serverUrl, CapabilitiesFiller.buildBrowserStackIphoneMobile());
            default:
                return new RemoteWebDriver(serverUrl, CapabilitiesFiller.buildSafariIOSMobile());
        }
    }

    private void setBrowserDriverPath(){
        if (!BROWSER.equalsIgnoreCase("bs-safari-ios-mobile")){
            OS os = OS.fromString(System.getProperty("os.name"));
            String driverPath = MessageFormat.format("driver.path.{0}.{1}", BROWSER, os);
            String driverPropertyKey = MessageFormat.format("driver.property.{0}.{1}", BROWSER, os);
            System.setProperty(this.environment.getProperty(driverPropertyKey), this.environment.getProperty(driverPath));
        }
    }

    enum OS{
        linux, mac, win;
        public static OS fromString(String text) {
            return Arrays.stream(OS.values()).filter(value->text.toLowerCase().contains(value.name()))
                    .findFirst().orElse(null);
        }
    }

    public static class CapabilitiesFiller {

        public static ChromeOptions buildChrome(){
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("chrome", Platform.ANY);
            // To abort some kind of request
            chromeOptions.addArguments("--host-resolver-rules=MAP aly.jst.ai 127.0.0.1'");
            return chromeOptions;
        }

        public static ChromeOptions buildChromeHeadless(){
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(true);
            return chromeOptions;
        }

        public static DesiredCapabilities buildSafariIOSMobile(){
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", "iPhone");
            caps.setCapability("device", "iPhone 11");
            caps.setCapability("realMobile", "true");
            caps.setCapability("os_version", "14.0");
            caps.setCapability("name", "BStack-[Java] Sample Test");
            caps.setCapability("build", "BStack Build Number 1");
            return caps;
        }

        public static DesiredCapabilities buildBrowserStackIphoneMobile(){
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", "iPhone");
            caps.setCapability("device", "iPhone 11");
            caps.setCapability("realMobile", "true");
            caps.setCapability("os_version", "14.0");
            caps.setCapability("name", "Regression iPhone"); // test name
            return caps;
        }

        public static FirefoxOptions buildFirefox(){
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("firefox", Platform.ANY);
            firefoxOptions.setCapability("marionette", true);
            return firefoxOptions;
        }

        // For Appium and a Real iPhone
        public static DesiredCapabilities buildiOS(){
            DesiredCapabilities desiredCapabilities = buildCapabilities("Safari", Platform.IOS, "{UDID}", "iPhone 12", "14.5");
            desiredCapabilities.setCapability("automationName","XCUITest");
            desiredCapabilities.setCapability("xcodeOrgId","{xcodeOrgId_value}");
            desiredCapabilities.setCapability("xcodeSigningId","iPhone Developer");
            return desiredCapabilities;
        }

        private static DesiredCapabilities buildCapabilities(String browserType, Platform platform, String udid, String deviceName, String platformVersion){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", browserType);
            capabilities.setCapability("platformName", platform);
            capabilities.setCapability("udid",udid);
            capabilities.setCapability("deviceName",deviceName);
            capabilities.setCapability("platformVersion",platformVersion);
            return capabilities;
        }
    }
}
