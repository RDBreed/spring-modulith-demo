package eu.phaf.news.infrastructure.external.newsapi;

import java.time.OffsetDateTime;
import java.util.List;

public record NewsResponse(String status, Integer totalResults, List<NewsArticle> articles) {
    public record NewsArticle(NewsSource source, String author, String title, String description, String url,
                              String urlToImage, OffsetDateTime publishedAt, String content) {
    }

    public record NewsSource(String id, String name) {
    }
}
