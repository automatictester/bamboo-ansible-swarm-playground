package uk.co.automatictester.bas.specs;

import uk.co.automatictester.bas.specs.config.BootstrapSwarmHostsPlanConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {

    private ServerConfig() {}

    public static String getServerUrl() {
        return getProperty("server.url");
    }

    public static String getAdminUser() {
        return getProperty("admin.user");
    }

    private static String getProperty(String key) {
        try (InputStream input = BootstrapSwarmHostsPlanConfig.class.getClassLoader().getResourceAsStream("bamboo.properties")) {
            Properties props = new Properties();
            props.load(input);
            return props.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
