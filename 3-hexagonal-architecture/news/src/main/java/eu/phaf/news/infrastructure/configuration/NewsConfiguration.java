package eu.phaf.news.infrastructure.configuration;

import eu.phaf.news.NoOp;
import eu.phaf.news.application.port.in.NewsService;
import eu.phaf.news.application.port.out.NewsApiPort;
import eu.phaf.news.application.port.out.NewsRepositoryPort;
import eu.phaf.news.application.usecase.GetNewsByCountryService;
import eu.phaf.news.infrastructure.external.newsapi.NewsApiClientV2;
import eu.phaf.news.infrastructure.external.newsapi.NewsApiV2Properties;
import eu.phaf.news.infrastructure.persistence.inmemory.InMemoryNewsDatabase;
import eu.phaf.news.infrastructure.persistence.inmemory.NewsRepositoryInMemoryAdapter;
import eu.phaf.news.infrastructure.persistence.jpa.NewsJpaRepository;
import eu.phaf.news.infrastructure.persistence.jpa.NewsRepositoryJpaAdapter;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = NoOp.class)
public class NewsConfiguration {

    @Bean
    public WebClient newsApiWebClient(NewsApiV2Properties props) {
        return WebClient.builder()
                .baseUrl(props.baseUrl())
                .build();
    }

    @Bean
    public NewsService newsService(
            NewsRepositoryPort repo,
            NewsApiPort api
    ) {
        return new GetNewsByCountryService(api, repo);
    }

    @Bean
    public NewsApiPort newsClient(NewsApiV2Properties props) {
        return new NewsApiClientV2(props, newsApiWebClient(props));
    }

    @Bean
    @Profile("!jpa")
    public NewsRepositoryPort newsRepositoryInMemoryAdapter(InMemoryNewsDatabase inMemoryNewsDatabase) {
        return new NewsRepositoryInMemoryAdapter(inMemoryNewsDatabase);
    }

    @Bean
    @Profile("jpa")
    public NewsRepositoryJpaAdapter newsRepositoryJpaAdapter(NewsJpaRepository newsJpaRepository) {
        return new NewsRepositoryJpaAdapter(newsJpaRepository);
    }

}
