import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WeatherTest {

    static Weather weather;

    @BeforeAll
    static void setup() throws IOException {
        weather = new Weather();
    }

    @Test
    void getEnvVariable_Test() {

        String actual = System.getenv("WeatherAPI_Key");
        String expected = "d7e302bbbb4946099e2171719260603";

        assertEquals(expected, actual);
    }

    @Test
    void getCurrentWeatherTest() {

        weather.getCurrentWeather("28412");
    }

}