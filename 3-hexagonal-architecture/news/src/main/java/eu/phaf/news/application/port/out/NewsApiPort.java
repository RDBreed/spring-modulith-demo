package eu.phaf.news.application.port.out;

import eu.phaf.news.domain.News;
import reactor.core.publisher.Flux;

public interface NewsApiPort {
    Flux<News> getNewsForCountry(String country);
}
