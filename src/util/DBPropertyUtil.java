package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getPropertyString(String propertyFileName) {
        Properties properties = new Properties();
        String connectionString = "";

        try (FileInputStream fis = new FileInputStream("resources/" + propertyFileName)) {
            properties.load(fis);

            String hostname = properties.getProperty("hostname");
            String dbname = properties.getProperty("dbname");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            String port = properties.getProperty("port");

            connectionString = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname +
                    "?user=" + username + "&password=" + password;

        } catch (IOException e) {
            System.err.println("Error loading database properties: " + e.getMessage());
        }

        return connectionString;
    }
}
