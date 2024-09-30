package eu.phaf.news_import.batch;

import eu.phaf.news.NewsClientV2;
import eu.phaf.news.NewsRepository;
import eu.phaf.news.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

@Configuration
@EnableScheduling
public class NewsBatchJob {

    private final NewsRepository newsRepository;
    private final NewsClientV2 newsClientV2;
    private final Logger LOGGER = LoggerFactory.getLogger(NewsBatchJob.class);

    // Just some random countries here. For example the ones most frequently called.
    private static final List<String> defaultCountries = List.of(
            "US",
            "CA",
            "BE",
            "DE",
            "EL",
            "ES",
            "FR",
            "IT",
            "NL",
            "PT",
            "SE"

    );

    public NewsBatchJob(NewsRepository newsRepository, NewsClientV2 newsClientV2) {
        this.newsRepository = newsRepository;
        this.newsClientV2 = newsClientV2;
    }

    @Scheduled(cron = "${batch.news.fixedScheduleCron}")
    public Flux<Tuple2<String, List<NewsService.News>>> getNewsForDefaultCountries() {
        LOGGER.info("Starting news batch job");
        return Flux.fromIterable(defaultCountries)
                .flatMap(country -> newsClientV2.getNewsForCountry(country)
                        .collectList()
                        .map(news -> Tuples.of(country, news)))
                .doOnNext(newsPerCountry ->
                {
                    newsRepository.deleteByCountry(newsPerCountry.getT1());
                    newsPerCountry.getT2().stream()
                            .map(news -> toNewsEntity(news, newsPerCountry.getT1()))
                            .forEach(newsRepository::save);
                });
    }

    // not clean
    public NewsRepository.NewsEntity toNewsEntity(NewsService.News news, String country) {
        NewsRepository.NewsEntity newsEntity = new NewsRepository.NewsEntity();
        newsEntity.setAuthor(news.author());
        newsEntity.setCountry(country);
        newsEntity.setDescription(news.description());
        newsEntity.setImage(news.image());
        newsEntity.setTitle(news.title());
        newsEntity.setPublishedAt(news.publishedAt());
        newsEntity.setUrl(news.url());
        return newsEntity;
    }
}
