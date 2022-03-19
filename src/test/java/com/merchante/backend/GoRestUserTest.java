package com.merchante.backend;

import com.merchante.core.TestBase;
import com.merchante.helpers.GoRestHelper;
import com.merchante.model.api.UserData;
import com.merchante.model.api.UserDataResponse;
import com.merchante.model.api.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;


public class GoRestUserTest extends TestBase {

    @Autowired
    private GoRestHelper goRestHelper;

    /*
    Millak, this is what Im doing in my API exaple test:

	1- Get a list of users:
		Service: GET https://gorest.co.in/public/v1/users
	2- From the list of results, find the active users and obtain the details of the first user in the list.
		Service: GET https://gorest.co.in/public/v1/users/{userId}
	Asserts:
	3- On the resulting user, assert the following:
		"status": "active"
     */

    // This kind of test is more for logic demostration (and not so much for testing inself)
    @Test(groups = {"regression", "api"})
    public void givenShopifyOrder_WhenGetOrder_ThenNameIsCorrect() {
        //GIVEN
        UserResponse userResponseList = goRestHelper.getUserList();
        //WHEN
        UserData userActive = goRestHelper.getFirstActiveUser(userResponseList.data);
        //THEN
        softAssert.assertThat(userActive.getStatus()).as("Status").isEqualTo("active");
        softAssert.assertAll();
    }

     /*
    Millak, this is what Im doing in my second API exaple test:

	Given a specified user id
	When I retrieve user through api by id
    Then The userData is correct

    NOTE:
    I realized that the user data for the chosen ID changes from time to time, so it is possible that the assert does not work at the time it is run.
    (This has to do with the chosen public API)
    But in practical terms, the example works, I understand

     */

    @Test(groups = {"regression", "api"})
    public void givenAnSpecificUserID_WhenFindById_ThenTheDataOfUserIsCorrect() {
        //GIVEN
        Long idExpected = 2397L;
        //WHEN
        UserDataResponse userActive = goRestHelper.getUserById(idExpected.toString());
        //THEN
        softAssert.assertThat(userActive.data.getName()).as("Name").isEqualTo("Sen. Som Dhawan");
        softAssert.assertThat(userActive.data.getEmail()).as("Email").isEqualTo("dhawan_sen_som@fadel-cassin.org");
        softAssert.assertThat(userActive.data.getGender()).as("Gender").isEqualTo("female");
        softAssert.assertAll();
    }

}