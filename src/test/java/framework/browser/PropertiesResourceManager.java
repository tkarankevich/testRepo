package framework.browser;

import framework.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

final class PropertiesResourceManager {
    private Properties properties = new Properties();
    private static final String resourceName = "configuration.properties";

    PropertiesResourceManager() {
        InputStream propertiesStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (propertiesStream != null) {
            try {
                properties.load(propertiesStream);
                propertiesStream.close();
            } catch (IOException e) {
                Logger.getLogger().info("Can't read properties");
            }
        } else {
            Logger.getLogger().info("Resource configuration not found");
        }
    }

    String getSystemProperty(String key){
        return properties.getProperty(key);
    }
}
