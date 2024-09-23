package eu.phaf.location;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class LocationService {
    private final Ip2LocationClientV1 ip2LocationClientV1;

    public LocationService(Ip2LocationClientV1 ip2LocationClientV1) {
        this.ip2LocationClientV1 = ip2LocationClientV1;
    }

    public Mono<Location> getLocationByIp(String ip) {
        return ip2LocationClientV1.getLocationByIp(ip);
    }

    public record Location(String countryCode, String countryName, String regionName, String cityName,
                           BigDecimal latitude, BigDecimal longitude) {

    }
}
