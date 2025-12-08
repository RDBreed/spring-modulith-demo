package eu.phaf.location.infrastructure.external.ip2location;

import eu.phaf.location.application.port.out.Ip2LocationPort;
import eu.phaf.location.domain.Location;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Ip2LocationClientV1 implements Ip2LocationPort {
    private final Ip2LocationProperties properties;
    private final WebClient webClient;

    public Ip2LocationClientV1(Ip2LocationProperties properties, WebClient webClient) {
        this.properties = properties;
        this.webClient = webClient;
    }

    @Override
    public Mono<Location> getLocationByIp(String ip) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", properties.apiKey())
                        .queryParam("ip", ip)
                        .queryParam("format", "json")
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(Ip2LocationClientV1::getIpGeolocationResponseMono)
                .map(r -> new Location(
                        r.countryCode(),
                        r.countryName(),
                        r.regionName(),
                        r.cityName(),
                        r.latitude(),
                        r.longitude()
                ));
    }

    private static Mono<IpGeolocationResponse> getIpGeolocationResponseMono(ClientResponse clientResponse) {
        if (clientResponse.statusCode().is2xxSuccessful()) {
            return clientResponse.bodyToMono(IpGeolocationResponse.class);
        } else {
            return clientResponse.createError();
        }
    }

}
