package com.merchante.frontend.desktop;

import com.merchante.core.selenium.TestBaseFrontEnd;
import com.merchante.pages.selenium.desktop.HomePage;
import com.merchante.pages.selenium.desktop.ResultPage;
import com.merchante.utils.DataProviders;
import org.testng.annotations.Test;

public class HomeTest extends TestBaseFrontEnd {

    @Test(groups = {"regression", "frontend", "search"}, description = "Search by keyword", dataProvider = "keywords", dataProviderClass = DataProviders.class)
    public void givenIamInHomePage_whenISearchByKeyword_thenISeeResultPageWithTheCorrectBreadcrumTitle(String keyword) {
        //GIVEN
        HomePage home = siteMap.homePage();
        //WHEN
        home.searchBy(keyword);
        ResultPage resultPage = home.clickOnSearchButton();
        //THEN
        softAssert.assertThat(resultPage.getBreadCrumTitle()).as("Breadcrum Title").isEqualTo(keyword);
        softAssert.assertAll();
    }
}