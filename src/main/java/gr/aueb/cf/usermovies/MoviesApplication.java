package gr.aueb.cf.usermovies;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationPath("/api")
public class MoviesApplication extends Application {

    private static Properties props;

    public static Properties getAppicationProperties(){
        if (props == null) props = loadProperties();
        return props;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = MoviesApplication.class.getResourceAsStream("/application.properties")) {
            props.load(is);
        } catch (IOException e) {
            System.out.println("Error. Could not load application properties");
            e.printStackTrace();
        }
        return props;
    }
}