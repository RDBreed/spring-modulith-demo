package eu.phaf.news.infrastructure.external.newsapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("newsapi.v2")
public record NewsApiV2Properties(String baseUrl, String apiKey) {
}
