package ru.rss.mode;

import ru.rss.json.weather.WeatherData;

public interface Mode {
    WeatherData submit(String cityName, String APIKey);

    void close();
}
