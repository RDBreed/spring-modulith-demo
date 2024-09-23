package eu.phaf.news;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryNewsDatabase {
    // simple in memory database here...
    private final Map<String, List<News>> newsByCountry = new HashMap<>();

    // Doing returning of the database object on purpose!
    // Let's see in another post how to improve...
    public List<News> getLatestNewsByCountry(String country) {
        return newsByCountry.getOrDefault(country, new ArrayList<>());
    }

    public void saveNews(String country, List<NewsService.News> news) {
        newsByCountry.put(country, toNews(news));
    }

    private static List<News> toNews(List<NewsService.News> news) {
        return news
                .stream()
                .map(InMemoryNewsDatabase::toNews)
                .toList();
    }

    private static News toNews(NewsService.News news1) {
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

    public record News(String sourceName,
                       String author,
                       String title,
                       String description,
                       String url,
                       byte[] image,
                       OffsetDateTime publishedAt) {

    }
}
