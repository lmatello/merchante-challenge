package com.merchante.pages.selenium.desktop;

import com.merchante.pages.selenium.common.Page;
import com.merchante.utils.PageUrl;
import com.merchante.webdriver.ExtendedWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;

@PageUrl(url = "search.host/")
public class ResultPage extends Page {

    @FindBy(className = "ui-search-breadcrumb__title")
    private WebElement breadCrumTitle;

    public ResultPage(ExtendedWebDriver extendedWebDriver, Environment environment) {
        super(extendedWebDriver, environment);
    }

    public String getBreadCrumTitle(){
        return breadCrumTitle.getText();
    }

}
