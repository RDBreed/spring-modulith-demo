package eu.phaf.news;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class NewsServiceNewsApi implements NewsService {
    private final NewsApiClientV2 newsClientV2;
    private final NewsRepository newsRepository;

    public NewsServiceNewsApi(NewsApiClientV2 newsClientV2, NewsRepository newsRepository) {
        this.newsClientV2 = newsClientV2;
        this.newsRepository = newsRepository;
    }

    @Override
    public Flux<News> getNewsByCountry(String country) {
        return Flux.fromIterable(newsRepository.findByCountry(country))
                .switchIfEmpty(newsClientV2.getNewsForCountry(country));
    }

}
