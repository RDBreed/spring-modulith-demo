package eu.phaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SpringBootConfiguration
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    @Profile("location")
    @ComponentScan(basePackageClasses = eu.phaf.location.NoOp.class)
    public static class LocationConfiguration {
    }

    @Configuration
    @Profile("news")
    @ComponentScan(basePackageClasses = eu.phaf.news.NoOp.class)
    public static class NewsConfiguration {
    }

    @Configuration
    @Profile("news-import")
    @ComponentScan(basePackageClasses = eu.phaf.news_import.batch.NoOp.class)
    public static class NewsImportConfiguration {
    }

    @Configuration
    @Profile("weather")
    @ComponentScan(basePackageClasses = eu.phaf.weather.NoOp.class)
    public static class WeatherConfiguration {
    }

    @Configuration
    @Profile("user")
    @ComponentScan(basePackageClasses = eu.phaf.user.NoOp.class)
    public static class UserConfiguration {

    }

}
