package ru.rss.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class URLBuildUtils {
    public String buildCurrentWeatherURL(String latitude, String longitude, String APIKey) {
        return URLConstants.CURRENT_WEATHER_DATA_URL
                .replace("{lat}", latitude)
                .replace("{lon}", longitude)
                .replace("{API key}", APIKey);
    }

    public String buildGeoCoordsURL(String cityName, String limit, String APIKey) {
        return URLConstants.GEO_COORDS_URL
                .replace("{city_name}", cityName)
                .replace("{limit}", limit)
                .replace("{API key}", APIKey);
    }
}
