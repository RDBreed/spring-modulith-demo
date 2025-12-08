package eu.phaf.location.domain;

import java.math.BigDecimal;

public record Location(String countryCode, String countryName, String regionName, String cityName,
                       BigDecimal latitude, BigDecimal longitude) {

}
