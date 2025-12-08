package eu.phaf.location.application.usecase;

import eu.phaf.location.application.port.in.LocationService;
import eu.phaf.location.application.port.out.Ip2LocationPort;
import eu.phaf.location.domain.Location;
import reactor.core.publisher.Mono;

public class GetLocationByIpService implements LocationService {
    private final Ip2LocationPort ip2LocationPort;

    public GetLocationByIpService(Ip2LocationPort ip2LocationPort) {
        this.ip2LocationPort = ip2LocationPort;
    }

    @Override
    public Mono<Location> getLocationByIp(String ip) {
        return ip2LocationPort.getLocationByIp(ip);
    }

}
