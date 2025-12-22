package eu.phaf.news.application.port.in;

import eu.phaf.news.domain.News;
import reactor.core.publisher.Flux;

public interface NewsService {
    Flux<News> getNewsByCountry(String country);
}
