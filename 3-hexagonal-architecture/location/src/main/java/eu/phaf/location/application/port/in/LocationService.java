package eu.phaf.location.application.port.in;

import eu.phaf.location.domain.Location;
import reactor.core.publisher.Mono;

public interface LocationService {
    Mono<Location> getLocationByIp(String ip);
}
