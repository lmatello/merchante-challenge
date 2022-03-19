package com.merchante.pages.selenium.desktop;


import com.merchante.pages.selenium.common.Page;
import com.merchante.utils.PageUrl;
import com.merchante.webdriver.ExtendedWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;


@PageUrl(url = "site.host/")
public class HomePage extends Page {

    @FindBy(id = "cb1-edit")
    WebElement searchBoxInput;
    @FindBy(className = "nav-icon-search")
    WebElement searchBoxButton;

    public HomePage(ExtendedWebDriver extendedWebDriver, Environment environment) {
        super(extendedWebDriver,environment);
    }

    public void searchBy(String keyword){
        searchBoxInput.sendKeys(keyword);
    }

    public ResultPage clickOnSearchButton(){
        searchBoxButton.click();
        return new ResultPage(webDriver,environment);
    }
}