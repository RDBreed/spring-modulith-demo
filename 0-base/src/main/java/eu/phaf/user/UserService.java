package eu.phaf.user;

import eu.phaf.location.LocationService;
import eu.phaf.location.LocationService.Location;
import eu.phaf.news.NewsService;
import eu.phaf.news.NewsService.News;
import eu.phaf.weather.WeatherService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static eu.phaf.weather.WeatherService.Weather;

@Service
public class UserService {
    private final NewsService newsService;
    private final LocationService locationService;
    private final WeatherService weatherService;

    public UserService(NewsService newsService, LocationService locationService, WeatherService weatherService) {
        this.newsService = newsService;
        this.locationService = locationService;
        this.weatherService = weatherService;
    }

    public Mono<User> getUserInfo(String ip) {
        return locationService.getLocationByIp(ip)
                .zipWhen(location ->
                                weatherService.getWeatherByLongitudeAndLatitude(location.longitude(), location.latitude())
                                        .zipWith(newsService.getNewsByCountry(location.countryCode()).collectList()),
                        (location, locationResults) -> new User(location, locationResults.getT1(), locationResults.getT2())
                );
    }

    public record User(Location location, Weather weather, List<News> news) {
    }
}
