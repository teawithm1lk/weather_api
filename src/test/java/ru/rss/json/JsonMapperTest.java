package ru.rss.json;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rss.json.geocoding.Location;
import ru.rss.json.weather.*;

public class JsonMapperTest {
    private static final String geoJson = """
            {
              "name":"London",
              "lat":42.9832406,
              "lon":-81.243372,
              "country":"CA",
              "state":"Ontario"
            }""";

    private static final String weatherJson = """
            {
              "coord": {
                "lon": 10.99,
                "lat": 44.34
              },
              "weather": [
                {
                  "id": 501,
                  "main": "Rain",
                  "description": "moderate rain",
                  "icon": "10d"
                }
              ],
              "base": "stations",
              "main": {
                "temp": 298.48,
                "feels_like": 298.74,
                "temp_min": 297.56,
                "temp_max": 300.05,
                "pressure": 1015,
                "humidity": 64,
                "sea_level": 1015,
                "grnd_level": 933
              },
              "visibility": 10000,
              "wind": {
                "speed": 0.62,
                "deg": 349,
                "gust": 1.18
              },
              "rain": {
                "1h": 3.16
              },
              "clouds": {
                "all": 100
              },
              "dt": 1661870592,
              "sys": {
                "type": 2,
                "id": 2075663,
                "country": "IT",
                "sunrise": 1661834187,
                "sunset": 1661882248
              },
              "timezone": 7200,
              "id": 3163858,
              "name": "Zocca",
              "cod": 200
            }
            """;

    private static final String incorrectJson = """
            {
                incorrect: "yes"
            }
            """;

    @Test
    public void createEntityFromJson_class_geoTest() {
        Location loc = JsonMapper.createEntityFromJson(geoJson, Location.class);

        {
            String expected = "London";
            Assertions.assertEquals(expected, loc.getName());
        }

        {
            double delta = 0.0000001;
            double expected = 42.9832406;
            Assertions.assertEquals(expected, loc.getLat(), delta);

            expected = -81.243372;
            Assertions.assertEquals(expected, loc.getLon(), delta);
        }
    }

    @Test
    public void createEntityFromJson_class_weatherTest() {
        WeatherData weather = JsonMapper.createEntityFromJson(weatherJson, WeatherData.class);

        {
            WeatherStatus actual = weather.getWeather();
            WeatherStatus expected = new WeatherStatus("Rain", "moderate rain");

            Assertions.assertEquals(expected.getMain(), actual.getMain());
            Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        }

        {
            Temperature actual = weather.getTemperature();
            Temperature expected = new Temperature(298.48, 298.74);

            Assertions.assertEquals(expected.getTemp(), actual.getTemp());
            Assertions.assertEquals(expected.getFeelsLike(), actual.getFeelsLike());
        }

        {
            int actual = weather.getVisibility();
            int expected = 10000;

            Assertions.assertEquals(expected, actual);
        }

        {
            Wind actual = weather.getWind();
            Wind expected = new Wind(0.62);

            Assertions.assertEquals(expected.getSpeed(), actual.getSpeed());
        }

        {
            long actual = weather.getDatetime();
            long expected = 1661870592;

            Assertions.assertEquals(expected, actual);
        }

        {
            AdditionalInfo actual = weather.getSys();
            AdditionalInfo expected = new AdditionalInfo(1661834187, 1661882248);

            Assertions.assertEquals(expected.getSunrise(), actual.getSunrise());
            Assertions.assertEquals(expected.getSunset(), actual.getSunset());
        }

        {
            long actual = weather.getTimezone();
            long expected = 7200;

            Assertions.assertEquals(expected, actual);
        }

        {
            String actual = weather.getName();
            String expected = "Zocca";

            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    public void createEntityFromJson_class_incorrectTest() {
        Assertions.assertNull(JsonMapper.createEntityFromJson(incorrectJson, Location.class));
        Assertions.assertNull(JsonMapper.createEntityFromJson(null, Location.class));
    }

    @Test
    public void createEntityFromJson_typeReference_geoTest() {
        Location loc = JsonMapper.createEntityFromJson(geoJson, new TypeReference<>() {
        });

        {
            String expected = "London";
            Assertions.assertEquals(expected, loc.getName());
        }

        {
            double delta = 0.0000001;
            double expected = 42.9832406;
            Assertions.assertEquals(expected, loc.getLat(), delta);

            expected = -81.243372;
            Assertions.assertEquals(expected, loc.getLon(), delta);
        }
    }

    @Test
    public void createEntityFromJson_typeReference_weatherTest() {
        WeatherData weather = JsonMapper.createEntityFromJson(weatherJson, new TypeReference<>() {
        });

        {
            WeatherStatus actual = weather.getWeather();
            WeatherStatus expected = new WeatherStatus("Rain", "moderate rain");

            Assertions.assertEquals(expected.getMain(), actual.getMain());
            Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        }

        {
            Temperature actual = weather.getTemperature();
            Temperature expected = new Temperature(298.48, 298.74);

            Assertions.assertEquals(expected.getTemp(), actual.getTemp());
            Assertions.assertEquals(expected.getFeelsLike(), actual.getFeelsLike());
        }

        {
            int actual = weather.getVisibility();
            int expected = 10000;

            Assertions.assertEquals(expected, actual);
        }

        {
            Wind actual = weather.getWind();
            Wind expected = new Wind(0.62);

            Assertions.assertEquals(expected.getSpeed(), actual.getSpeed());
        }

        {
            long actual = weather.getDatetime();
            long expected = 1661870592;

            Assertions.assertEquals(expected, actual);
        }

        {
            AdditionalInfo actual = weather.getSys();
            AdditionalInfo expected = new AdditionalInfo(1661834187, 1661882248);

            Assertions.assertEquals(expected.getSunrise(), actual.getSunrise());
            Assertions.assertEquals(expected.getSunset(), actual.getSunset());
        }

        {
            long actual = weather.getTimezone();
            long expected = 7200;

            Assertions.assertEquals(expected, actual);
        }

        {
            String actual = weather.getName();
            String expected = "Zocca";

            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    public void createEntityFromJson_typeReference_incorrectTest() {
        Assertions.assertNull(JsonMapper.createEntityFromJson(incorrectJson, new TypeReference<Location>() {
        }));
        Assertions.assertNull(JsonMapper.createEntityFromJson(null, new TypeReference<Location>() {
        }));
    }

    @Test
    public void createJsonFromEntity_weatherTest() {
        WeatherData weather = new WeatherData();
        weather.setWeather(new WeatherStatus("Rain", "moderate rain"));
        weather.setTemperature(new Temperature(298.48, 298.74));
        weather.setVisibility(10000);
        weather.setWind(new Wind(0.62));
        weather.setDatetime(1661870592);
        weather.setSys(new AdditionalInfo(1661834187, 1661882248));
        weather.setTimezone(7200);
        weather.setName("Zocca");

        String expected = "{\"weather\":{\"main\":\"Rain\",\"description\":\"moderate rain\"},\"temperature\":{\"temp\":298.48,\"feels_like\":298.74},\"visibility\":10000,\"wind\":{\"speed\":0.62},\"datetime\":1661870592,\"sys\":{\"sunrise\":1661834187,\"sunset\":1661882248},\"timezone\":7200,\"name\":\"Zocca\"}";
        Assertions.assertEquals(expected, JsonMapper.createJsonFromEntity(weather));
    }

    @Test
    public void createJsonFromEntity_incorrectTest() {
        Assertions.assertNull(JsonMapper.createJsonFromEntity(null));
    }
}
