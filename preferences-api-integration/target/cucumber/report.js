$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("features/GetIndividualMemberPreference.feature");
formatter.feature({
  "id": "get-member-individual-preference",
  "description": "This feature defines the acceptance criteria for Get Member Individual Preference features.",
  "name": "Get Member Individual Preference",
  "keyword": "Feature",
  "line": 1
});
formatter.scenarioOutline({
  "id": "get-member-individual-preference;1-get-member-individual-preference-success-conditions",
  "description": "",
  "name": "1 Get Member Individual Preference success conditions",
  "keyword": "Scenario Outline",
  "line": 4,
  "type": "scenario_outline"
});
formatter.step({
  "name": "I provide a individual id as \"\u003cindividualId\u003e\"",
  "keyword": "Given ",
  "line": 5
});
formatter.step({
  "name": "I invoke the get preferences API",
  "keyword": "When ",
  "line": 6
});
formatter.step({
  "name": "I expect the response as \"\u003cresponse\u003e\"",
  "keyword": "Then ",
  "line": 7
});
formatter.step({
  "name": "I expect the response to be \"\u003chttpStatus\u003e\"",
  "keyword": "And ",
  "line": 8
});
formatter.examples({
  "id": "get-member-individual-preference;1-get-member-individual-preference-success-conditions;",
  "description": "",
  "name": "",
  "keyword": "Examples",
  "line": 11,
  "rows": [
    {
      "id": "get-member-individual-preference;1-get-member-individual-preference-success-conditions;;1",
      "cells": [
        "individualId",
        "response",
        "httpStatus",
        "Comments"
      ],
      "line": 12
    },
    {
      "id": "get-member-individual-preference;1-get-member-individual-preference-success-conditions;;2",
      "cells": [
        "12345678",
        "expectedFiles/expectedFile_31352553411.json",
        "200",
        "CR12 - TC#0"
      ],
      "line": 13
    },
    {
      "id": "get-member-individual-preference;1-get-member-individual-preference-success-conditions;;3",
      "cells": [
        "35162625611",
        "expectedFiles/expectedFile_35162625611.json",
        "200",
        "CR12 - TC#1"
      ],
      "line": 14
    }
  ]
});
formatter.scenario({
  "id": "get-member-individual-preference;1-get-member-individual-preference-success-conditions;;2",
  "description": "",
  "name": "1 Get Member Individual Preference success conditions",
  "keyword": "Scenario Outline",
  "line": 13,
  "type": "scenario"
});
formatter.step({
  "name": "I provide a individual id as \"12345678\"",
  "keyword": "Given ",
  "line": 5,
  "matchedColumns": [
    0
  ]
});
formatter.step({
  "name": "I invoke the get preferences API",
  "keyword": "When ",
  "line": 6
});
formatter.step({
  "name": "I expect the response as \"expectedFiles/expectedFile_31352553411.json\"",
  "keyword": "Then ",
  "line": 7,
  "matchedColumns": [
    1
  ]
});
formatter.step({
  "name": "I expect the response to be \"200\"",
  "keyword": "And ",
  "line": 8,
  "matchedColumns": [
    2
  ]
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "id": "get-member-individual-preference;1-get-member-individual-preference-success-conditions;;3",
  "description": "",
  "name": "1 Get Member Individual Preference success conditions",
  "keyword": "Scenario Outline",
  "line": 14,
  "type": "scenario"
});
formatter.step({
  "name": "I provide a individual id as \"35162625611\"",
  "keyword": "Given ",
  "line": 5,
  "matchedColumns": [
    0
  ]
});
formatter.step({
  "name": "I invoke the get preferences API",
  "keyword": "When ",
  "line": 6
});
formatter.step({
  "name": "I expect the response as \"expectedFiles/expectedFile_35162625611.json\"",
  "keyword": "Then ",
  "line": 7,
  "matchedColumns": [
    1
  ]
});
formatter.step({
  "name": "I expect the response to be \"200\"",
  "keyword": "And ",
  "line": 8,
  "matchedColumns": [
    2
  ]
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});