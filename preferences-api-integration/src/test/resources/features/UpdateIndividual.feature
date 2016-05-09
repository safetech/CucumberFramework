Feature: Update Individual Mapping
  This feature defines the acceptance criteria for Update Individual Mapping features.

  Background:
    Given preferences Api up and running
    And the ole api is up and running


  Scenario Outline: 1 Update Individual Mapping success conditions
    Given I invoke appEnroll with "<Channel>" from "<appEnrollInputFile>"
    And I supply a systemName as "<systemName>"
    And I set the json payload based on appEnroll response
    And insert the compas individual id in registry
    When  I invoke the update individual API
    Then I expect response to match  "<httpStatusCode>"
    And the response body is not null



    Examples:
      |DPSD      |Channel|appEnrollInputFile                    |systemName|httpStatusCode |Comments         |
      |2016-06-01|DTC    |inputFiles/dtcPayLoad.json            |COMPAS    |200            |Email Ind is NULL|
      |2016-06-01|DTC    |inputFiles/dtcPayLoad_EmailOptIn.json |COMPAS    |200            |Email Opt In YES |
      |2016-06-01|DTC    |inputFiles/dtcPayLoad_EmailOptOut.json|COMPAS    |200            |Email Opt In No  |




  Scenario Outline: 2 Update Individual Mapping error conditions
    Given I supply the payload as "<inputFile>"
    And I supply a systemName as "<systemName>"
    When  I invoke the update individual API
    Then I expect response to match  "<httpStatus>"
    And I expect to match the file "<response>"


    Examples:
      |inputFile                         |systemName  |httpStatus |response                                   |Comments                                        |
      |inputFiles/updateIndividual1.json |COMP        |400        |expectedFiles/expectedFile_error400_1.json |Error Scenario - Invalid systemName             |
      |inputFiles/updateIndividual3.json |COMPAS      |500        |expectedFiles/expectedFile_error500.json   |Error   Scenario - Missing appId in JSON Payload|
      |inputFiles/bulkRequest.json       |COMPAS      |500        |expectedFiles/expectedFile_bulkError.json  |Error   Scenario - Request bulk >25             |



  Scenario Outline: 3 Update Individual Mapping Bulk Valid Request Scenario
    Given I invoke the appEnroll with "<Channel>" from "<appEnrollInputFile>" and "<bulkRequestCount>"
    And I supply a systemName as "<systemName>"
    When  I invoke the update individual API
    Then I expect response to match  "<httpStatusCode>"
    And the response body is not null



    Examples:
      |DPSD      |Channel|appEnrollInputFile                    |bulkRequestCount|systemName|httpStatusCode |Comments         |
      |2016-06-01|DTC    |inputFiles/dtcPayLoad.json            |25              |COMPAS    |200            |Email Ind is NULL|
      |2016-06-01|DTC    |inputFiles/dtcPayLoad_EmailOptIn.json |25              |COMPAS    |500            |Email Opt In YES |
      |2016-06-01|DTC    |inputFiles/dtcPayLoad_EmailOptOut.json|25              |COMPAS    |500            |Email Opt In No  |




  Scenario Outline: 4 Update Individual Mapping Bulk Mix of Valid and Invalid Request data Scenario
    Given I invoke appEnroll service with "<Channel>" from "<appEnrollInputFile>" and "<bulkRequestCount>"
    And I supply a systemName as "<systemName>"
    When  I invoke the update individual API
    Then I expect response to match  "<httpStatusCode>"
    And the response body should contain error status



    Examples:
       |Channel|appEnrollInputFile                    |bulkRequestCount|systemName|httpStatusCode |Comments         |
       |DTC    |inputFiles/dtcPayLoad.json            |25              |COMPAS    |200            |Email Ind is NULL|




