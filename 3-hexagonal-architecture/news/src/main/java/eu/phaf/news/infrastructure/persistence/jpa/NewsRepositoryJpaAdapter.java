package eu.phaf.news.infrastructure.persistence.jpa;

import eu.phaf.news.application.port.out.NewsRepositoryPort;
import eu.phaf.news.domain.News;

import java.util.List;
import java.util.stream.Collectors;

public class NewsRepositoryJpaAdapter implements NewsRepositoryPort {

    private final NewsJpaRepository newsJpaRepository;

    public NewsRepositoryJpaAdapter(NewsJpaRepository newsJpaRepository) {
        this.newsJpaRepository = newsJpaRepository;
    }

    @Override
    public void save(News news, String country) {
        newsJpaRepository.save(toNewsEntity(news, country));
    }

    @Override
    public void deleteByCountry(String country) {
        newsJpaRepository.deleteByCountry(country);
    }

    @Override
    public List<News> findByCountry(String country) {
        return newsJpaRepository.findByCountry(country).stream().map(this::toNews).collect(Collectors.toList());
    }

    private NewsJpaRepository.NewsEntity toNewsEntity(News news, String country) {
        NewsJpaRepository.NewsEntity newsEntity = new NewsJpaRepository.NewsEntity();
        newsEntity.setAuthor(news.author());
        newsEntity.setCountry(country);
        newsEntity.setDescription(news.description());
        newsEntity.setImage(news.image());
        newsEntity.setTitle(news.title());
        newsEntity.setPublishedAt(news.publishedAt());
        newsEntity.setUrl(news.url());
        return newsEntity;
    }

    private News toNews(NewsJpaRepository.NewsEntity news) {
        return new News(
                "newsApi",
                news.getAuthor(),
                news.getTitle(),
                news.getDescription(),
                news.getUrl(),
                news.getImage(),
                news.getPublishedAt()
        );
    }
}
