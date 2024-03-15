package ru.rss.json.weather;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class WeatherStatusDeserializer extends StdDeserializer<WeatherStatus> {
    public WeatherStatusDeserializer() {
        this(null);
    }

    protected WeatherStatusDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public WeatherStatus deserialize(JsonParser jp, DeserializationContext ctxt) {
        JsonNode node;
        try {
            node = jp.getCodec().readTree(jp);
        } catch (IOException e) {
            return null;
        }

        if (node.isArray() && !node.isEmpty()) {
            JsonNode weatherNode = node.get(0);
            return new WeatherStatus(weatherNode.get("main").asText(), weatherNode.get("description").asText());
        }
        return null;
    }
}
