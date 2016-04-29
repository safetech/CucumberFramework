Feature: Get Member Individual Preference
  This feature defines the acceptance criteria for Get Member Individual Preference features.

  Scenario Outline: 1 Get Member Individual Preference success conditions
    Given I provide a ole ref id as "<oleRefId>"
    When  I invoke the get preferences API
    Then I expect the response as "<response>"
    And  I expect the response to be "<httpStatus>"


  Examples:
    |oleRefId     |response                                       |httpStatus |Comments    |
    |976          |expectedFiles/expectedFile_8300251212283.json  |200        |CR12 - TC#0 |




  Scenario Outline: 2 Get Member Individual Preference error conditions
    Given I provide a ole ref id as "<oleRefId>"
    When  I invoke the get preferences API
    Then I expect the valid response as "<response>"
    And  I expect the response to be "<httpStatus>"


    Examples:
      |oleRefId     |response                                       |httpStatus |Comments    |
      |1            |expectedFiles/expectedFile_1.json              |404        |CR12 - TC#0 |
