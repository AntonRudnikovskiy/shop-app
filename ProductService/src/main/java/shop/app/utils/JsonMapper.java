package shop.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonMapper {
    private final ObjectMapper objectMapper;

    public <T> T readJsonAsObject(String fileName, Class<T> clazz) {
        try (InputStream inputStream = new ClassPathResource(fileName).getInputStream()) {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            log.error("Failed to read JSON file: {}. Error: {}", fileName, e.getMessage(), e);
            throw new RuntimeException("Failed to process JSON file: " + fileName, e);
        }
    }
}

