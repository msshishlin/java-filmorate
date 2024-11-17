package ru.yandex.practicum.filmorate.util.serialization;

// region imports

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.Duration;

// endregion

@JsonComponent
@Slf4j
public class DurationDeserializer extends JsonDeserializer<Duration> {
    @Override
    public Duration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String durationString = jsonParser.getText();
        if (durationString == null || durationString.isBlank()) {
            return null;
        }

        int durationNumber;
        try {
            durationNumber = Integer.parseInt(durationString);
        } catch(NumberFormatException ex) {
            log.error(ex.getLocalizedMessage());
            return null;
        }

        return Duration.ofMinutes(durationNumber);
    }
}
