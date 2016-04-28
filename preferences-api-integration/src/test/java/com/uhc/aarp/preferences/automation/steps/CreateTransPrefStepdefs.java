package com.uhc.aarp.preferences.automation.steps;

import com.google.gson.JsonObject;
import com.uhc.aarp.automation.util.FileUtils;
import com.uhc.aarp.automation.util.JsonUtils;
import com.uhc.aarp.automation.util.PropertyUtils;
import com.uhc.aarp.automation.util.RestApiClient;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.junit.Assert.assertEquals;

/**
 * Created by lnampal on 4/27/16.
 */
public class CreateTransPrefStepdefs {

    private String systemName;
    private String oleRefId;
    private static final String contextPath = "/preferences-api/enrollments/";

    private RestApiClient restApiClient;

//    @Before
//    public void setup(){
//        restApiClient = new RestApiClient();
//        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
//    }

    @Given("^preferences Api is up and running$")
    public void preferencesApi_up_and_running()  {
        // Express the Regexp above with the code you wish you had
        restApiClient = new RestApiClient();
        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
    }

    @Given("^I supply a ole ref id as \"([^\"]*)\"$")
    public void I_supply_a_ole_ref_id_as(String oleRefId) throws Throwable {
        // Express the Regexp above with the code you wish you had
        this.oleRefId = oleRefId;
    }


    @And("^I provide an systemName as \"([^\"]*)\"$")
    public void I_provide_an_systemName_as(String systemName) throws Throwable {
        // Express the Regexp above with the code you wish you had
        this.systemName = systemName;
    }

    @And("^I provide a json pay load as \"([^\"]*)\"$")
    public void I_provide_a_json_pay_load_as(String fileName) throws Throwable {
        // Express the Regexp above with the code you wish you had

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        restApiClient.setHeaders(headers);
        restApiClient.setHeaders(headers);
        JsonObject requestJson = JsonUtils.createJsonFromString(FileUtils.readFixture(fileName));
        restApiClient.setRequestBody(requestJson.toString());
        restApiClient.setRestUri(contextPath + oleRefId + "?systemName=" + systemName);
    }


    @And("^I expect response to match the \"([^\"]*)\"$")
    public void I_expect_tha_response_to_match_the(int httpStatusCode) throws Throwable {
        // Express the Regexp above with the code you wish you had
        assertEquals(restApiClient.getResponseEntity().getStatusCode().value(), httpStatusCode);
    }

    @When("^I invoke the create transactional preferences API$")
    public void I_invoke_the_create_transactional_preferences_API() throws Throwable {
        // Express the Regexp above with the code you wish you had
        restApiClient.setHttpMethod(HttpMethod.POST);
        restApiClient.execute();
    }

    @Then("^I expect response to match \"([^\"]*)\"$")
    public void I_expect_the_response_to_match(String expectedResult) throws Throwable {
        // Express the Regexp above with the code you wish you had
        JSONAssert.assertEquals(FileUtils.readFixture(expectedResult), restApiClient.getResponseEntity().getBody(), false);
    }


}
