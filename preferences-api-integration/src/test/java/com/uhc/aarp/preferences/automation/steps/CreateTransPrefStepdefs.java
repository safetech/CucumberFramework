package com.uhc.aarp.preferences.automation.steps;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import com.uhc.aarp.automation.util.FileUtils;
import com.uhc.aarp.automation.util.JsonUtils;
import com.uhc.aarp.automation.util.PropertyUtils;
import com.uhc.aarp.automation.util.RestApiClient;
import com.uhc.aarp.preferences.automation.util.DateUtils;
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
    public Faker faker;
    private static final String contextPath = "/preferences-api/enrollments/";
    private static final String olePath = "/appEnroll-web/resources/applicationDetail/ship/medsupp/";

    private RestApiClient restApiClient;
    private SaveOleStepsDefs saveOleStepsDefs;

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

    @And("^the ole api is running$")
    public void the_ole_api_is_running() throws Throwable {
        restApiClient = new RestApiClient();
        restApiClient.setHostName(PropertyUtils.getProperty("ole.base.url"));
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

    }


    @And("^I expect response to match the \"([^\"]*)\"$")
    public void I_expect_tha_response_to_match_the(int httpStatusCode) throws Throwable {
        // Express the Regexp above with the code you wish you had
        assertEquals(restApiClient.getResponseEntity().getStatusCode().value(), httpStatusCode);
    }

    @When("^I invoke the create transactional preferences API$")
    public void I_invoke_the_create_transactional_preferences_API() throws Throwable {


        restApiClient.setHttpMethod(HttpMethod.POST);

        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
        restApiClient.execute();
    }

    @Then("^I expect response to match \"([^\"]*)\"$")
    public void I_expect_the_response_to_match(String expectedResult) throws Throwable {
        // Express the Regexp above with the code you wish you had
        JSONAssert.assertEquals(FileUtils.readFixture(expectedResult), restApiClient.getResponseEntity().getBody(), false);
    }


    @And("^I set the a ole ref id$")
    public void I_set_the_a_ole_ref_id()  throws Exception {
        // Express the Regexp above with the code you wish you had

        JsonObject actualJsonObject = JsonUtils.createJsonFromString(restApiClient.getResponseEntity().getBody());

        System.out.println("Get Provider Method" + restApiClient.getResponseEntity().getBody());

        String test = actualJsonObject.getAsJsonObject().get("applicationId").getAsString();

//       String individualId=  PreferencesDbHelper.retrieveIndividualID(test);
//
//        Gson gson = new Gson();
//
//                Enrollments enrollments = new Enrollments();
//
//List<Individual> individuals = new ArrayList<Individual>();
//        Individual.builder().applicationId("XYZ").compasIndividualId(individualId).




        restApiClient.setRestUri(contextPath + test + "?systemName=" + systemName);
    }



    @Given("^I start an app with dpsd \"([^\"]*)\" on \"([^\"]*)\" from \"([^\"]*)\"")
    public void I_start_an_app_with_dpsd_on_from_testFiles_dtcPayLoad_json(String dpsd, String channel, String payLoad) throws Throwable {
        faker = new Faker();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Content-Length", "6890");
        JsonObject requestJson = JsonUtils.createJsonFromString(FileUtils.readFixture(payLoad));

        requestJson.get("application").getAsJsonObject().addProperty("FirstName", faker.firstName());
        requestJson.get("application").getAsJsonObject().addProperty("LastName", faker.lastName());
        requestJson.get("application").getAsJsonObject().addProperty("DOB", DateUtils.getDobInCompasFormat(70));
        requestJson.get("application").getAsJsonObject().addProperty("ReqEffectiveDate", dpsd);
        requestJson.get("application").getAsJsonObject().addProperty("AARPMembershipNumber", faker.numerify("#########"+"1"));
        requestJson.get("application").getAsJsonObject().addProperty("MPBED", DateUtils.getFirstDayOfPasOrFutureMonths(-1));
        requestJson.get("application").getAsJsonObject().addProperty("MPAED", DateUtils.getFirstDayOfPasOrFutureMonths(-1));
        requestJson.get("application").getAsJsonObject().addProperty("AddressLine1", faker.streetAddress(false));

        restApiClient.setRequestBody(requestJson.toString());
        restApiClient.setHeaders(headers);
        restApiClient.setRestUri(olePath + "?channel=" + channel);
        restApiClient.setHttpMethod(HttpMethod.PUT);
        restApiClient.execute();

    }

    @Given("^I supply a ole ref id as \"([^\"]*)\"$")
    public void I_supply_a_ole_ref_id_as(String oleRefId) throws Throwable {
        // Express the Regexp above with the code you wish you had
        this.oleRefId = oleRefId;
    }

    @When("^I invoke create transactional preferences API$")
    public void I_invoke_create_transactional_preferences_API() throws Throwable {
        // Express the Regexp above with the code you wish you had
        restApiClient.setRestUri(contextPath + oleRefId + "?systemName=" + systemName);
        restApiClient.setHttpMethod(HttpMethod.POST);

        restApiClient.setHostName(PropertyUtils.getProperty("base.url"));
        restApiClient.execute();
    }


}
