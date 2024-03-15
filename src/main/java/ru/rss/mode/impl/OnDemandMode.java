package ru.rss.mode.impl;

import ru.rss.mode.Mode;
import ru.rss.json.weather.WeatherData;
import ru.rss.rest.service.WeatherService;
import ru.rss.util.Constants;

import java.util.LinkedHashMap;

public class OnDemandMode implements Mode {
    private final LinkedHashMap<String, WeatherData> queueMap = new LinkedHashMap<>();

    @Override
    public WeatherData submit(String cityName, String APIKey) {
        WeatherData existingWeather = findExistingWeather(cityName);
        if (existingWeather != null) {
            return existingWeather;
        }

        rollQueueIfNeed();

        WeatherData weather = WeatherService.getCurrentWeatherEntityForCoords(cityName, APIKey);
        queueMap.putLast(cityName, weather);
        return weather;
    }

    @Override
    public void close() {

    }

    private void rollQueueIfNeed() {
        if (queueMap.size() == Constants.MAX_QUEUE_SIZE) {
            queueMap.pollFirstEntry();
        }
    }

    private WeatherData findExistingWeather(String cityName) {
        return queueMap.get(cityName);
    }
}
