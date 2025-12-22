package eu.phaf.location;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Service
@ConfigurationPropertiesScan
public class Ip2LocationClientV1 {
    private final Ip2LocationProperties ip2LocationProperties;

    public Ip2LocationClientV1(Ip2LocationProperties ip2LocationProperties) {
        this.ip2LocationProperties = ip2LocationProperties;
    }

    public Mono<LocationService.Location> getLocationByIp(String ip) {
        String url = ip2LocationProperties.baseUrl() + "/?key=" + ip2LocationProperties.apiKey() + "&ip=" + ip + "&format=json";
        System.out.println("url: " + url);
        return WebClient.create(url)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(Ip2LocationClientV1::getIpGeolocationResponseMono)
                .map(ipGeolocationResponse -> new LocationService.Location(
                                ipGeolocationResponse.countryCode(),
                                ipGeolocationResponse.countryName(),
                                ipGeolocationResponse.regionName(),
                                ipGeolocationResponse.cityName(),
                                ipGeolocationResponse.latitude(),
                                ipGeolocationResponse.longitude()
                        )
                );
    }

    private static Mono<IpGeolocationResponse> getIpGeolocationResponseMono(ClientResponse clientResponse) {
        if (clientResponse.statusCode().is2xxSuccessful()) {
            return clientResponse.bodyToMono(IpGeolocationResponse.class);
        } else {
            return clientResponse.createError();
        }
    }

    @ConfigurationProperties("ip2location.v1")
    public record Ip2LocationProperties(String baseUrl, String apiKey) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record IpGeolocationResponse(
            String countryCode,
            String countryName,
            String regionName,
            String cityName,
            BigDecimal longitude,
            BigDecimal latitude) {

    }
}
