package com.uhc.aarp.preferences.automation.steps;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.uhc.aarp.automation.util.FileUtils;
import com.uhc.aarp.automation.util.JsonUtils;
import com.uhc.aarp.automation.util.PropertyUtils;
import com.uhc.aarp.automation.util.RestApiClient;
import com.uhc.aarp.preferences.automation.dao.PreferencesDbHelper;
import com.uhc.aarp.preferences.automation.util.DateUtils;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lnampal on 4/29/16.
 */

@Slf4j
public class UpdateIndividualStepdefs {


    private String systemName;
    private String individualId;
    private static final String contextPath = "/preferences-api/enrollments/individuals";
    private static final String olePath = "/appEnroll-web/resources/applicationDetail/ship/medsupp/";
    private Faker faker;

    private RestApiClient restApiClient;

    @Given("^preferences Api up and running$")
    public void preferencesApi_up_and_running()  {
        // Express the Regexp above with the code you wish you had
        restApiClient = new RestApiClient();
        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
    }



    @And("^I supply a systemName as \"([^\"]*)\"$")
    public void I_supply_a_systemName_as(String systemName) throws Throwable {
        // Express the Regexp above with the code you wish you had
        this.systemName = systemName;
    }

    @Then("^I expect response to match  \"([^\"]*)\"$")
    public void I_expect_response_to_match(int httpStatusCode) throws Throwable {
        // Express the Regexp above with the code you wish you had
        assertEquals(restApiClient.getResponseEntity().getStatusCode().value(), httpStatusCode);


    }

    @Given("^I supply the payload as \"([^\"]*)\"$")
    public void I_supply_the_payload_as(String fileName) throws Throwable {
        // Express the Regexp above with the code you wish you had
        JsonObject requestJson = JsonUtils.createJsonFromString(FileUtils.readFixture(fileName));
        restApiClient.setRequestBody(requestJson.toString());


        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
        restApiClient.setRestUri(contextPath  + "?systemName=" + systemName);
    }

    @And("^I expect to match the file \"([^\"]*)\"$")
    public void I_expect_to_match_the_file(String fileName) throws Throwable {
        // Express the Regexp above with the code you wish you had
        JSONAssert.assertEquals(FileUtils.readFixture(fileName),restApiClient.getResponseEntity().getBody(),false);
    }



    @Given("^I invoke appEnroll with \"([^\"]*)\" from \"([^\"]*)\"")
    public void I_start_an_app_with_dpsd_on_from_testFiles_dtcPayLoad_json(String channel, String payLoad) throws Throwable {
        faker = new Faker();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Content-Length", "6890");
        JsonObject requestJson = JsonUtils.createJsonFromString(FileUtils.readFixture(payLoad));

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



    @And("^I set the json payload based on appEnroll response$")
    public void I_set_the_a_ole_reference_id() throws Throwable {
        // Express the Regexp above with the code you wish you had
        JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());

        System.out.println("Get Provider Method" + restApiClient.getResponseEntity().getBody());

     //   String oleRefId = actualJsonObject.getAsJsonObject().get("applicationId").getAsString();


//        String applicationId = actualJsonObject.getAsJsonObject().get("applicationId").getAsString();
//        String systemApplicationId = actualJsonObject.getAsJsonObject().get("systemApplicationId").getAsString();
          String applicationId = JsonPath.read(actualJsonObject.toString(), "$.applicationId");
          String systemApplicationId = JsonPath.read(actualJsonObject.toString(), "$.systemApplicationId");

        log.debug("Print the JSON PAth value for ApplicationId " +  applicationId);

        log.debug("Print the JSON PAth value for ApplicationId " +  systemApplicationId);

        String individualId = PreferencesDbHelper.retrieveIndividualID(applicationId);

        Gson gson = new Gson();

        Enrollments enrollments = new Enrollments();
        List<Individual> individuals = new ArrayList<Individual>();
        individuals.add(Individual.builder().systemApplicationId(systemApplicationId).compasIndividualId(individualId).applicationId(applicationId).build());
        enrollments.setIndividuals(individuals);


        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
//        restApiClient.setRestUri(contextPath  + "?systemName=" + systemName);
        restApiClient.setRequestBody(gson.toJson(enrollments));


    }



    @And("^insert the compas individual id in registry$")
    public void insert_the_compas_individual_id_in_registry() throws Throwable {
        // Express the Regexp above with the code you wish you had
        JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());
        String applicationId = actualJsonObject.getAsJsonObject().get("applicationId").getAsString();

        String individualId = PreferencesDbHelper.retrieveIndividualID(applicationId);
        PreferencesDbHelper.insertPreferenceforMember(individualId);
        log.debug(individualId);
    }


    @When("^I invoke the update individual API$")
    public void I_invoke_the_update_individual_API() throws Throwable {
        // Express the Regexp above with the code you wish you had
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        restApiClient.setHeaders(headers);
        restApiClient.setRestUri(contextPath  + "?systemName=" + systemName);

       // log.debug(restApiClient.getResponseEntity().getBody());
        restApiClient.setHttpMethod(HttpMethod.PUT);
        restApiClient.execute();
        log.debug(restApiClient.getResponseEntity().getBody());
    }


    @And("^I provide json pay load as \"([^\"]*)\"$")
    public void I_provide_json_pay_load_as(String fileName) throws Throwable {
        // Express the Regexp above with the code you wish you had
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        restApiClient.setHeaders(headers);
        restApiClient.setHeaders(headers);
        JsonObject requestJson = JsonUtils.createJsonFromString(FileUtils.readFixture(fileName));
        restApiClient.setRequestBody(requestJson.toString());
    }

    @When("^I invoke the POST create transactional preferences API$")
    public void invokeCreatePref() throws Throwable {
        // Express the Regexp above with the code you wish you had
        restApiClient.setHttpMethod(HttpMethod.POST);

        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
        restApiClient.execute();
    }

    @And("^the ole api is up and running$")
    public void the_ole_api_is_running() throws Throwable {
        restApiClient = new RestApiClient();
        restApiClient.setHostName(PropertyUtils.getProperty("ole.base.url"));
    }

    @And("^the response body is not null$")
    public void the_response_body_is_not_null()  {
        // Express the Regexp above with the code you wish you had
        assertNotNull(restApiClient.getResponseEntity().getBody());
        JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());
        String message = JsonPath.read(actualJsonObject.toString(), "$.preferences.*.[0].error").toString();

        assertFalse(message.contains("errorMessage"));
//        assertFalse(message.contains("[{\"errorMessage\":\"Enrollment not found [applicationId=*]\"}]"));
//        assertNull("The error message should be null", message);


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
