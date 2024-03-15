package ru.rss.util;

import lombok.experimental.UtilityClass;

import java.util.ResourceBundle;

@UtilityClass
public class Constants {
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("settings");

    private static final String MAX_QUEUE_SIZE_STRING = RESOURCES.getString("MAX_QUEUE_SIZE");
    public static final int MAX_QUEUE_SIZE = Integer.parseInt(MAX_QUEUE_SIZE_STRING);
}
