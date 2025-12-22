package eu.phaf.news_import.batch;

import eu.phaf.news.application.port.out.NewsRepositoryPort;
import eu.phaf.news.application.port.in.NewsService;
import eu.phaf.news.domain.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

@Configuration
@EnableScheduling
public class NewsBatchJob {

    private final NewsRepositoryPort newsRepositoryPort;
    private final NewsService newsService;
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

    public NewsBatchJob(NewsRepositoryPort newsRepositoryPort, NewsService newsService) {
        this.newsRepositoryPort = newsRepositoryPort;
        this.newsService = newsService;
    }

    @Scheduled(cron = "${batch.news.fixedScheduleCron}")
    public Flux<Tuple2<String, List<News>>> getNewsForDefaultCountries() {
        LOGGER.info("Starting news batch job");
        return Flux.fromIterable(defaultCountries)
                .flatMap(country -> newsService.getNewsByCountry(country)
                        .collectList()
                        .map(news -> Tuples.of(country, news)))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(newsPerCountry ->
                {
                    newsRepositoryPort.deleteByCountry(newsPerCountry.getT1());
                    newsPerCountry.getT2()
                            .forEach(news -> newsRepositoryPort.save(news, newsPerCountry.getT1()));
                });
    }
}
