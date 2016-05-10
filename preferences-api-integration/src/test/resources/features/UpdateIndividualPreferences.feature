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
#    And the response body is not null



    Examples:
        |Channel|appEnrollInputFile                    |payLoad                     |httpStatusCode |Comments         |
        |DTC    |inputFiles/dtcPayLoad.json            |inputFiles/updateIndPref1.json  |200            |Email Ind is NULL|
#        |DTC    |inputFiles/dtcPayLoad_EmailOptIn.json |                                |200            |Email Opt In YES |
#        |DTC    |inputFiles/dtcPayLoad_EmailOptOut.json|                                |200            |Email Opt In No  |


  Scenario Outline: 2 Update Individual Preference success conditions
    Given set the payload for invoking the individual preference "<payLoad>"
    And supply the compas individual id as "<individualId>"
    When  I invoke the update individual preference mapping API
    Then I expect the response to match  "<httpStatusCode>"
    And expect to match the response body "<expectedResponse>"
  #    And the response body is not null


    Examples:
        |payLoad                            |individualId  |httpStatusCode|expectedResponse                                   |Comments          |
        |inputFiles/updateIndPrefTrue.json  |1300259031413 |200           |expectedFiles/expectedFile_1300259031413.json      |Email Opt is True |
        |inputFiles/updateIndPrefFalse.json |1300259031413 |200           |expectedFiles/expectedFile_1300259031413_False.json|Email Opt is False|




  Scenario Outline: 3 Update Individual Preference success conditions
    Given set the payload for invoking the individual preference "<payLoad>"
    And supply the compas individual id as "<individualId>"
    When  I invoke the update individual preference mapping API
    Then I expect the response to match  "<httpStatusCode>"
#    And expect to match the response body "<expectedResponse>"



    Examples:
      |payLoad                             |individualId  |httpStatusCode|expectedResponse                                   |Comments          |
      |inputFiles/updateIndPrefTrue.json   |              |405           |expectedFiles/expectedFile_1300259031413.json      |Email Opt is True |
      |inputFiles/emptyPreferences.json    |1300259031413 |400           |expectedFiles/expectedFile_1300259031413_False.json|Empty Preferences |
#      |inputFiles/updateIndPrefTrue1.json  |1300259031413 |400           |expectedFiles/expectedFile_1300259031413_False.json|Email Opt is False|

#  1300259031413


