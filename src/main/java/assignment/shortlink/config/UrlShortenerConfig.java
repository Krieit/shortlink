package assignment.shortlink.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for the URL shortening service.
 */
@Configuration
@ConfigurationProperties(prefix = "shortlink")
public class UrlShortenerConfig {

    private String baseUrl;
    private int shortUrlLength;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getShortUrlLength() {
        return shortUrlLength;
    }

    public void setShortUrlLength(int shortUrlLength) {
        this.shortUrlLength = shortUrlLength;
    }
}
