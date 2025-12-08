package eu.phaf.location.application.port.out;

import eu.phaf.location.domain.Location;
import reactor.core.publisher.Mono;

public interface Ip2LocationPort {
    Mono<Location> getLocationByIp(String ip);
}
