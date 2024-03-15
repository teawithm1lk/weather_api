package ru.rss.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class URLBuildUtilsTest {
    @Test
    public void buildCurrentWeatherURLTest() {
        String latitude = "10.2837319";
        String longitude = "longitude";
        String APIKey = "7ae65fbcf8ear7608b6c";

        String expected ="http://api.openweathermap.org/data/2.5/weather?lat=10.2837319&lon=longitude&appid=7ae65fbcf8ear7608b6c";
        String actual = URLBuildUtils.buildCurrentWeatherURL(latitude, longitude, APIKey);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void buildGeoCoordsURLTest() {
        String cityName = "London";
        String limit = "1";
        String APIKey = "7ae65fbcf8ear7608b6c";

        String expected ="http://api.openweathermap.org/geo/1.0/direct?q=London&limit=1&appid=7ae65fbcf8ear7608b6c";
        String actual = URLBuildUtils.buildGeoCoordsURL(cityName,limit, APIKey);
        Assertions.assertEquals(expected, actual);
    }
}
