package eu.phaf.wiremockfixtures;

import com.github.tomakehurst.wiremock.client.MappingBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.havingExactly;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public final class LocationApiFixture {
    private LocationApiFixture() {
        // final
    }

    public static void success(String ip, String apiKey) {
        stubFor(stubGetIpGeolocation200(apiKey, ip, "json",
                """
                        {
                          "country_code": "US",
                          "city_name": "Mountain View",
                          "as": "Google LLC",
                          "ip": "8.8.8.8",
                          "latitude": 37.405992,
                          "country_name": "United States of America",
                          "region_name": "California",
                          "time_zone": "-07:00",
                          "asn": "15169",
                          "is_proxy": false,
                          "zip_code": "94043",
                          "longitude": -122.078515
                        }
                        """));
    }

    public static MappingBuilder stubGetIpGeolocation200(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull String ip, @jakarta.annotation.Nullable String format, String response) {
        MappingBuilder stub = get(urlPathEqualTo("/"))
                .withHeader("Accept", havingExactly("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                );

        stub = stub.withQueryParam("key", equalTo(key));
        stub = stub.withQueryParam("ip", equalTo(ip));
        if (format != null) {
            stub = stub.withQueryParam("format", equalTo(format));
        }

        return stub;
    }
}
