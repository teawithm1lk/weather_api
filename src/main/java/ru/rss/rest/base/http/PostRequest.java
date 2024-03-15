package ru.rss.rest.base.http;

import lombok.experimental.UtilityClass;
import ru.rss.rest.base.ConnectionBuilder;
import ru.rss.rest.exception.ConnectionException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class PostRequest {
    private HttpURLConnection postConnection(String address) throws ConnectionException {
        return ConnectionBuilder.makeConfiguratedConnection(address, HTTPRequestMethod.POST_METHOD);
    }

    private void postRequest(HttpURLConnection connection, String json) throws ConnectionException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            throw new ConnectionException("Error occurred while posting request: ", e);
        }
    }

    public String post(String address, String json) throws ConnectionException {
        HttpURLConnection connection = postConnection(address);
        postRequest(connection, json);
        return Response.getResponseAndDisconnect(connection);
    }
}
