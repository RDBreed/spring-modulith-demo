package eu.phaf.news.infrastructure.persistence.inmemory;

import eu.phaf.news.domain.News;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryNewsDatabase {
    // simple in memory database here...
    private final Map<String, List<NewsDatabase>> newsByCountry = new HashMap<>();

    // Doing returning of the database object on purpose!
    // Let's see in another post how to improve...
    public List<NewsDatabase> getLatestNewsByCountry(String country) {
        return newsByCountry.getOrDefault(country, new ArrayList<>());
    }

    public void save(String country, News news) {
        List<NewsDatabase> newsList = newsByCountry.getOrDefault(country, new ArrayList<>());
        newsList.add(toNews(news));
        newsByCountry.put(country, newsList);
    }

    private static NewsDatabase toNews(News news) {
        return new NewsDatabase(
                news.sourceName(),
                news.author(),
                news.title(),
                news.description(),
                news.url(),
                news.image(),
                news.publishedAt()
        );
    }

    public void deleteByCountry(String country) {
        newsByCountry.put(country, new ArrayList<>());
    }

    public record NewsDatabase(String sourceName,
                               String author,
                               String title,
                               String description,
                               String url,
                               byte[] image,
                               OffsetDateTime publishedAt) {

    }
}
