import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Weather {
    Map<String, String> config;

    public Weather() throws IOException {
        loadConfig();
    }

    public Map<String, String> loadConfig() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("config.yml"));
        Yaml yaml = new Yaml();
        config = yaml.load(inputStream);
        return config;
    }

    public void getCurrentWeather(String zipcode) {
        String url = config.get("baseURL") + config.get("currentEndPt");
        String paramString = "?key=" + config.get("apiKey") + "&q=" + zipcode;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+paramString))
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
