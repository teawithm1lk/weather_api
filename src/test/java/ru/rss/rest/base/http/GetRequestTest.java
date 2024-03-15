package ru.rss.rest.base.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rss.rest.exception.ConnectionException;

public class GetRequestTest {
    @Test
    public void getTest() throws ConnectionException {
        String correctURL = "http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}"
                .replace("{API key}", System.getenv("TEST_API_KEY"));
        Assertions.assertNotNull(GetRequest.get(correctURL));
    }
}
