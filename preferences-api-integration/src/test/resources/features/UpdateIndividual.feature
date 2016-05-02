Feature: Update Individual Mapping
  This feature defines the acceptance criteria for Update Individual Mapping features.

  Background:
    Given preferences Api up and running
    And the ole api is up and running


  Scenario Outline: 1 Update Individual Mapping success conditions
    Given I supply the payload as "<inputFile>"
    And I supply a systemName as "<systemName>"
    When I invoke the update individual API
    Then I expect response to match  "<httpStatus>"


  Examples:
    |inputFile                         |systemName|httpStatus |Comments        |
    |inputFiles/updateIndividual1.json |COMPAS    |200        |Success Scenario|


#    |1492-J712-10|COMPAS    |inputFiles/inputFile2.json|expectedFiles/expectedFile_100.json            |201        |Email Ind true |


  Scenario Outline: 2 Update Individual Mapping success conditions
    Given I invoke appEnroll with dpsd "<DPSD>" on "<Channel>" from "<appEnrollInputFile>"
    And I supply a systemName as "<systemName>"
#    And I provide json pay load as "<inputFile>"
    And I set the json payload based on appEnroll response
    And insert the compas individual id in registry
    When  I invoke the update individual API
    Then I expect response to match  "<httpStatusCode>"



    Examples:
      |DPSD      |Channel|appEnrollInputFile        |inputFile                         |systemName|httpStatusCode |Comments        |
      |2016-06-01|DTC    |inputFiles/dtcPayLoad.json|inputFiles/updateIndividual1.json |COMPAS    |200            |Success Scenario|




  Scenario Outline: 3 Update Individual Mapping error conditions
    Given I supply the payload as "<inputFile>"
    And I supply a systemName as "<systemName>"
    When  I invoke the update individual API
    Then I expect response to match  "<httpStatus>"
    And I expect to match the file "<response>"


    Examples:
      |inputFile                         |systemName  |httpStatus |response                                   |Comments                                        |
      |inputFiles/updateIndividual1.json |COMP        |400        |expectedFiles/expectedFile_error400_1.json |Error Scenario - Invalid systemName             |
      |inputFiles/updateIndividual3.json |COMPAS      |500        |expectedFiles/expectedFile_error500.json   |Error   Scenario - Missing appId in JSON Payload|


