package com.merchante.utils;

import org.testng.annotations.DataProvider;

/*
    This class is used to provide certain data in some test cases
*/

public class DataProviders {

    @DataProvider(name = "keywords")
    private Object[][] breweryTypes() {
        return new Object[][]
                {
                        {"Freezer"},
                        {"Iphone"}
                };
    }

}
