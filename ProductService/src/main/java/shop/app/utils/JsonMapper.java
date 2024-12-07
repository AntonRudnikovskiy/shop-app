package shop.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JsonMapper {
    public <T> T readJsonAsObject(String fileName, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource(fileName);
            return objectMapper.readValue(resource.getInputStream(), clazz);
        } catch (IOException e) {
            log.error("Error occurred while reading json file with name: {}", fileName);
            throw new RuntimeException(e.getMessage());
        }
    }
}

