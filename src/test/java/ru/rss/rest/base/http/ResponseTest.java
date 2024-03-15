package ru.rss.rest.base.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rss.rest.base.ConnectionBuilder;
import ru.rss.rest.exception.ConnectionException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;

public class ResponseTest {
    @Test
    public void getResponseTest() throws MalformedURLException, ConnectionException {
        String correctURL = "http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}"
                .replace("{API key}", System.getenv("TEST_API_KEY"));
        HttpURLConnection connection = ConnectionBuilder.makeConfiguratedConnection(correctURL, HTTPRequestMethod.GET_METHOD);

        String incorrectURL = "http://sfsdfds.sdfsd";
        HttpURLConnection incorrectConnection = new HttpURLConnection(URI.create(incorrectURL).toURL()) {
            @Override
            public void disconnect() {

            }

            @Override
            public boolean usingProxy() {
                return false;
            }

            @Override
            public void connect() {

            }
        };

        Assertions.assertThrows(ConnectionException.class, () -> Response.getResponseAndDisconnect(incorrectConnection));
        Assertions.assertNotNull(Response.getResponseAndDisconnect(connection));
    }
}
