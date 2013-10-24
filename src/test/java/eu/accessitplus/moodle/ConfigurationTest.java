package eu.accessitplus.moodle;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class ConfigurationTest {

    public static final String TEST_CONFIGURATION_FILE = "./src/test/resources/test-configuration.xml";

    @Test
    public void shouldReturnDefaultValues() {
        Configuration configuration = new Configuration();
        assertThat(configuration.getContinueLabel(), is("Continue"));
        assertThat(configuration.getInitialSeq(), is(2000l));
    }

    @Test
    public void shouldOverrideDefaultValuesWithCustomFile() {
        Configuration notDefaultConf = new Configuration(TEST_CONFIGURATION_FILE);
        assertThat(notDefaultConf.getContinueLabel(), is("Kontynuuj"));
        assertThat(notDefaultConf.getInitialSeq(), is(1l));
    }
}
