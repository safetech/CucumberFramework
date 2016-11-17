package com.uhc.aarp.preferences.automation.steps;

import com.google.gson.JsonObject;
import com.uhc.aarp.automation.util.FileUtils;
import com.uhc.aarp.automation.util.JsonUtils;
import com.uhc.aarp.automation.util.RestApiClient;
import com.uhc.aarp.automation.util.PropertyUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class GetIndividualTransPrefStepdefs {

    private String systemName;
    private String systemId;

    private static final String updateContextPath = "/preferences-api/individual";

    private RestApiClient restApiClient;



//    @Before
//    public void setup() {
//        restApiClient = new RestApiClient();
//        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
//    }

    @Given("^preferencesApi is up and running$")
    public void preferencesApi_is_up_and_running()  {
        // Express the Regexp above with the code you wish you had
        restApiClient = new RestApiClient();
        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
    }

    @Given("^I provide a systemName as \"([^\"]*)\"$")
    public void I_provide_a_systemName_as(String systemName) {
        // Express the Regexp above with the code you wish you had
        this.systemName = systemName;
    }

    @And("^I provide a ids as \"([^\"]*)\"$")
    public void I_provide_a_ids_as(String systemId) throws Throwable {
        // Express the Regexp above with the code you wish you had
        this.systemId = systemId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        restApiClient.setHeaders(headers);
        restApiClient.setRestUri(updateContextPath + "/preferences?systemName=" + systemName + "&ids=" + systemId);

    }

    @When("^I invoke the get individual transactional preferences API$")
    public void I_invoke_the_get_individual_transactional_preferences_API() throws Throwable {
        // Use the restApiClient to set the headers

        restApiClient.setHttpMethod(HttpMethod.GET);
        restApiClient.execute();

    }



    @Then("^I expect the response to match \"([^\"]*)\"$")
    public void I_expect_the_response_to_match(String expectedResult) throws Throwable {
        // Express the Regexp above with the code you wish you had
        JSONAssert.assertEquals(FileUtils.readFixture(expectedResult),restApiClient.getResponseEntity().getBody(),false);
    }

    @And("^I expect the response to match the \"([^\"]*)\"$")
    public void I_expect_the_response_to_match_the(int httpStatus) throws Throwable {
        // Express the Regexp above with the code you wish you had

        if (httpStatus == 200){
            assertEquals(restApiClient.getResponseEntity().getStatusCode().value(),httpStatus);
        } else {

            JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());
            assertNotNull(actualJsonObject.get("errors").getAsJsonArray().get(0).getAsJsonObject().get("errorCode").getAsString());
            assertEquals(restApiClient.getResponseEntity().getStatusCode().value(), httpStatus);
        }
    }
}
