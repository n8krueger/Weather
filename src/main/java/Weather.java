import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;

public class Weather {

    final String API_KEY_VARIABLE = "WeatherAPI_KEY";
    final String WEATHERAPI_URL = "https://api.weatherapi.com/v1/forecast.json";

    String apiKey;

    public Weather() {
        apiKey = System.getenv(API_KEY_VARIABLE);
    }

    public void getCurrentWeather(String zipcode) {
        JsonNode jsonNode = getWeatherFromAPI(zipcode);
        String location = getLocation(jsonNode);
        String temp = jsonNode.path("current").path("temp_f").asText();
        String condition = jsonNode.path("current").path("condition").path("text").asText();

        lineBreak();

        System.out.println("The current weather at " + location);
        System.out.println(condition + ", " + temp + "F");

        lineBreak();
    }

    public void getTenDayForecast(String zipcode) {
        JsonNode jsonNode = getWeatherFromAPI(zipcode);
        String location = getLocation(jsonNode);

        lineBreak();

        System.out.println("The 10-day forecast for " + location);

        int dayCount = 1;
        Iterator<JsonNode> days = jsonNode.path("forecast").path("forecastday").elements();
        while (days.hasNext()) {
            JsonNode day = days.next();
            String date = day.path("date").asText();
            String forecastDay = LocalDate.parse(date).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
            String highTemp = day.path("day").path("maxtemp_f").asText();
            String lowTemp = day.path("day").path("mintemp_f").asText();
            String rainChance = day.path("day").path("daily_chance_of_rain").asText();
            String conditions = day.path("day").path("condition").path("text").asText();

            System.out.println("DAY " + dayCount);
            System.out.println(forecastDay);
            System.out.println(conditions + ", " + highTemp + "/" + lowTemp + "F");
            System.out.println("Chance of Rain: " + rainChance + "%");

            System.out.println();

            dayCount++;
        }

        lineBreak();
    }

    // get all data from the forecast endpoint.
    public JsonNode getWeatherFromAPI(String zipcode) {

        String jsonBody = "";
        JsonNode jsonNode = null;

        String paramString = "?key=" + apiKey + "&q=" + zipcode + "&days=10";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEATHERAPI_URL+paramString))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonBody = response.body();
        }
        catch (IOException | InterruptedException e) {
            // TODO: handle exception
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(jsonBody);
        }
        catch (JsonProcessingException e) {
            // TODO: handle exception
        }

        return jsonNode;
    }

    public String getLocation(JsonNode jsonNode) {

        String city = jsonNode.path("location").path("name").asText();
        String state = jsonNode.path("location").path("region").asText();

        return city + ", " + state;
    }

    private void lineBreak() {
        System.out.println();
        System.out.println("----------------------------------------------------------------------");
        System.out.println();
    }
}
