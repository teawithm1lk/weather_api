package ru.rss;

import ru.rss.mode.Mode;
import ru.rss.exception.APIKeyException;
import ru.rss.json.JsonMapper;

import java.util.HashMap;
import java.util.Map;

public class WeatherProvider {
    private static final Map<String, WeatherProvider> apis = new HashMap();

    private final String APIKey;
    private final Mode mode;

    public static void delete(String APIKey) throws APIKeyException {
        if (!apis.containsKey(APIKey)) {
            throw new APIKeyException("Weather SDK have not an object with specified API key!");
        }

        apis.remove(APIKey).stopSubThreads();
    }

    public WeatherProvider(String APIKey, Mode mode) throws APIKeyException {
        if (apis.containsKey(APIKey)) {
            throw new APIKeyException("Weather SDK have an object with specified API key!");
        }

        this.APIKey = APIKey;
        this.mode = mode;

        apis.put(APIKey, this);
    }

    public String getWeatherForCity(String cityName) {
        return JsonMapper.createJsonFromEntity(mode.submit(cityName, APIKey));
    }

    public void delete() {
        apis.remove(APIKey).stopSubThreads();
    }

    private void stopSubThreads() {
        mode.close();
    }
}
