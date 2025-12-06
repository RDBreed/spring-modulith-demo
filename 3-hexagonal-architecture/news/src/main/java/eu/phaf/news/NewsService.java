package eu.phaf.news;

import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;

public interface NewsService {
    Flux<NewsService.News> getNewsByCountry(String country);

    record News(String sourceName, String author, String title, String description, String url,
                       byte[] image, OffsetDateTime publishedAt) {

    }
}
