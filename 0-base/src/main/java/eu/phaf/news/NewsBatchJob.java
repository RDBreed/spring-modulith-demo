package eu.phaf.news;

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

    private final InMemoryNewsDatabase inMemoryNewsDatabase;
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

    public NewsBatchJob(InMemoryNewsDatabase inMemoryNewsDatabase, NewsClientV2 newsClientV2) {
        this.inMemoryNewsDatabase = inMemoryNewsDatabase;
        this.newsClientV2 = newsClientV2;
    }

    @Scheduled(cron = "${batch.news.fixedScheduleCron}")
    public Flux<Tuple2<String, List<NewsService.News>>> getNewsForDefaultCountries() {
        LOGGER.info("Starting news batch job");
        return Flux.fromIterable(defaultCountries)
                .flatMap(country -> newsClientV2.getNewsForCountry(country)
                        .collectList()
                        .map(news -> Tuples.of(country, news)))
                .doOnNext(newsPerCountry -> inMemoryNewsDatabase.saveNews(newsPerCountry.getT1(), newsPerCountry.getT2()));
    }
}
