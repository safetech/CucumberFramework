
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
      |2016-09-01|DTC    |inputFiles/dtcPayLoad.json |COMPAS    |inputFiles/inputFile1.json|201        |Email Ind false|
      |2016-09-01|DTC    |inputFiles/dtcPayLoad.json |COMPAS    |inputFiles/inputFile2.json|201        |Email Ind true |





  Scenario Outline: 2 Create Trans Preference error conditions
    Given I supply a ole ref id as "<oleRefId>"
    And I provide an systemName as "<systemName>"
    And I provide a json pay load as "<inputFile>"
    When  I invoke create transactional preferences API
    Then I expect response to match "<response>"
    And  I expect response to match the "<httpStatus>"

#    rename API to service

    Examples:
      |oleRefId    |systemName|inputFile                 |response                                         |httpStatus |Comments             |
      |149C-4513-75|COMPAS    |inputFiles/inputFile1.json|expectedFiles/expectedFile_error409.json         |409        |Member already exists|

