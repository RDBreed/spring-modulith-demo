package eu.phaf.news;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("jpa")
public class NewsRepositoryJpaDelegate implements NewsRepository {

    private final NewsJpaRepository newsJpaRepository;

    public NewsRepositoryJpaDelegate(NewsJpaRepository newsJpaRepository) {
        this.newsJpaRepository = newsJpaRepository;
    }

    @Override
    public void save(NewsService.News news, String country) {
        newsJpaRepository.save(toNewsEntity(news, country));
    }

    @Override
    public void deleteByCountry(String country) {
        newsJpaRepository.deleteByCountry(country);
    }

    @Override
    public List<NewsService.News> findByCountry(String country){
        return newsJpaRepository.findByCountry(country).stream().map(this::toNews).collect(Collectors.toList());
    }
    
    private NewsJpaRepository.NewsEntity toNewsEntity(NewsService.News news, String country) {
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
    
    private NewsService.News toNews(NewsJpaRepository.NewsEntity news) {
        return new NewsService.News(
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
