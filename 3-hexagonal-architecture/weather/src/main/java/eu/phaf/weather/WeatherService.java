package eu.phaf.weather;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WeatherService {
    private final OpenWeatherMapClientV3 openWeatherMapClientV3;

    public WeatherService(OpenWeatherMapClientV3 openWeatherMapClientV3) {
        this.openWeatherMapClientV3 = openWeatherMapClientV3;
    }

    public Mono<Weather> getWeatherByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude) {
        return openWeatherMapClientV3.getWeatherByLongitudeAndLatitude(longitude, latitude);
    }


    public record Weather(BigDecimal temperatureInKelvin, String weatherDescription) {
        public BigDecimal temperatureInCelsius() {
            return Optional.ofNullable(temperatureInKelvin).map(temp -> temp.subtract(new BigDecimal("273.15"))).orElse(null);
        }
    }
}
