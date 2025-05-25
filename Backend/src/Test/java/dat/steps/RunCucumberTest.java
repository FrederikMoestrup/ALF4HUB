package dat.steps;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("resources/feature/notification_management/notification_management.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "dat.steps")
public class RunCucumberTest
{
}
