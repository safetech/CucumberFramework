package com.uhc.aarp.preferences.automation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue="com.uhc.aarp.preferences.automation.steps",
        features = {"src/test/resources/"},
        format={"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class CucumberRunnerTest {

}