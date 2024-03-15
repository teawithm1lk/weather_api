# OpenWeatherMap Java SDK

## Introduction

OpenWeatherMap Java SDK designed for external using by developers. For now there are a few functions like:
- Creation of object of SDK.
- Deletion of object of SDK.
- Getting current weather in specified city.

## Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage Example](#usage-example)

## Installation

Due to having no verified account on mavenrepository.com and having no opened nexus
repository to deploy this library somewhere, the real advice to install by maven is next:
- install the project by writing
```cmd
mvn clean install
```

- add next artifact to your java application:
 ```xml
<dependency>
    <groupId>ru.rss</groupId>
    <artifactId>weather_sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

It can work in several modes:
  
- Polling mode: you are requesting for a weather in some city and request is repeating
every 10 minutes, overwrite old weather info. While 10 minutes hasn't gone, last weather
request is considered like actual. It has implemented like that to ensure low latency of
SDK response.

- On demand mode: every time when you request a weather, SDK will push new request to the Open Weather API. It has higher latency than previous mode. 

To interact with SDK methods you need to provide an Open Weather API key.
Only 1 SDK with same API key can work at the same time.

## Usage Example

To say as note: validation of cities should be done externally, by SDK users.

Example of work in polling mode:

```java
import ru.rss.WeatherProvider;
import ru.rss.exception.APIKeyException;
import ru.rss.mode.impl.PollingMode;

public class Main {
    public static void main(String[] args) {
        String APIKey = "b9567fcd32131bcca1239befb";
        WeatherProvider wsdk = null;
        try {
            wsdk = new WeatherProvider(APIKey, new PollingMode());
            while (true) {
                wsdk.getWeatherForCity("London");
                wsdk.getWeatherForCity("Moscow");
                Thread.sleep(300000);
            }
        } catch (APIKeyException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (wsdk != null) {
                wsdk.delete();
            }
        }
    }
}
```
In this example we can see how to use SDK in polling mode. Current weather in 2 cities is
being requested every 5 minutes. Every 2nd requests weather will be equal to previous,
because it's updating every 10 minutes for every city from the moment of access to it.

Example of work on demand mode:

```java
import ru.rss.WeatherProvider;
import ru.rss.exception.APIKeyException;
import ru.rss.mode.impl.OnDemandMode;

public class Main {
    public static void main(String[] args) {
        String APIKey = "b9567fcd32131bcca1239befb";
        WeatherProvider wsdk = null;
        try {
            wsdk = new WeatherProvider(APIKey, new OnDemandMode());

            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    String city = scanner.nextLine();
                    System.out.println(wsdk.getWeatherForCity(city));
                }
            }
        } catch (APIKeyException e) {
            throw new RuntimeException(e);
        } finally {
            if (wsdk != null) {
                wsdk.delete();
            }
        }
    }
}
```

In this example we can get json of current weather by writing city into console, and it is
printing into standard output. The SDK object should be deleted, when leaving out of scope.

Also, deletion of SDK object can be with static method of WeatherProvider class:
```java
import ru.rss.WeatherProvider;

public class Main {
    public static void main(String[] args) {
        String APIKey = "b9567fcd32131bcca1239befb";
        WeatherProvider wsdk = null;
        try {
            wsdk = new WeatherProvider(APIKey, new OnDemandMode());
            WeatherProvider.delete(APIKey);
        } catch (APIKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
```