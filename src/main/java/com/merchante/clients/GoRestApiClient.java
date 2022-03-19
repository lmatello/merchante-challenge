package com.merchante.clients;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import static io.restassured.RestAssured.given;

@Service
@Lazy
public class GoRestApiClient extends CommonClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoRestApiClient.class);

    @Value("${gorest.host}")
    private String host;
    @Value("${gorest-get-users-list.endpoint}")
    private String getUsersListEndpoint;
    @Value("${gorest-get-users.endpoint}")
    private String getUserEndpoint;

    public GoRestApiClient(){ }

    public Response getUsers() {
        LOGGER.info("Trying to get users through api");
        return given().spec(setBaseUriAs(host)).urlEncodingEnabled(false)
                .get(getUsersListEndpoint);
    }

    public Response getUserBy(String userId) {
        LOGGER.info("Trying to get users through api by userId for {} ...", userId);
        return given().spec(setBaseUriAs(host)).urlEncodingEnabled(false)
                .get(MessageFormat.format(getUserEndpoint, userId));
    }


}
