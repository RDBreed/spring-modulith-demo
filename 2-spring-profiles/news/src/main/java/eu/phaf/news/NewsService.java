package eu.phaf.news;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;

@Service
public class NewsService {
    private final NewsClientV2 newsClientV2;

    public NewsService(NewsClientV2 newsClientV2) {
        this.newsClientV2 = newsClientV2;
    }

    public Flux<News> getNewsByCountry(String country) {
        return newsClientV2.getNewsForCountry(country);
    }

    public record News(String sourceName, String author, String title, String description, String url,
                       byte[] image, OffsetDateTime publishedAt) {

    }
}
