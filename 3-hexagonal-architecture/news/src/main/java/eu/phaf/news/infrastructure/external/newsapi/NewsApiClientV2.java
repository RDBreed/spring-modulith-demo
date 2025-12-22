package eu.phaf.news.infrastructure.external.newsapi;

import eu.phaf.news.application.port.out.NewsApiPort;
import eu.phaf.news.domain.News;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class NewsApiClientV2 implements NewsApiPort {
    private final NewsApiV2Properties newsApiV2Properties;
    private final WebClient webClient;

    public NewsApiClientV2(NewsApiV2Properties newsApiV2Properties, WebClient webClient) {
        this.newsApiV2Properties = newsApiV2Properties;
        this.webClient = webClient;
    }

    @Override
    public Flux<News> getNewsForCountry(String country) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/top-headlines")
                        .queryParam("country", country)
                        .queryParam("apiKey", newsApiV2Properties.apiKey())
                        .build()).accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(NewsApiClientV2::getNewsResponseMono)
                .flatMapIterable(NewsResponse::articles)
                .flatMap(newsResponse -> getImage(newsResponse.urlToImage())
                        .map(imageInBytes -> new News(
                                Optional.ofNullable(newsResponse.source())
                                        .map(NewsResponse.NewsSource::name)
                                        .orElse(""),
                                newsResponse.author(),
                                newsResponse.title(),
                                newsResponse.description(),
                                newsResponse.url(),
                                imageInBytes,
                                newsResponse.publishedAt()))
                );
    }

    private static Mono<NewsResponse> getNewsResponseMono(ClientResponse clientResponse) {
        if (clientResponse.statusCode().is2xxSuccessful()) {
            return clientResponse.bodyToMono(NewsResponse.class);
        } else {
            return clientResponse.createError();
        }
    }

    private Mono<byte[]> getImage(String path) {
        if (path != null && !path.isEmpty()) {
            return WebClient.create(path)
                    .get()
                    .accept(MediaType.IMAGE_JPEG)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .onErrorResume(throwable -> Mono.just(new byte[0]));
        }
        return Mono.just(new byte[0]);
    }


}
