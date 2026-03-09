import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WeatherTest {

    static Weather weather;

    @BeforeAll
    static void setup() throws IOException {
        weather = new Weather();
    }

    @Test
    void loadConfigTest() throws IOException {
        //Weather weather = new Weather();
        //Map<String, String> config = weather.loadConfig();
        String actual = weather.config.get("apiKey");
        String expected = "d7e302bbbb4946099e2171719260603";

        assertEquals(expected, actual);
    }

    @Test
    void getCurrentWeatherTest() {
        weather.getCurrentWeather("28412");
    }

}