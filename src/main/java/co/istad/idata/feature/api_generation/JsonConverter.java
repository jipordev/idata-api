package co.istad.idata.feature.api_generation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;

@Converter
public class JsonConverter implements AttributeConverter<String, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to String", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, String.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert String to JSON", e);
        }
    }
}

