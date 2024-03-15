package ru.rss.rest.base;

import lombok.experimental.UtilityClass;
import ru.rss.rest.exception.ConnectionException;

import java.io.IOException;
import java.net.*;

@UtilityClass
public class ConnectionBuilder {
    private static final String[] JSON_CONTENT_TYPE = {"Content-Type", "application/json"};

    private URL makeUrl(String address) throws ConnectionException {
        try {
            return new URI(address).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new ConnectionException("Incorrect URL address while processing: ", e);
        }
    }

    private HttpURLConnection makeUrlConnection(String address) throws ConnectionException {
        try {
            return (HttpURLConnection) makeUrl(address).openConnection();
        } catch (IOException e) {
            throw new ConnectionException("Process failed during creation of connection: ", e);
        }
    }

    public HttpURLConnection makeConfiguratedConnection(String address, String method) throws ConnectionException {
        HttpURLConnection connection = makeUrlConnection(address);
        connection.setDoOutput(true);
        connection.setRequestProperty(JSON_CONTENT_TYPE[0], JSON_CONTENT_TYPE[1]);
        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            throw new ConnectionException("The requested method isn't valid for HTTP: ", e);
        }
        return connection;
    }
}
