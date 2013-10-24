package eu.accessitplus.moodle;

import java.io.File;
import static eu.accessitplus.FilesAssert.exists;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest {

    Configuration configuration;
    
    @Before
    public void loadTestConfiguration() {
        configuration = new Configuration(ConfigurationTest.TEST_CONFIGURATION_FILE);
    }
    
    @Test
    public void shouldConvertGivenMbzFile() throws IOException{
        //when
        App.main(new String[] { ConfigurationTest.TEST_CONFIGURATION_FILE });
        
        //then
        assertThat(configuration.getOutputFile(), exists());
    }

    @After
    public void cleanup() {
        File outputFile = configuration.getOutputFile();
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }
    
}
