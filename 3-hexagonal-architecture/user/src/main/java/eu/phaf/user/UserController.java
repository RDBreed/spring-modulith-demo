package eu.phaf.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/user/info")
    public Mono<ResponseEntity<UserInfoResponse>> getUserInfo(String ip) {
        return userService.getUserInfo(ip)
                .map(userInformation -> new UserInfoResponse(new UserInfoResponse.LocationInfo(
                                userInformation.location().countryCode(),
                                userInformation.location().countryName(),
                                userInformation.location().regionName(),
                                userInformation.location().cityName(),
                                userInformation.location().latitude(),
                                userInformation.location().longitude()),
                                new UserInfoResponse.WeatherInfo(
                                        userInformation.weather().temperatureInCelsius(),
                                        userInformation.weather().weatherDescription()
                                ),
                                userInformation.news()
                                        .stream()
                                        .map(news -> new UserInfoResponse.NewsInfo(
                                                news.sourceName(),
                                                news.author(),
                                                news.title(),
                                                news.description(),
                                                news.url(),
                                                news.image(),
                                                news.publishedAt()
                                        ))
                                        .toList()
                        )
                )
                .map(ResponseEntity::ok);
    }

    public record UserInfoResponse(LocationInfo location, WeatherInfo weather, List<NewsInfo> news) {
        public record LocationInfo(String countryCode, String countryName, String regionName, String cityName,
                                   BigDecimal latitude, BigDecimal longitude) {

        }

        public record WeatherInfo(BigDecimal temperature, String weatherDescription) {

        }

        public record NewsInfo(String sourceName, String author, String title, String description, String url,
                               byte[] image, OffsetDateTime publishedAt) {

        }
    }

}
