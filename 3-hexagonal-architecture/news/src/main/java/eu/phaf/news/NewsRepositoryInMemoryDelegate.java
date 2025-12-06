package eu.phaf.news;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("!jpa")
public class NewsRepositoryInMemoryDelegate implements NewsRepository {
    private final InMemoryNewsDatabase inMemoryNewsDatabase;

    public NewsRepositoryInMemoryDelegate(InMemoryNewsDatabase inMemoryNewsDatabase) {
        this.inMemoryNewsDatabase = inMemoryNewsDatabase;
    }

    @Override
    public void save(NewsService.News news, String country) {
        inMemoryNewsDatabase.saveNews(country, news);
    }

    @Override
    public void deleteByCountry(String country) {
        inMemoryNewsDatabase.deleteByCountry(country);
    }

    @Override
    public List<NewsService.News> findByCountry(String country) {
        return inMemoryNewsDatabase.getLatestNewsByCountry(country)
                .stream()
                .map(this::map)
                .toList();
    }

    private NewsService.News map(InMemoryNewsDatabase.News news) {
        return new NewsService.News(
                news.sourceName(),
                news.author(),
                news.title(),
                news.description(),
                news.url(),
                news.image(),
                news.publishedAt()
        );
    }
}
