package assignment.shortlink.controller;

import assignment.shortlink.model.UrlDto;
import assignment.shortlink.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/encode")
    public ResponseEntity<UrlDto> encodeUrl(@RequestBody UrlDto urlDto) {
        String shortUrl = urlService.encode(urlDto.getUrl());
        return ResponseEntity.ok(new UrlDto(shortUrl));
    }

    @PostMapping("/decode")
    public ResponseEntity<UrlDto> decodeUrl(@RequestBody UrlDto urlDto) {
        String originalUrl = urlService.decode(urlDto.getUrl());
        return ResponseEntity.ok(new UrlDto(originalUrl));
    }
}
