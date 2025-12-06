package eu.phaf.location;

import reactor.core.publisher.Mono;

public interface LocationService {
    Mono<LocationServiceIp2Location.Location> getLocationByIp(String ip);
}
