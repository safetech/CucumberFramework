package com.uhc.aarp.preferences.automation.steps;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.uhc.aarp.automation.util.FileUtils;
import com.uhc.aarp.automation.util.JsonUtils;
import com.uhc.aarp.automation.util.PropertyUtils;
import com.uhc.aarp.automation.util.RestApiClient;
import com.uhc.aarp.preferences.automation.dao.PreferencesDbHelper;
import com.uhc.aarp.preferences.automation.util.DateUtils;
import com.uhc.aarp.preferences.automation.util.SSLCertificateValidation;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lnampal on 5/10/16.
 */
@Slf4j
public class UpdateIndividualPreferencesStepdefs {

    private String systemName;
    public String individualId;

    private static final String contextPath = "/preferences-api/individuals/";
    private static final String olePath = "/appEnroll-web/resources/applicationDetail/ship/medsupp/";
    private Faker faker;

    private RestApiClient restApiClient;

    @Given("^preferences api up and running$")
    public void preferencesApi_up_and_running()  {
        // Express the Regexp above with the code you wish you had
        restApiClient = new RestApiClient();
        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
    }

    @And("^the appenroll api is up and running$")
    public void the_ole_api_is_running() throws Throwable {
        SSLCertificateValidation.disable();
        restApiClient = new RestApiClient();
        restApiClient.setHostName(PropertyUtils.getProperty("ole.base.url"));
    }


    @Given("^I invoke appEnroll service with \"([^\"]*)\" from \"([^\"]*)\"$")
    public void I_invoke_appEnroll_service_with_from(String channel, String appEnrollInputFile) throws Throwable {
        // Express the Regexp above with the code you wish you had
        faker = new Faker();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Content-Length", "6890");
        JsonObject requestJson = JsonUtils.createJsonFromString(FileUtils.readFixture(appEnrollInputFile));

        requestJson.get("application").getAsJsonObject().addProperty("FirstName", faker.firstName());
        requestJson.get("application").getAsJsonObject().addProperty("LastName", faker.lastName());
        requestJson.get("application").getAsJsonObject().addProperty("DOB", DateUtils.getDobInCompasFormat(70));
        requestJson.get("application").getAsJsonObject().addProperty("ReqEffectiveDate", DateUtils.getFirstDayOfPasOrFutureMonths(+1));
//        requestJson.get("application").getAsJsonObject().addProperty("ReqEffectiveDate", dpsd);
        requestJson.get("application").getAsJsonObject().addProperty("AARPMembershipNumber", faker.numerify("#########"+"1"));
        requestJson.get("application").getAsJsonObject().addProperty("MPBED", DateUtils.getFirstDayOfPasOrFutureMonths(-1));
        requestJson.get("application").getAsJsonObject().addProperty("MPAED", DateUtils.getFirstDayOfPasOrFutureMonths(-1));
        requestJson.get("application").getAsJsonObject().addProperty("AddressLine1", faker.streetAddress(false));

        System.out.println("Next MonthDate " + DateUtils.getFirstDayOfPasOrFutureMonths(+1));

        restApiClient.setRequestBody(requestJson.toString());
        restApiClient.setHeaders(headers);
        restApiClient.setHostName(PropertyUtils.getProperty("ole.base.url"));
        restApiClient.setRestUri(olePath + "?channel=" + channel);
        restApiClient.setHttpMethod(HttpMethod.PUT);
        restApiClient.execute();
    }

    @And("^I fetch the compas individual id based on appEnroll response$")
    public void I_fetch_the_compas_individual_id_based_on_appEnroll_response() throws Throwable {
        // Express the Regexp above with the code you wish you had
        JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());

        System.out.println("Get Provider Method" + restApiClient.getResponseEntity().getBody());

        String applicationId = JsonPath.read(actualJsonObject.toString(), "$.applicationId");
        Object systemApplicationId = JsonPath.read(actualJsonObject.toString(), "$.systemApplicationId");


        log.debug("Print the JSON PAth value for ApplicationId " +  applicationId);

        log.debug("Print the JSON PAth value for ApplicationId " +  systemApplicationId);

    }

    @And("^insert the compas individual id into registry table$")
    public void insert_the_compas_individual_id_into_registry_table() throws Throwable {
        // Express the Regexp above with the code you wish you had
        // Express the Regexp above with the code you wish you had
        JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());
        String applicationId = actualJsonObject.getAsJsonObject().get("applicationId").getAsString();
        log.debug("JUST Print the new values ");
        individualId = PreferencesDbHelper.retrieveIndividualID(applicationId);
        PreferencesDbHelper.insertPreferenceforMember(individualId);
        log.debug(individualId);
    }

    @When("^I invoke the update individual preference mapping API$")
    public void I_invoke_the_update_individual_preference_mapping_API() throws Throwable {
        // Express the Regexp above with the code you wish you had
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        restApiClient.setHeaders(headers);
        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));

        restApiClient.setRestUri(contextPath  + individualId + "/preferences");

        // log.debug(restApiClient.getResponseEntity().getBody());

        restApiClient.setHttpMethod(HttpMethod.PUT);
        restApiClient.execute();
        log.debug(restApiClient.getResponseEntity().getBody());
    }

    @And("^set the payload for invoking the individual preference \"([^\"]*)\"$")
    public void setPayloadForIndividualPreference(String fileName) throws Throwable {
        // Express the Regexp above with the code you wish you had
        JsonObject requestJson = JsonUtils.createJsonFromString(FileUtils.readFixture(fileName));
        restApiClient.setRequestBody(requestJson.toString());
    }

    @Then("^I expect the response to match  \"([^\"]*)\"$")
    public void I_expect_the_response_to_match(int httpStatusCode) throws Throwable {
        // Express the Regexp above with the code you wish you had
         assertEquals(restApiClient.getResponseEntity().getStatusCode().value(), httpStatusCode);
    }

    @And("^supply the compas individual id as \"([^\"]*)\"$")
    public void supply_the_compas_individual_id_as(String individualId) throws Throwable {
        // Express the Regexp above with the code you wish you had
        this.individualId = individualId;
    }

    @And("^expect to match the response body \"([^\"]*)\"$")
    public void expect_to_match_the_response_body(String expectedResponse) throws Throwable {
        // Express the Regexp above with the code you wish you had
        JSONAssert.assertEquals(FileUtils.readFixture(expectedResponse), restApiClient.getResponseEntity().getBody(), false);
    }

    @And("^response body is not null$")
    public void response_body_is_not_null() throws Throwable {
        // Express the Regexp above with the code you wish you had
        assertNotNull(restApiClient.getResponseEntity().getBody());
    }


    @Getter
    @Setter
    private static class Enrollments {

        List<Individual> individuals;

    }


    @Getter
    @Setter
    @Builder
    private static class Individual {
        private String applicationId;
        private String systemApplicationId;
        private String compasIndividualId;

    }
}
