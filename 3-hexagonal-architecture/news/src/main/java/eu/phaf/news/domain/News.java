package eu.phaf.news.domain;

import java.time.OffsetDateTime;

public record News(String sourceName, String author, String title, String description, String url,
                   byte[] image, OffsetDateTime publishedAt) {

}
