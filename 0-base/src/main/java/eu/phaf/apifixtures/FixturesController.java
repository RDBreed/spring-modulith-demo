package eu.phaf.apifixtures;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("!production")
@RestController
@RequestMapping("/stubs")
public class FixturesController {

    @GetMapping(path = "/ip/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IpGeolocationResponse> getIpGeolocation(
            @RequestParam("key") String key,
            @RequestParam("ip") String ip,
            @RequestParam(value = "format", required = false) String format
    ) {

        IpGeolocationResponse response = new IpGeolocationResponse(
                "US",
                "Mountain View",
                "Google LLC",
                "8.8.8.8",
                37.405992,
                "United States of America",
                "California",
                "-07:00",
                "15169",
                false,
                "94043",
                -122.078515
        );

        return ResponseEntity.ok(response);
    }


    public record IpGeolocationResponse(
            String country_code,
            String city_name,
            String as,
            String ip,
            double latitude,
            String country_name,
            String region_name,
            String time_zone,
            String asn,
            boolean is_proxy,
            String zip_code,
            double longitude
    ) {
    }

    @GetMapping(path = "/news/top-headlines", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopHeadlinesResponse> getTopHeadlines(
            @RequestParam("apiKey") String apiKey,
            @RequestParam(value = "country", required = false) String country
    ) {

        TopHeadlinesResponse response = new TopHeadlinesResponse(
                "ok",
                36,
                List.of(
                        new Article(
                                new Source("fake", "FAKE news"),
                                "Nothing",
                                "This article is just empty",
                                null,
                                null,
                                null,
                                "2024-04-13T12:21:00Z",
                                null
                        ),
                        new Article(
                                new Source("usa-today", "USA Today"),
                                "USA TODAY",
                                "Roman art showing Helen of Troy discovered in Pompeii",
                                null,
                                "https://www.usatoday.com/",
                                null,
                                "2024-04-13T12:21:00Z",
                                null
                        )
                )
        );

        return ResponseEntity.ok(response);
    }

    public record TopHeadlinesResponse(
            String status,
            int totalResults,
            List<Article> articles
    ) {
    }

    public record Article(
            Source source,
            String author,
            String title,
            String description,
            String url,
            String urlToImage,
            String publishedAt,
            String content
    ) {
    }

    public record Source(
            String id,
            String name
    ) {
    }


    @GetMapping(path = "/weather/onecall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> getWeather(
            @RequestParam("lat") String lat,
            @RequestParam("lon") String lon,
            @RequestParam("appid") String appid
    ) {

        WeatherResponse response = new WeatherResponse(
                List.of(
                        new WeatherResponse.Alert(
                                1684952747L,
                                "Small Craft Advisory",
                                "NWS Philadelphia - Mount Holly",
                                1684988747L,
                                "Small Craft Advisory",
                                List.of("tags", "tags")
                        )
                ),
                new WeatherResponse.CurrentWeather(
                        1684926645L,
                        292.55,
                        10000,
                        0.16,
                        1014,
                        53,
                        292.87,
                        6.71,
                        1684929490L,
                        93,
                        290.69,
                        1684977332L,
                        List.of(new WeatherResponse.WeatherCondition("04d", "broken clouds", "Clouds", 803)),
                        89,
                        3.13
                ),
                "America/Chicago",
                -18000,
                List.of(),
                -94.04,
                List.of(),
                List.of(),
                33.44
        );

        return ResponseEntity.ok(response);
    }


    public record WeatherResponse(
            List<Alert> alerts,
            CurrentWeather current,
            String timezone,
            int timezone_offset,
            List<DailyWeather> daily,
            double lon,
            List<HourlyWeather> hourly,
            List<MinutelyWeather> minutely,
            double lat
    ) {
        public record Alert(
                long start,
                String description,
                String sender_name,
                long end,
                String event,
                List<String> tags
        ) {
        }

        public record CurrentWeather(
                long sunrise,
                double temp,
                int visibility,
                double uvi,
                int pressure,
                int clouds,
                double feels_like,
                double wind_gust,
                long dt,
                int wind_deg,
                double dew_point,
                long sunset,
                List<WeatherCondition> weather,
                int humidity,
                double wind_speed
        ) {
        }

        public record WeatherCondition(
                String icon,
                String description,
                String main,
                int id
        ) {
        }

        public record DailyWeather(
                long moonset,
                String summary,
                double rain,
                long sunrise,
                Temperature temp,
                double moon_phase,
                double uvi,
                long moonrise,
                int pressure,
                int clouds,
                FeelsLike feels_like,
                double wind_gust,
                long dt,
                double pop,
                int wind_deg,
                double dew_point,
                long sunset,
                List<WeatherCondition> weather,
                int humidity,
                double wind_speed
        ) {
        }

        public record Temperature(
                double min,
                double max,
                double eve,
                double night,
                double day,
                double morn
        ) {
        }

        public record FeelsLike(
                double eve,
                double night,
                double day,
                double morn
        ) {
        }

        public record HourlyWeather(
                double temp,
                int visibility,
                double uvi,
                int pressure,
                int clouds,
                double feels_like,
                double wind_gust,
                long dt,
                double pop,
                int wind_deg,
                double dew_point,
                List<WeatherCondition> weather,
                int humidity,
                double wind_speed
        ) {
        }

        public record MinutelyWeather(
                long dt,
                double precipitation
        ) {
        }

    }

}
