package eu.phaf;

import eu.phaf.news.InMemoryNewsDatabase;
import eu.phaf.wiremockfixtures.ImageFixture;
import eu.phaf.wiremockfixtures.LocationApiFixture;
import eu.phaf.wiremockfixtures.NewsOrgApiFixture;
import eu.phaf.wiremockfixtures.WeatherApiFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {
                Application.class,
                WiremockServer.class
        }
)
@ActiveProfiles({"default", "test"})
@AutoConfigureWebTestClient
@ExtendWith(WiremockServer.class)
public class TestApplication {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private InMemoryNewsDatabase inMemoryNewsDatabase;

    @Test
    public void shouldRunApplication() {
    }

    @Test
    public void shouldRunNewsBatch() {
        NewsOrgApiFixture.successAnyCountry("news_api_key");
        await()
                .untilAsserted(() -> assertThat(inMemoryNewsDatabase.getLatestNewsByCountry("NL")).isNotEmpty());
    }

    @Test
    public void shouldGiveUserInfo() {
        // given
        LocationApiFixture.success("1.2.3.3", "location_api_key");
        WeatherApiFixture.success("-122.078515", "37.405992", "weather_api_key");
        NewsOrgApiFixture.successAnyCountry("news_api_key");
        NewsOrgApiFixture.success(WiremockServer.wireMockServer().port(), "news_api_key", "US");
        ImageFixture.success();
        // when
        String responseBody = webTestClient
                .get()
                .uri("/api/user/info?ip=1.2.3.3")
                .exchange()
                // then
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThatJson(json(responseBody))
                .when(IGNORING_ARRAY_ORDER)
                .isEqualTo(json(FileUtils.readFileToString("assertions/responses/user-success.json")));
    }
}
