package eu.phaf.news.application.usecase;

import eu.phaf.news.application.port.in.NewsService;
import eu.phaf.news.application.port.out.NewsApiPort;
import eu.phaf.news.application.port.out.NewsRepositoryPort;
import eu.phaf.news.domain.News;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetNewsByCountryService implements NewsService {
    private final NewsApiPort newsApiPort;
    private final NewsRepositoryPort newsRepositoryPort;

    public GetNewsByCountryService(NewsApiPort newsApiPort, NewsRepositoryPort newsRepositoryPort) {
        this.newsApiPort = newsApiPort;
        this.newsRepositoryPort = newsRepositoryPort;
    }

    @Override
    public Flux<News> getNewsByCountry(String country) {
        return Flux.fromIterable(newsRepositoryPort.findByCountry(country))
                .switchIfEmpty(newsApiPort.getNewsForCountry(country));
    }

}
