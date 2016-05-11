Feature: Update Individual Preference
  This feature defines the acceptance criteria for Update Individual Preference features.

  Background:
    Given preferences api up and running
    And the appenroll api is up and running


  Scenario Outline: 1 Update Individual Preference success conditions
    Given I invoke appEnroll service with "<Channel>" from "<appEnrollInputFile>"
    And I fetch the compas individual id based on appEnroll response
    And insert the compas individual id into registry table
    And set the payload for invoking the individual preference "<payLoad>"
    When  I invoke the update individual preference mapping API
    Then I expect the response to match  "<httpStatusCode>"
    And response body is not null



    Examples:
        |Channel|appEnrollInputFile                    |payLoad                            |httpStatusCode |Comments         |
        |DTC    |inputFiles/dtcPayLoad.json            |inputFiles/updateIndPrefTrue.json  |200            |Email Ind is NULL|
        |DTC    |inputFiles/dtcPayLoad_EmailOptIn.json |inputFiles/updateIndPrefFalse.json |200            |Email Opt Out    |


  Scenario Outline: 2 Update Individual Preference success conditions
    Given set the payload for invoking the individual preference "<payLoad>"
    And supply the compas individual id as "<individualId>"
    When  I invoke the update individual preference mapping API
    Then I expect the response to match  "<httpStatusCode>"
    And expect to match the response body "<expectedResponse>"
    And response body is not null


    Examples:
        |payLoad                            |individualId  |httpStatusCode|expectedResponse                                   |Comments          |
        |inputFiles/updateIndPrefTrue.json  |1300259031413 |200           |expectedFiles/expectedFile_1300259031413.json      |Email Opt is True |
        |inputFiles/updateIndPrefFalse.json |1300259031413 |200           |expectedFiles/expectedFile_1300259031413_False.json|Email Opt is False|




  Scenario Outline: 3 Update Individual Preference error conditions
    Given set the payload for invoking the individual preference "<payLoad>"
    And supply the compas individual id as "<individualId>"
    When  I invoke the update individual preference mapping API
    Then I expect the response to match  "<httpStatusCode>"

    Examples:
      |payLoad                             |individualId  |httpStatusCode |Comments           |
      |inputFiles/updateIndPrefTrue.json   |              |405            |Blank Individual Id|


  Scenario Outline: 4 Update Individual Preference error conditions
    Given set the payload for invoking the individual preference "<payLoad>"
    And supply the compas individual id as "<individualId>"
    When  I invoke the update individual preference mapping API
    Then I expect the response to match  "<httpStatusCode>"
    And expect to match the response body "<expectedResponse>"
    And response body is not null



    Examples:
      |payLoad                             |individualId  |httpStatusCode|expectedResponse                   |Comments          |
      |inputFiles/emptyPreferences.json    |1300259031413 |400           |expectedFiles/expectedFile_400.json|Empty Preferences |



