package eu.phaf.location.infrastructure.external.ip2location;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ip2location.v1")
public record Ip2LocationProperties(String baseUrl, String apiKey) {
}
