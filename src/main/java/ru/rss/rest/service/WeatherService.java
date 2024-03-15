package ru.rss.rest.service;

import lombok.experimental.UtilityClass;
import ru.rss.json.JsonMapper;
import ru.rss.json.geocoding.Location;
import ru.rss.json.weather.WeatherData;
import ru.rss.rest.base.http.GetRequest;
import ru.rss.rest.exception.ConnectionException;
import ru.rss.util.URLBuildUtils;

import java.util.List;

@UtilityClass
public class WeatherService {
    private static final int LIMIT_CITIES = 1;

    public String getCurrentWeatherJsonForCoords(String latitude, String longitude, String APIKey) {
        try {
            return GetRequest.get(URLBuildUtils.buildCurrentWeatherURL(latitude, longitude, APIKey));
        } catch (ConnectionException e) {
            return null;
        }
    }

    public WeatherData getCurrentWeatherEntityForCoords(String cityName, String APIKey) {
        List<Location> locations = GeocodingService.getLocationEntityFromCity(cityName, String.valueOf(LIMIT_CITIES), APIKey);
        return JsonMapper.createEntityFromJson(getCurrentWeatherJsonForLocation(extractLocation(locations), APIKey),
                WeatherData.class);
    }

    private String getCurrentWeatherJsonForLocation(Location loc, String APIKey) {
        return getCurrentWeatherJsonForCoords(String.valueOf(loc.getLat()), String.valueOf(loc.getLon()), APIKey);
    }

    private Location extractLocation(List<Location> locs) {
        if (locs == null || locs.isEmpty()) {
            throw new RuntimeException("City with specified name was not found!");
        }
        return locs.get(0);
    }
}
