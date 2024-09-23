package eu.phaf.news;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@ConfigurationPropertiesScan
public class NewsClientV2 {
    private final NewsApiV2Properties newsApiV2Properties;

    public NewsClientV2(NewsApiV2Properties newsApiV2Properties) {
        this.newsApiV2Properties = newsApiV2Properties;
    }

    public Flux<NewsService.News> getNewsForCountry(String country) {
        return WebClient.create(newsApiV2Properties.baseUrl() + "/top-headlines?country=" + country + "&apiKey=" + newsApiV2Properties.apiKey())
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(NewsClientV2::getNewsResponseMono)
                .flatMapIterable(NewsResponse::articles)
                .flatMap(newsResponse -> getImage(newsResponse.urlToImage())
                        .map(imageInBytes -> new NewsService.News(
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


    @ConfigurationProperties("newsapi.v2")
    public record NewsApiV2Properties(String baseUrl, String apiKey) {
    }

    public record NewsResponse(String status, Integer totalResults, List<NewsArticle> articles) {
        public record NewsArticle(NewsSource source, String author, String title, String description, String url,
                                  String urlToImage, OffsetDateTime publishedAt, String content) {
        }

        public record NewsSource(String id, String name) {
        }
    }
}
