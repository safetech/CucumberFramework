Feature: Get Individual Trans Preference
  This feature defines the acceptance criteria for Get Individual Trans Preference features.

  Background:
    Given preferencesApi is up and running


  Scenario Outline: 1 Get Individual Trans Preference success conditions
    Given I provide a systemName as "<systemName>"
    And I provide a ids as "<ids>"
    When  I invoke the get individual transactional preferences API
    Then I expect the response to match "<response>"
    And  I expect the response to match the "<httpStatus>"


  Examples:
    |systemName|ids             |response                                       |httpStatus |Comments                          |
    |COMPAS    |100             |expectedFiles/expectedFile_100.json            |200        |Valid COMPAS Id                   |
    |COMPAS    |100,300,500     |expectedFiles/expectedFile_100_300_500.json    |200        |Bulk valid COMPAS Id              |
    |COMPAS    |100,300,500,11  |expectedFiles/expectedFile_100_300_500_11.json |200        |Bulk Valid and Invalid COMPAS Id's|
    |COMPAS    |11              |expectedFiles/expectedFile_11.json             |200        |Invalid COMPAS ID                 |
    |COMPAS    |100,100         |expectedFiles/expectedFile_100.json            |200        |Duplicate valid COMPAS Id         |
#    |UCPS      |11              |expectedFiles/expectedFile_11.json             |200        |Invalid COMPAS ID  |




  Scenario Outline: 2 Get Individual Trans Preference error conditions
    Given I provide a systemName as "<systemName>"
    And I provide a ids as "<ids>"
    When  I invoke the get individual transactional preferences API
    Then I expect the response to match "<response>"
    And  I expect the response to match the "<httpStatus>"


    Examples:
      |systemName|ids     |response                                            |httpStatus |Comments              |
      |COMPAS    |        | expectedFiles/expectedFile_error400.json           |400        |Invalid COMPAS ID     |
      |SMART     | 100    | expectedFiles/expectedFile_error400_1.json         |400        |Invalid SystemName    |
      |NEW       | 100    | expectedFiles/expectedFile_error400_1.json         |400        |Invalid SystemName    |

