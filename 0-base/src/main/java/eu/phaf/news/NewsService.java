package eu.phaf.news;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class NewsService {
    private final NewsClientV2 newsClientV2;
    private final InMemoryNewsDatabase inMemoryNewsDatabase;

    public NewsService(NewsClientV2 newsClientV2, InMemoryNewsDatabase inMemoryNewsDatabase) {
        this.newsClientV2 = newsClientV2;
        this.inMemoryNewsDatabase = inMemoryNewsDatabase;
    }

    public Flux<News> getNewsByCountry(String country) {
        List<InMemoryNewsDatabase.News> latestNewsByCountry = inMemoryNewsDatabase.getLatestNewsByCountry(country);
        if (latestNewsByCountry.isEmpty()) {
            return newsClientV2.getNewsForCountry(country);
        }
        return Flux.fromStream(() ->
                latestNewsByCountry
                        .stream()
                        .map(NewsService::toNews)
        );
    }

    // Doing mapping here on purpose, let's see in another post how to improve.
    private static News toNews(InMemoryNewsDatabase.News news1) {
        return new News(
                news1.sourceName(),
                news1.author(),
                news1.title(),
                news1.description(),
                news1.url(),
                news1.image(),
                news1.publishedAt()
        );
    }

    public record News(String sourceName, String author, String title, String description, String url,
                       byte[] image, OffsetDateTime publishedAt) {

    }
}
