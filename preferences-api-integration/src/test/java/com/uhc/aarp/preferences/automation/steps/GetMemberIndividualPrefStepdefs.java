package com.uhc.aarp.preferences.automation.steps;

import com.google.gson.JsonObject;
import com.uhc.aarp.automation.util.FileUtils;
import com.uhc.aarp.automation.util.JsonUtils;
import com.uhc.aarp.automation.util.PropertyUtils;
import com.uhc.aarp.automation.util.RestApiClient;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by lnampal on 4/19/16.
 */
public class GetMemberIndividualPrefStepdefs {


    private String individualId;
    private static final String contextPath = "/preferences-api/enrollment/members/";

    private RestApiClient restApiClient;

    @Before
    public void setup(){
        restApiClient = new RestApiClient();
        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
    }

    @Given("^I provide a ole ref id as \"([^\"]*)\"$")
    public void assignOleRefId(String oleRefId) {
        // Express the Regexp above with the code you wish you had
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        restApiClient.setHeaders(headers);
        restApiClient.setRestUri(contextPath + oleRefId);

    }

    @When("^I invoke the get preferences API$")
    public void i_invoke_the_get_enrollment_API() throws Throwable {
        // Express the Regexp above with the code you wish you had
        restApiClient.setHttpMethod(HttpMethod.GET);

        restApiClient.execute();
    }

    @Then("^I expect the response as \"([^\"]*)\"$")
    public void I_expect_the_response_as(String expectedResponse) throws Throwable {

        //   JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());
        // assertNotNull(actualJsonObject.get("errors").getAsJsonArray().get(0).getAsJsonObject().get("errorCode").getAsString());


        JSONAssert.assertEquals(FileUtils.readFixture(expectedResponse), restApiClient.getResponseEntity().getBody(), false);
    }


    @And("^I expect the response to be \"([^\"]*)\"$")
    public void I_expect_the_response_to_be(int expectedStatusCode) throws Throwable {

        assertEquals(expectedStatusCode, restApiClient.getResponseEntity().getStatusCode().value());
    }

    @Then("^I expect the valid response as \"([^\"]*)\"$")
    public void I_expect_the_valid_response_as(String expectedResponse) throws Throwable {

        JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());
        assertNotNull(actualJsonObject.get("errors").getAsJsonArray().get(0).getAsJsonObject().get("errorCode").getAsString());


        JSONAssert.assertEquals(FileUtils.readFixture(expectedResponse), restApiClient.getResponseEntity().getBody(), false);
    }
}
