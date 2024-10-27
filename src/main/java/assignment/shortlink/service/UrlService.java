package assignment.shortlink.service;

import assignment.shortlink.exception.UrlNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class UrlService {

    private final Map<String, String> urlMap = new HashMap<>();
    private final Map<String, String> reverseUrlMap = new HashMap<>();
    private static final String BASE_URL = "http://short.est/";
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    public String encode(String url) {
        if (reverseUrlMap.containsKey(url)) {
            return BASE_URL + reverseUrlMap.get(url);
        }
        String uniqueId = generateUniqueId();
        urlMap.put(uniqueId, url);
        reverseUrlMap.put(url, uniqueId);
        return BASE_URL + uniqueId;
    }

    public String decode(String shortUrl) {
        String uniqueId = shortUrl.replace(BASE_URL, "");
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
        StringBuilder uniqueId = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            uniqueId.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        return uniqueId.toString();
    }
}
