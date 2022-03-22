package settings;

import java.io.IOException;
import java.io.InputStream;

public class Configuration {

    private static final String CONFIGURATION_FILE = "/application.properties";
    private static final java.util.Properties properties;

    static {
        properties = new java.util.Properties();
        try (InputStream inputStream = Configuration.class.getResourceAsStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file" + CONFIGURATION_FILE, e);
        }
    }

    public static String getFromProperties(String key) {
        return ((System.getProperty(key) == null) ? properties.getProperty(key) : System.getProperty(key));
    }

    public Configuration() {

    }
}
