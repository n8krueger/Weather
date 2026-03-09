import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Weather {

    final String API_KEY_VARIABLE = "WeatherAPI_KEY";
    final String WEATHERAPI_URL = "https://api.weatherapi.com/v1/forecast.json";

    String apiKey;

    public Weather() {
        apiKey = System.getenv(API_KEY_VARIABLE);
    }

    public void getCurrentWeather(String zipcode) {

        String paramString = "?key=" + apiKey + "&q=" + zipcode;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEATHERAPI_URL+paramString))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonBody = response.body();
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> map = objectMapper.readValue(jsonBody, new TypeReference<>() {});
            Map<String, String> locationMap = (Map<String, String>) map.get("location");
            Map<String, Object> currentMap = (Map<String, Object>) map.get("current");
            Map<String, String> conditionsMap = (Map<String, String>) currentMap.get("condition");

            //System.out.println("Status Code: " + response.statusCode());
            System.out.println("The current weather for " + locationMap.get("name") + ", " + locationMap.get("region") + " is " + currentMap.get("temp_f") + "F, " + conditionsMap.get("text"));
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
