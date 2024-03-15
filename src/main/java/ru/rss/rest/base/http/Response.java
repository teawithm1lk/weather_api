package ru.rss.rest.base.http;

import lombok.experimental.UtilityClass;
import ru.rss.rest.exception.ConnectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class Response {
    private String getResponse(HttpURLConnection connection) throws ConnectionException {
        try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine);
            }

            return response.toString();
        } catch (IOException e) {
            throw new ConnectionException("Error occurred while getting response: ", e);
        }
    }

    public String getResponseAndDisconnect(HttpURLConnection connection) throws ConnectionException {
        String response = getResponse(connection);
        connection.disconnect();
        return response;
    }
}
