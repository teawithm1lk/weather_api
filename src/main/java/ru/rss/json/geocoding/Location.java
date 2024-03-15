package ru.rss.json.geocoding;

import lombok.Data;

@Data
public class Location {
    private String name;
    private double lat;
    private double lon;
}
