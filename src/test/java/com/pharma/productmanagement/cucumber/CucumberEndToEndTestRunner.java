package com.pharma.productmanagement.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverters;
import org.junit.runner.RunWith;

@CucumberOptions(plugin = { "pretty" },features = "classpath:features")
@XStreamConverters(@XStreamConverter(LocalDateCon.class))
@RunWith(Cucumber.class)
public class CucumberEndToEndTestRunner {


}
