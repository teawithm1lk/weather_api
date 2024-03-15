package ru.rss.rest.base.http;

import lombok.experimental.UtilityClass;
import ru.rss.rest.base.ConnectionBuilder;
import ru.rss.rest.exception.ConnectionException;

import java.net.HttpURLConnection;

@UtilityClass
public class GetRequest {
    private HttpURLConnection getConnection(String address) throws ConnectionException {
        return ConnectionBuilder.makeConfiguratedConnection(address, HTTPRequestMethod.GET_METHOD);
    }

    public String get(String address) throws ConnectionException {
        return Response.getResponseAndDisconnect(getConnection(address));
    }
}
