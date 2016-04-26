package com.uhc.aarp.preferences.automation.util;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue="com.uhc.aarp.eligibility.automation.steps",
        features = {"src/test/resources/"},
        format={"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class CucumberRunnerTest {

}