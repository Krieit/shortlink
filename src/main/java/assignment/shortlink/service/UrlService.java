package assignment.shortlink.service;

import assignment.shortlink.config.UrlShortenerConfig;
import assignment.shortlink.exception.UrlNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class UrlService {

    private final Map<String, String> urlMap = new HashMap<>();
    private final Map<String, String> reverseUrlMap = new HashMap<>();
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom random = new SecureRandom();

    private final String baseUrl;
    private final int shortUrlLength;

    @Autowired
    public UrlService(UrlShortenerConfig config) {
        this.baseUrl = config.getBaseUrl();
        this.shortUrlLength = config.getShortUrlLength();
    }

    public String encode(String url) {
        if (reverseUrlMap.containsKey(url)) {
            return baseUrl + reverseUrlMap.get(url);
        }
        String uniqueId = generateUniqueId();
        urlMap.put(uniqueId, url);
        reverseUrlMap.put(url, uniqueId);
        return baseUrl + uniqueId;
    }

    public String decode(String shortUrl) {
        String uniqueId = shortUrl.replace(baseUrl, "");
        String originalUrl = urlMap.get(uniqueId);
        if (originalUrl == null) {
            throw new UrlNotFoundException("URL not found for: " + shortUrl);
        }
        return originalUrl;
    }

    private String generateUniqueId() {
        String uniqueId;
        do {
            uniqueId = randomString();
        } while (urlMap.containsKey(uniqueId));
        return uniqueId;
    }

    private String randomString() {
        StringBuilder uniqueId = new StringBuilder(shortUrlLength);
        for (int i = 0; i < shortUrlLength; i++) {
            uniqueId.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        return uniqueId.toString();
    }

   }
