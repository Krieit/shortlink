package assignment.shortlink.controller;

import assignment.shortlink.model.UrlDto;
import assignment.shortlink.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for URL encoding and decoding.
 */
@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    /**
     * Encodes a URL to a shortened URL.
     *
     * @param urlDto the URL to encode
     * @return the shortened URL
     */
    @PostMapping("/encode")
    public ResponseEntity<UrlDto> encodeUrl(@RequestBody UrlDto urlDto) {
        String shortUrl = urlService.encode(urlDto.getUrl());
        return ResponseEntity.ok(new UrlDto(shortUrl));
    }

    /**
     * Decodes a shortened URL to its original URL.
     *
     * @param urlDto the shortened URL to decode
     * @return the original URL
     */
    @PostMapping("/decode")
    public ResponseEntity<UrlDto> decodeUrl(@RequestBody UrlDto urlDto) {
        String originalUrl = urlService.decode(urlDto.getUrl());
        return ResponseEntity.ok(new UrlDto(originalUrl));
    }
}
