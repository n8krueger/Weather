import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class WeatherTest {

    static Weather weather;
    static JsonNode jsonNode;

    @BeforeAll
    static void setup() throws IOException {
        weather = new Weather();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("forecast-example.json");
        byte[] bytes = inputStream.readAllBytes();

        ObjectMapper mapper = new ObjectMapper();
        jsonNode = mapper.readTree(new String(bytes, StandardCharsets.UTF_8));
    }

    @Test
    void getEnvVariable_Test() {

        String actual = System.getenv("WeatherAPI_Key");
        String expected = "d7e302bbbb4946099e2171719260603";

        assertEquals(expected, actual);
    }

    @Test
    void getWeatherTest() {
        JsonNode node = weather.getWeatherFromAPI("28412");

        assertNotNull(node);
    }

//    @Test
//    void getWeatherTest_pullDateTimeWithin30mins() throws InterruptedException, IOException {
//        weather.getWeather("28412", Weather.FORECAST_OPTION.CURRENT);
//        Map<String, Object> pull1 = weather.weatherMap;
//
//        // new
//        weather.getWeather("21771", Weather.FORECAST_OPTION.CURRENT);
//
//    }

    @Test
    void getLocationTest() {

        String expected = "Wilmington, North Carolina";
        String actual = weather.getLocation(jsonNode);

        assertEquals(expected, actual);
    }

}