
Feature: Create Preference
  This feature defines the acceptance criteria for Create Transactional Preference features.

  Background:
    Given preferences Api is up and running
    And the ole api is running


  Scenario Outline: 1 Create Trans Preference success conditions
    Given I start an app with dpsd "<DPSD>" on "<Channel>" from "<appEnrollInputFile>"
    And I provide an systemName as "<systemName>"
    And I provide a json pay load as "<inputFile>"
    And I set the a ole ref id
    When  I invoke the create transactional preferences API
    And  I expect response to match the "<httpStatus>"


  Examples:
    |DPSD      |Channel|appEnrollInputFile         |systemName|inputFile                 |httpStatus |Comments       |
    |2016-06-01|DTC    |inputFiles/dtcPayLoad.json |COMPAS    |inputFiles/inputFile1.json|201        |Email Ind false|
#    |2016-06-01|DTC    |inputFiles/dtcPayLoad.json |COMPAS    |inputFiles/inputFile2.json|201        |Email Ind true |

#    |1492-J712-10|COMPAS    |inputFiles/inputFile2.json|expectedFiles/expectedFile_100.json            |201        |Email Ind true |



  Scenario Outline: 2 Create Trans Preference error conditions
    Given I supply a ole ref id as "<oleRefId>"
    And I provide an systemName as "<systemName>"
    And I provide a json pay load as "<inputFile>"
    When  I invoke create transactional preferences API
    Then I expect response to match "<response>"
    And  I expect response to match the "<httpStatus>"


    Examples:
      |oleRefId    |systemName|inputFile                 |response                                         |httpStatus |Comments             |
      |1492-JU90-10|TEST      |inputFiles/inputFile1.json|expectedFiles/expectedFile_createPref_Error1.json|400        |Valid COMPAS Id      |
      |1493-5C15-51|COMPAS    |inputFiles/inputFile1.json|expectedFiles/expectedFile_error409.json         |409        |Member already exists|

