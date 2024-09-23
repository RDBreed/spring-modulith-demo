package eu.phaf.wiremockfixtures;

import com.github.tomakehurst.wiremock.client.MappingBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.havingExactly;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public final class WeatherApiFixture {
    private WeatherApiFixture() {
        // final
    }

    public static void success(String longitude, String latitude, String apiKey) {
        stubFor(stubGetWeather200(latitude, longitude, apiKey,
                getAResponse()
        ));
    }

    private static MappingBuilder stubGetWeather200(@jakarta.annotation.Nonnull String lat, @jakarta.annotation.Nonnull String lon, @jakarta.annotation.Nonnull String appid, String response) {
        MappingBuilder stub = get(urlPathEqualTo("/onecall"))
                .withHeader("Accept", havingExactly("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                );

        stub = stub.withQueryParam("lat", equalTo(lat));
        stub = stub.withQueryParam("lon", equalTo(lon));
        stub = stub.withQueryParam("appid", equalTo(appid));

        return stub;
    }

    private static String getAResponse() {
        return """
                {
                  "alerts": [
                    {
                      "start": 1684952747,
                      "description": "...SMALL CRAFT ADVISORY REMAINS IN EFFECT FROM 5 PM THIS AFTERNOON TO 3 AM EST FRIDAY... * WHAT...North winds 15 to 20 kt with gusts up to 25 kt and seas 3 to 5 ft expected. * WHERE...Coastal waters from Little Egg Inlet to Great Egg Inlet NJ out 20 nm, Coastal waters from Great Egg Inlet to Cape May NJ out 20 nm and Coastal waters from Manasquan Inlet to Little Egg Inlet NJ out 20 nm. * WHEN...From 5 PM this afternoon to 3 AM EST Friday. * IMPACTS...Conditions will be hazardous to small craft.\\n",
                      "sender_name": "NWS Philadelphia - Mount Holly (New Jersey, Delaware, Southeastern Pennsylvania)",
                      "end": 1684988747,
                      "event": "Small Craft Advisory",
                      "tags": [
                        "tags",
                        "tags"
                      ]
                    },
                    {
                      "start": 1684952747,
                      "description": "...SMALL CRAFT ADVISORY REMAINS IN EFFECT FROM 5 PM THIS AFTERNOON TO 3 AM EST FRIDAY... * WHAT...North winds 15 to 20 kt with gusts up to 25 kt and seas 3 to 5 ft expected. * WHERE...Coastal waters from Little Egg Inlet to Great Egg Inlet NJ out 20 nm, Coastal waters from Great Egg Inlet to Cape May NJ out 20 nm and Coastal waters from Manasquan Inlet to Little Egg Inlet NJ out 20 nm. * WHEN...From 5 PM this afternoon to 3 AM EST Friday. * IMPACTS...Conditions will be hazardous to small craft.\\n",
                      "sender_name": "NWS Philadelphia - Mount Holly (New Jersey, Delaware, Southeastern Pennsylvania)",
                      "end": 1684988747,
                      "event": "Small Craft Advisory",
                      "tags": [
                        "tags",
                        "tags"
                      ]
                    }
                  ],
                  "current": {
                    "sunrise": 1684926645,
                    "temp": 292.55,
                    "visibility": 10000,
                    "uvi": 0.16,
                    "pressure": 1014,
                    "clouds": 53,
                    "feels_like": 292.87,
                    "wind_gust": 6.71,
                    "dt": 1684929490,
                    "wind_deg": 93,
                    "dew_point": 290.69,
                    "sunset": 1684977332,
                    "weather": [
                      {
                        "icon": "04d",
                        "description": "broken clouds",
                        "main": "Clouds",
                        "id": 803
                      },
                      {
                        "icon": "04d",
                        "description": "broken clouds",
                        "main": "Clouds",
                        "id": 803
                      }
                    ],
                    "humidity": 89,
                    "wind_speed": 3.13
                  },
                  "timezone": "America/Chicago",
                  "timezone_offset": -18000,
                  "daily": [
                    {
                      "moonset": 1684905480,
                      "summary": "Expect a day of partly cloudy with rain",
                      "rain": 0.15,
                      "sunrise": 1684926645,
                      "temp": {
                        "min": 290.69,
                        "max": 300.35,
                        "eve": 297.51,
                        "night": 291.45,
                        "day": 299.03,
                        "morn": 292.55
                      },
                      "moon_phase": 0.16,
                      "uvi": 9.23,
                      "moonrise": 1684941060,
                      "pressure": 1016,
                      "clouds": 92,
                      "feels_like": {
                        "eve": 297.86,
                        "night": 291.37,
                        "day": 299.21,
                        "morn": 292.87
                      },
                      "wind_gust": 8.92,
                      "dt": 1684951200,
                      "pop": 0.47,
                      "wind_deg": 76,
                      "dew_point": 290.48,
                      "sunset": 1684977332,
                      "weather": [
                        {
                          "icon": "04d",
                          "description": "broken clouds",
                          "main": "Clouds",
                          "id": 803
                        },
                        {
                          "icon": "04d",
                          "description": "broken clouds",
                          "main": "Clouds",
                          "id": 803
                        }
                      ],
                      "humidity": 59,
                      "wind_speed": 3.98
                    },
                    {
                      "moonset": 1684905480,
                      "summary": "Expect a day of partly cloudy with rain",
                      "rain": 0.15,
                      "sunrise": 1684926645,
                      "temp": {
                        "min": 290.69,
                        "max": 300.35,
                        "eve": 297.51,
                        "night": 291.45,
                        "day": 299.03,
                        "morn": 292.55
                      },
                      "moon_phase": 0.16,
                      "uvi": 9.23,
                      "moonrise": 1684941060,
                      "pressure": 1016,
                      "clouds": 92,
                      "feels_like": {
                        "eve": 297.86,
                        "night": 291.37,
                        "day": 299.21,
                        "morn": 292.87
                      },
                      "wind_gust": 8.92,
                      "dt": 1684951200,
                      "pop": 0.47,
                      "wind_deg": 76,
                      "dew_point": 290.48,
                      "sunset": 1684977332,
                      "weather": [
                        {
                          "icon": "04d",
                          "description": "broken clouds",
                          "main": "Clouds",
                          "id": 803
                        },
                        {
                          "icon": "04d",
                          "description": "broken clouds",
                          "main": "Clouds",
                          "id": 803
                        }
                      ],
                      "humidity": 59,
                      "wind_speed": 3.98
                    }
                  ],
                  "lon": -94.04,
                  "hourly": [
                    {
                      "temp": 292.01,
                      "visibility": 10000,
                      "uvi": 0,
                      "pressure": 1014,
                      "clouds": 54,
                      "feels_like": 292.33,
                      "wind_gust": 5.88,
                      "dt": 1684926000,
                      "pop": 0.15,
                      "wind_deg": 86,
                      "dew_point": 290.51,
                      "weather": [
                        {
                          "icon": "04d",
                          "description": "broken clouds",
                          "main": "Clouds",
                          "id": 803
                        },
                        {
                          "icon": "04d",
                          "description": "broken clouds",
                          "main": "Clouds",
                          "id": 803
                        }
                      ],
                      "humidity": 91,
                      "wind_speed": 2.58
                    },
                    {
                      "temp": 292.01,
                      "visibility": 10000,
                      "uvi": 0,
                      "pressure": 1014,
                      "clouds": 54,
                      "feels_like": 292.33,
                      "wind_gust": 5.88,
                      "dt": 1684926000,
                      "pop": 0.15,
                      "wind_deg": 86,
                      "dew_point": 290.51,
                      "weather": [
                        {
                          "icon": "04d",
                          "description": "broken clouds",
                          "main": "Clouds",
                          "id": 803
                        },
                        {
                          "icon": "04d",
                          "description": "broken clouds",
                          "main": "Clouds",
                          "id": 803
                        }
                      ],
                      "humidity": 91,
                      "wind_speed": 2.58
                    }
                  ],
                  "minutely": [
                    {
                      "dt": 1684929540,
                      "precipitation": 0
                    },
                    {
                      "dt": 1684929540,
                      "precipitation": 0
                    }
                  ],
                  "lat": 33.44
                }
                """;
    }
}
