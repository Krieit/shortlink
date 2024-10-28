package assignment.shortlink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testEncode() throws Exception {
        mockMvc.perform(post("/api/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"https://example.com/library/react\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(matchesPattern("http://short\\.est/[A-Za-z0-9]{6}")));
    }
    @Test
    public void testEncodeSameUrl() throws Exception {
        String url = "https://example.com/library/react";
        MvcResult firstResult = mockMvc.perform(post("/api/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"" + url + "\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String firstResponse = firstResult.getResponse().getContentAsString();
        MvcResult secondResult = mockMvc.perform(post("/api/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"" + url + "\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String secondResponse = secondResult.getResponse().getContentAsString();
        assert firstResponse.equals(secondResponse);
    }
    @Test
    public void testDecode() throws Exception {
        // First encode the URL
        MvcResult encodeResult = mockMvc.perform(post("/api/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"https://example.com/library/react\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String encodeResponse = encodeResult.getResponse().getContentAsString();
        System.out.println("Encode Response: " + encodeResponse);
        // Extract the short URL from the response
        String shortUrl = extractShortUrlFromResponse(encodeResponse);
        // Now decode it
        MvcResult decodeResult = mockMvc.perform(post("/api/decode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"" + shortUrl + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("https://example.com/library/react"))
                .andReturn();
        String decodeResponse = decodeResult.getResponse().getContentAsString();
        System.out.println("Decode Response: " + decodeResponse);
    }
    
    @Test
    public void testDecodeNotFound() throws Exception {
        // Attempt to decode a non-existent short URL
        mockMvc.perform(post("/api/decode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"http://short.est/unknown\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("URL not found for: http://short.est/unknown"));
    }
    private String extractShortUrlFromResponse(String response) {
        // Assuming the response format is: {"url":"http://short.est/GeAi9K"}
        int startIndex = response.indexOf("http://short.est/");
        int endIndex = response.lastIndexOf("\"");
        return response.substring(startIndex, endIndex);
    }
}