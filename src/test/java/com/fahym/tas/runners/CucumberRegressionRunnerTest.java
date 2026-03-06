package com.fahym.tas.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.fahym.tas.steps,com.fahym.tas.hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber/cucumber.html, json:target/cucumber/cucumber.json")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@regression and not @wip")
public class CucumberRegressionRunnerTest {
}
