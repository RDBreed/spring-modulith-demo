package eu.phaf.news.infrastructure.persistence.inmemory;

import eu.phaf.news.application.port.out.NewsRepositoryPort;
import eu.phaf.news.domain.News;

import java.util.List;

public class NewsRepositoryInMemoryAdapter implements NewsRepositoryPort {
    private final InMemoryNewsDatabase inMemoryNewsDatabase;

    public NewsRepositoryInMemoryAdapter(InMemoryNewsDatabase inMemoryNewsDatabase) {
        this.inMemoryNewsDatabase = inMemoryNewsDatabase;
    }

    @Override
    public void save(News news, String country) {
        inMemoryNewsDatabase.save(country, news);
    }

    @Override
    public void deleteByCountry(String country) {
        inMemoryNewsDatabase.deleteByCountry(country);
    }

    @Override
    public List<News> findByCountry(String country) {
        return inMemoryNewsDatabase.getLatestNewsByCountry(country)
                .stream()
                .map(this::map)
                .toList();
    }

    private News map(InMemoryNewsDatabase.NewsDatabase news) {
        return new News(
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
