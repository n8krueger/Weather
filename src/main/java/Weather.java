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

    private final String API_KEY_VARIABLE = "WeatherAPI_KEY";
    private final String WEATHERAPI_URL = "https://api.weatherapi.com/v1/forecast.json";
    private final String ZIPCODE_REGEX_PATTERN = "^[0-9]{5}$";

    final String API_KEY;

    public Weather() {
        API_KEY = System.getenv(API_KEY_VARIABLE);
    }

    public void getCurrentWeather(JsonNode weatherJson) {

        String location = getLocation(weatherJson);
        String temp = weatherJson.path("current").path("temp_f").asText();
        String condition = weatherJson.path("current").path("condition").path("text").asText();

        lineBreak();

        System.out.println("The current weather at " + location);
        System.out.println(condition + ", " + temp + "F");

        lineBreak();
    }

    public void getTenDayForecast(JsonNode weatherJson) {

        String location = getLocation(weatherJson);

        lineBreak();

        System.out.println("The 10-day forecast for " + location);

        int dayCount = 1;
        Iterator<JsonNode> days = weatherJson.path("forecast").path("forecastday").elements();
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
    public JsonNode getWeatherFromAPI(String zipCode) throws IOException, InterruptedException{

        // validate basic format of zip code
        if (!validateZipCode(zipCode)) {
            throw new IllegalArgumentException("Invalid zip code");
        }

        String paramString = "?key=" + API_KEY + "&q=" + zipCode + "&days=10";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEATHERAPI_URL+paramString))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonBody = response.body();
        JsonNode jsonNode = readJsonBody(jsonBody);

        if (response.statusCode() == 400) {
            String errorMessage = jsonNode.path("error").path("message").asText();
            throw new IllegalArgumentException(errorMessage);
        }

        return jsonNode;
    }

    public JsonNode readJsonBody(String jsonBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonBody);
    }

    public boolean validateZipCode(String zipCode) {
        if (zipCode == null || zipCode.isEmpty()) {
            return false;
        }

        return zipCode.matches(ZIPCODE_REGEX_PATTERN);
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
