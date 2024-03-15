package ru.rss.util;

import lombok.experimental.UtilityClass;

import java.util.ResourceBundle;

@UtilityClass
public final class URLConstants {
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("strings");

    private final String CURRENT_WEATHER_URL_SUBKEY = RESOURCES.getString("CURRENT_URL_SUBKEY");
    private final String CURRENT_WEATHER_URL_VERSION = RESOURCES.getString("CURRENT_URL_VERSION");
    private final String CURRENT_WEATHER_DATA_SUBKEY = RESOURCES.getString("CURRENT_WEATHER_DATA_URL_WITH_PARAM");

    public final String WEATHERMAP_API_BASE_URL = RESOURCES.getString("WEATHERMAP_API_BASE_URL");
    public final String CURRENT_WEATHER_BASE_URL = "%s%s%s".formatted(WEATHERMAP_API_BASE_URL, CURRENT_WEATHER_URL_SUBKEY, CURRENT_WEATHER_URL_VERSION);
    public final String CURRENT_WEATHER_DATA_URL = "%s%s".formatted(CURRENT_WEATHER_BASE_URL, CURRENT_WEATHER_DATA_SUBKEY);

    private final String GEO_URL_SUBKEY = RESOURCES.getString("GEO_URL_SUBKEY");
    private final String GEO_URL_VERSION = RESOURCES.getString("GEO_URL_VERSION");
    private final String GEO_COORDS_SUBKEY = RESOURCES.getString("GEO_COORDS_URL_WITH_PARAM");

    public final String GEO_BASE_URL = "%s%s%s".formatted(WEATHERMAP_API_BASE_URL, GEO_URL_SUBKEY, GEO_URL_VERSION);
    public final String GEO_COORDS_URL = "%s%s".formatted(GEO_BASE_URL, GEO_COORDS_SUBKEY);
}
