package ru.rss.rest.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import ru.rss.json.JsonMapper;
import ru.rss.json.geocoding.Location;
import ru.rss.rest.base.http.GetRequest;
import ru.rss.rest.exception.ConnectionException;
import ru.rss.util.URLBuildUtils;

import java.util.List;

@UtilityClass
public class GeocodingService {
    public String getLocationJsonFromCity(String cityName, String limit, String APIKey) {
        try {
            return GetRequest.get(URLBuildUtils.buildGeoCoordsURL(cityName, limit, APIKey));
        } catch (ConnectionException e) {
            return null;
        }
    }

    public List<Location> getLocationEntityFromCity(String cityName, String limit, String APIKey) {
        return JsonMapper.createEntityFromJson(getLocationJsonFromCity(cityName, limit, APIKey), new TypeReference<>() {});
    }
}
