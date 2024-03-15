package ru.rss.rest.service;

import org.junit.jupiter.api.Test;

public class GeocodingServiceTest {
    private static final String correctJson = """
            {
              "name":"London",
              "lat":42.9832406,
              "lon":-81.243372,
              "country":"CA",
              "state":"Ontario"
            }""";

    @Test
    public void getLocationJsonFromCityTest() {
        //TODO MOCKS
    }

    @Test
    public void getLocationEntityFromCityTest() {

    }
}
