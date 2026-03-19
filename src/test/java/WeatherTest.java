import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @Disabled
    class DisabledTests {

        @Test
        void getEnvVariable_Test() {

            String actual = System.getenv("WeatherAPI_Key");
            String expected = "d7e302bbbb4946099e2171719260603";

            assertEquals(expected, actual);
        }

        @Test
        void getWeatherTest_success() throws IOException, InterruptedException {
            JsonNode node = weather.getWeatherFromAPI("28412");

            assertNotNull(node);
        }

        @Test
        void getWeatherTest_failure() throws IOException, InterruptedException {
            JsonNode node = weather.getWeatherFromAPI("99999");
            assertNull(node);
        }
    }

    @Nested
    class GetLocationTests {

        @Test
        void getLocationTest() {

            String expected = "Wilmington, North Carolina";
            String actual = weather.getLocation(jsonNode);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class ZipCodeTests {
        @Test
        void validateZipCodeTest_validZipCode() {
            assertTrue(weather.validateZipCode("12345"));
        }

        @Test
        void validateZipCodeTest_invalidZipCode_tooShort() {
            assertFalse(weather.validateZipCode("1234"));
        }

        @Test
        void validateZipCodeTest_invalidZipCode_tooLong() {
            assertFalse(weather.validateZipCode("123456"));
        }

        @Test
        void validateZipCodeTest_invalidZipCode_null() {
            assertFalse(weather.validateZipCode(null));
        }

        @Test
        void validateZipCodeTest_invalidZipCode_empty() {
            assertFalse(weather.validateZipCode(""));
        }

        @Test
        void validateZipCodeTest_invalidZipCode_nonnumeric() {
            assertFalse(weather.validateZipCode("a1234"));
        }
    }

}