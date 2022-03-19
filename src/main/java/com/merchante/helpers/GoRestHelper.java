package com.merchante.helpers;

import com.merchante.clients.GoRestApiClient;
import com.merchante.model.api.UserData;
import com.merchante.model.api.UserDataResponse;
import com.merchante.model.api.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoRestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoRestHelper.class);

    @Autowired
    private GoRestApiClient goRestApiClient;

    public UserResponse getUserList() {
        LOGGER.info("Trying to get list of users...");
        return goRestApiClient.getUsers()
                .then()
                .extract().response()
                .as(UserResponse.class);
    }

    public UserData getFirstActiveUser(List<UserData> userDataList) {
        return userDataList.stream().filter(userData -> userData.getStatus().equalsIgnoreCase("active")).findFirst().get();
    }

    public UserDataResponse getUserById(String id) {
        LOGGER.info("Trying to get user by id for {}...", id);
        return goRestApiClient.getUserBy(id)
                .then()
                .extract().response()
                .as(UserDataResponse.class);
    }

}


