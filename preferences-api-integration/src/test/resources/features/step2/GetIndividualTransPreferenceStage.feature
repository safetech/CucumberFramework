@ignoreStage
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

#    update Valid systemName line 9 and valid ids on line 10



  Examples:
    |systemName|ids                                                     |response                                                                                 |httpStatus |Comments                          |
    |COMPAS    |9100003381491                                           |expectedFiles/expectedFile_9100003381491.json                                            |200        |Valid COMPAS Id                   |
    |COMPAS    |4000002397540,7500011841075,5600250741456               |expectedFiles/expectedFile_4000002397540_7500011841075_5600250741456.json                |200        |Bulk valid COMPAS Ids             |
    |COMPAS    |4000002397540,7500011841075,5600250741456,9500004182195 |expectedFiles/expectedFile_4000002397540_7500011841075_5600250741456_9500004182195.json  |200        |Bulk Valid and Invalid COMPAS Ids |
    |COMPAS    |11                                                      |expectedFiles/expectedFile_11.json                                                       |200        |Invalid COMPAS Id                 |
    |COMPAS    |100,100                                                 |expectedFiles/expectedFile_100.json                                                      |200        |Duplicate valid COMPAS Ids        |





  Scenario Outline: 2 Get Individual Trans Preference error conditions
    Given I provide a systemName as "<systemName>"
    And I provide a ids as "<ids>"
    When  I invoke the get individual transactional preferences API
    Then I expect the response to match "<response>"
    And  I expect the response to match the "<httpStatus>"

    #    update invalid systemName line 33 and invalid ids on line 35
#    rename API to service


    Examples:
      |systemName|ids     |response                                            |httpStatus |Comments              |
      |COMPAS    |        | expectedFiles/expectedFile_error400.json           |400        |Null COMPAS Id        |
      |SMART     | 100    | expectedFiles/expectedFile_error400_1.json         |400        |Invalid SystemName    |
      |NEW       | 100    | expectedFiles/expectedFile_error400_1.json         |400        |Invalid SystemName    |

