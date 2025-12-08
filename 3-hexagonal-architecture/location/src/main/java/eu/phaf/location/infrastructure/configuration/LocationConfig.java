package eu.phaf.location.infrastructure.configuration;

import eu.phaf.location.NoOp;
import eu.phaf.location.application.port.in.LocationService;
import eu.phaf.location.application.port.out.Ip2LocationPort;
import eu.phaf.location.application.usecase.GetLocationByIpService;
import eu.phaf.location.infrastructure.external.ip2location.Ip2LocationClientV1;
import eu.phaf.location.infrastructure.external.ip2location.Ip2LocationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = NoOp.class)
public class LocationConfig {

    @Bean
    public LocationService locationService(Ip2LocationProperties properties) {
        return new GetLocationByIpService(ip2LocationPort(properties));
    }

    @Bean
    public Ip2LocationPort ip2LocationPort(Ip2LocationProperties properties) {
        return new Ip2LocationClientV1(properties, ip2LocationWebClient(properties));
    }

    @Bean
    public WebClient ip2LocationWebClient(Ip2LocationProperties properties) {
        return WebClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }
}
