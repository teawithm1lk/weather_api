package ru.rss.mode.impl;

import ru.rss.json.weather.WeatherData;
import ru.rss.mode.Mode;
import ru.rss.rest.service.WeatherService;
import ru.rss.util.Constants;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public class PollingMode implements Mode {
    private final LinkedHashMap<String, PollData> queueMap = new LinkedHashMap<>();

    @Override
    public WeatherData submit(String cityName, String APIKey) {
        PollData existingData = queueMap.get(cityName);
        if (existingData != null) {
            return existingData.weatherData;
        }

        rollQueueIfNeed();

        PollData pollData = new PollData(APIKey);
        pollData.buildThread(WeatherService::getCurrentWeatherEntityForCoords, cityName).start();
        queueMap.putLast(cityName, pollData);

        return pollData.weatherData;
    }

    @Override
    public void close() {
        queueMap.forEach((k, v) -> v.stopThread());
    }

    private void rollQueueIfNeed() {
        if (queueMap.size() == Constants.MAX_QUEUE_SIZE) {
            queueMap.pollFirstEntry().getValue().stopThread();
        }
    }

    private static class PollData {
        private final String APIKey;
        private WeatherData weatherData;
        private Thread thread;
        private LocalDateTime lastAccess;

        public PollData(String APIKey) {
            this.APIKey = APIKey;
        }

        public void stopThread() {
            thread.interrupt();
        }

        private Thread buildThread(BiFunction<String, String, WeatherData> func, String cityName) {
            weatherData = func.apply(cityName, APIKey);
            lastAccess = LocalDateTime.now();

            thread = new Thread(() -> {
                while (!thread.isInterrupted()) {
                    LocalDateTime now = LocalDateTime.now();
                    if (lastAccess.isBefore(now.minusMinutes(10)) || weatherData == null) {
                        weatherData = func.apply(cityName, APIKey);
                        lastAccess = now;
                    }
                }
            });
            return thread;
        }
    }
}
