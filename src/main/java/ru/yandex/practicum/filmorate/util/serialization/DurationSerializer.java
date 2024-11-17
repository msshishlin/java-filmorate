package ru.yandex.practicum.filmorate.util.serialization;

// region imports

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.Duration;

// endregion

@JsonComponent
public class DurationSerializer extends JsonSerializer<Duration> {
    @Override
    public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (duration == null) {
            return;
        }

        jsonGenerator.writeNumber(duration.toMinutes());
    }
}
