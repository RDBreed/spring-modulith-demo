package eu.phaf.location.infrastructure.external.ip2location;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record IpGeolocationResponse(
        String countryCode,
        String countryName,
        String regionName,
        String cityName,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
