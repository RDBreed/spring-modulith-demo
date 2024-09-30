package eu.phaf.news;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<NewsRepository.NewsEntity, UUID> {

    List<NewsEntity> findByCountry(String country);

    @Transactional
    void deleteByCountry(String country);

    @Table
    @Entity
    public static class NewsEntity {
        // TODO is this something that is necessary? Let's dive into this in another post!
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;
        private String country;
        private String sourceName;
        private String author;
        private String title;
        @Lob
        private String description;
        private String url;

        @Lob
        private byte[] image;
        private OffsetDateTime publishedAt;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }

        public OffsetDateTime getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(OffsetDateTime publishedAt) {
            this.publishedAt = publishedAt;
        }
    }
}
