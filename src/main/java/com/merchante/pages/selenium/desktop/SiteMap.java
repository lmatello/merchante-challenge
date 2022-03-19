package com.merchante.pages.selenium.desktop;


import com.merchante.utils.PageUrlUtils;
import com.merchante.webdriver.ExtendedWebDriver;
import org.springframework.core.env.Environment;

import static com.merchante.utils.PageUrlUtils.urlFor;

/*
    This siteMap class is used for start the navigation for frontend automation cases
*/

public class SiteMap {

    private final ExtendedWebDriver extendedWebDriver;
    private Environment environment;

    public SiteMap(ExtendedWebDriver extendedWebDriver, Environment environment){
        this.extendedWebDriver = extendedWebDriver;
        this.environment = environment;
    }

    public HomePage homePage(){
        extendedWebDriver.get(PageUrlUtils.urlFor(HomePage.class, environment));
        return new HomePage(extendedWebDriver, environment);
    }

}