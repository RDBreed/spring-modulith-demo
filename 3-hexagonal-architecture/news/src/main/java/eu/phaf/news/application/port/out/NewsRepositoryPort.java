package eu.phaf.news.application.port.out;

import eu.phaf.news.domain.News;

import java.util.List;

public interface NewsRepositoryPort {
    void save(News news, String country);

    void deleteByCountry(String country);

    List<News> findByCountry(String country);
}
