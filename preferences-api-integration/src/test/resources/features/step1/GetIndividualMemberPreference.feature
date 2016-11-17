@ignoreTest
Feature: Get Member Individual Preference
  This feature defines the acceptance criteria for Get Member Individual Preference features.

  Scenario Outline: 1 Get Member Individual Preference success conditions
    Given I provide a ole ref id as "<oleRefId>"
    When  I invoke the get preferences API
    Then I expect the response as "<response>"
    And  I expect the response to be "<httpStatus>"
#    Update the status code and line 7 with valid response code


  Examples:
    |oleRefId     |response                                       |httpStatus |Comments         |Env  |
    |14A0-AF96-89 |expectedFiles/expectedFile_300259053003.json   |200        |Success HTTP 200 |TEST|
#   |1492-C764-86 |expectedFiles/expectedFile_9200259036092.json  |200        |Success HTTP 200 |STAGE|





  Scenario Outline: 2 Get Member Individual Preference error conditions
    Given I provide a ole ref id as "<invalidOleRefId>"
    When  I invoke the get preferences API
    Then I expect the valid response as "<response>"
    And  I expect the response to be "<httpStatus>"
#    Update the status code
    #    Fix the error response line 22


    Examples:
      |invalidOleRefId     |response                                       |httpStatus |Comments        |
      |11A0-AF96-89        |expectedFiles/expectedFile_1.json              |404        |Failure HTTP 404|
