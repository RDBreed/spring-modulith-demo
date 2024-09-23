package eu.phaf.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static eu.phaf.weather.OpenWeatherMapClientV3.WeatherResponse.CurrentWeather;
import static eu.phaf.weather.OpenWeatherMapClientV3.WeatherResponse.CurrentWeather.WeatherCondition;
import static eu.phaf.weather.WeatherService.Weather;

@Service
@ConfigurationPropertiesScan
public class OpenWeatherMapClientV3 {
    private final OpenWeatherMapProperties openWeatherMapProperties;

    public OpenWeatherMapClientV3(OpenWeatherMapProperties openWeatherMapProperties) {
        this.openWeatherMapProperties = openWeatherMapProperties;
    }

    public Mono<Weather> getWeatherByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude) {
        return WebClient.create(openWeatherMapProperties.baseUrl() + "/onecall?lat=" + latitude + "&lon=" + longitude + "&appid=" +
                                openWeatherMapProperties.apiKey())
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(OpenWeatherMapClientV3::getWeatherResponseMono)
                .map(OpenWeatherMapClientV3::getWeather);
    }


    private static Mono<WeatherResponse> getWeatherResponseMono(ClientResponse clientResponse) {
        if (clientResponse.statusCode().is2xxSuccessful()) {
            return clientResponse.bodyToMono(WeatherResponse.class);
        } else {
            return clientResponse.createError();
        }
    }

    private static Weather getWeather(WeatherResponse weatherResponse) {
        Optional<CurrentWeather> current = Optional.ofNullable(weatherResponse.current());
        return new Weather(
                current.map(CurrentWeather::temp).orElse(null),
                current.map(CurrentWeather::weather)
                        .map(List::stream)
                        .flatMap(Stream::findFirst)
                        .map(WeatherCondition::description)
                        .orElse(null)
        );
    }

    @ConfigurationProperties("openweathermap.v3")
    public record OpenWeatherMapProperties(String apiKey, String baseUrl) {

    }

    public record WeatherResponse(CurrentWeather current) {
        public record CurrentWeather(
                // temperature in Kelvin!
                BigDecimal temp,
                List<WeatherCondition> weather) {
            public record WeatherCondition(String description) {
            }
        }

    }
}
