Feature: Create Preference
  This feature defines the acceptance criteria for Create Transactional Preference features.

  Background:
    Given preferences Api is up and running


  Scenario Outline: 1 Create Trans Preference success conditions
    Given I supply a ole ref id as "<oleRefId>"
    And I provide an systemName as "<systemName>"
    And I provide a json pay load as "<inputFile>"
    When  I invoke the create transactional preferences API
#    Then I expect the response to match "<response>"
    And  I expect response to match the "<httpStatus>"


  Examples:
    |oleRefId    |systemName|inputFile                 |response                                       |httpStatus |Comments       |
    |1492-J232-10|COMPAS    |inputFiles/inputFile1.json|expectedFiles/expectedFile_100.json            |201        |Email Ind false|
    |1492-J602-10|COMPAS    |inputFiles/inputFile2.json|expectedFiles/expectedFile_100.json            |201        |Email Ind true |



  Scenario Outline: 2 Create Trans Preference error conditions
    Given I supply a ole ref id as "<oleRefId>"
    And I provide an systemName as "<systemName>"
    And I provide a json pay load as "<inputFile>"
    When  I invoke the create transactional preferences API
    Then I expect response to match "<response>"
    And  I expect response to match the "<httpStatus>"


    Examples:
      |oleRefId    |systemName|inputFile                 |response                                                     |httpStatus |Comments       |
      |1492-JU90-10|TEST      |inputFiles/inputFile1.json|expectedFiles/expectedFile_createPref_Error1.json            |400        |Valid COMPAS Id|

