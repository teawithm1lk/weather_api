package ru.rss.json.weather;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class WeatherData {
    @JsonDeserialize(using = WeatherStatusDeserializer.class)
    private WeatherStatus weather;
    @JsonAlias({"main"})
    private Temperature temperature;
    private int visibility;
    private Wind wind;
    @JsonAlias({"dt"})
    private long datetime;
    private AdditionalInfo sys;
    private long timezone;
    private String name;
}
