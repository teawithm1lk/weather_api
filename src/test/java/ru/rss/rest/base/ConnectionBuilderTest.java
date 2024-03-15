package ru.rss.rest.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rss.rest.base.http.HTTPRequestMethod;
import ru.rss.rest.exception.ConnectionException;

public class ConnectionBuilderTest {
    @Test
    public void getTest() {
        String incorrectURL = "dashdha";
        String malformedURL = "http://hahaha.ru/\n";
        String correctURL = "http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}"
                .replace("{API key}", System.getenv("TEST_API_KEY"));

        String correctMethod = HTTPRequestMethod.GET_METHOD;
        String incorrectMethod = "dsajd";

        Assertions.assertThrows(IllegalArgumentException.class, () -> ConnectionBuilder.makeConfiguratedConnection(incorrectURL, correctMethod));
        Assertions.assertThrows(ConnectionException.class, () -> ConnectionBuilder.makeConfiguratedConnection(malformedURL, correctMethod));
        Assertions.assertThrows(ConnectionException.class, () -> ConnectionBuilder.makeConfiguratedConnection(correctURL, incorrectMethod));

        Assertions.assertDoesNotThrow(() -> ConnectionBuilder.makeConfiguratedConnection(correctURL, correctMethod));
    }
}
