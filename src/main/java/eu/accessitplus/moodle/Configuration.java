package eu.accessitplus.moodle;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

public class Configuration {

    public static final String INPUT_FILE_KEY = "input.file";
    public static final String OUTPUT_FILE_KEY = "output.file";
    public static final String CONTINUE_LABEL_KEY = "continue.label";
    public static final String INITIAL_SEQ_KEY = "initial.seq";
    public static final String TEMP_DIR_KEY = "temp.dir";

    private final static String CONFIGURATION_FILENAME = "configuration.xml";
    Properties configuration;

    public Configuration() {
        loadDefaultConfiguration();
    }

    public Configuration(String fileName) {
        loadCustomConfiguration(fileName);
    }

    public File getInputFile() {
        return new File(configuration.getProperty(INPUT_FILE_KEY));
    }

    public File getOutputFile() {
        return new File(configuration.getProperty(OUTPUT_FILE_KEY));
    }

    public String getContinueLabel() {
        return configuration.getProperty(CONTINUE_LABEL_KEY, "Continue");
    }

    public File getTempDir() {
        String customTempDir = configuration.getProperty(TEMP_DIR_KEY);
        if (customTempDir == null) {
            return FileUtils.getTempDirectory();
        }
        return new File(customTempDir);
    }

    public long getInitialSeq() {
        String initialSeqString = configuration.getProperty(INITIAL_SEQ_KEY, "1");
        return Long.parseLong(initialSeqString);
    }

    private void loadDefaultConfiguration() {
        this.configuration = new Properties();
        try {
            InputStream propertiesStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIGURATION_FILENAME);
            configuration.loadFromXML(propertiesStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    private void loadCustomConfiguration(String configurationFile) {
        this.configuration = new Properties();
        try {
            InputStream propertiesStream = new FileInputStream(new File(configurationFile));
            configuration.loadFromXML(propertiesStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Configuration:\n");
        sb.append(INPUT_FILE_KEY);
        sb.append(":");
        sb.append(getInputFile());
        sb.append("\n");
        sb.append(OUTPUT_FILE_KEY);
        sb.append(":");
        sb.append(getOutputFile());
        sb.append("\n");
        sb.append(CONTINUE_LABEL_KEY);
        sb.append(":");
        sb.append(getContinueLabel());
        sb.append("\n");
        sb.append(INITIAL_SEQ_KEY);
        sb.append(":");
        sb.append(getInitialSeq());
        return sb.toString();
    }
}
