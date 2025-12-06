package eu.phaf.news;

import java.util.List;

public interface NewsRepository {
    void save(NewsService.News news, String country);
    void deleteByCountry(String country);

    List<NewsService.News> findByCountry(String country);
}
