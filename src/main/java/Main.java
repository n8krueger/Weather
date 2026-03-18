import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static Weather weather;
    private static JsonNode weatherJson;

    public static void main(String[] args) throws IOException, InterruptedException {
        weather = new Weather();

        boolean running = true;

        while (running) {
            System.out.print("Enter your zipcode: ");
            String zipCode = getInput();

            try {
                weatherJson = weather.getWeatherFromAPI(zipCode);
                running = menu();
            }
            // catch illegal argument, print error message and retry loop
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }

        SCANNER.close();
    }

    public static boolean menu() {
        boolean back = false;

        while (!back) {
            showMenu();

            String choice = getInput();

            if(validateMenuInput(choice)) {
                switch (choice) {
                    // get current weather
                    case "1":
                        weather.getCurrentWeather(weatherJson);
                        break;
                    // get 10-day forecast
                    case "2":
                        weather.getTenDayForecast(weatherJson);
                        break;
                    // go back and enter new zipcode
                    case "3":
                        back = true;
                        break;
                    // exit
                    case "0":
                        return false;
                }
            }
            else {
                System.out.println("Invalid menu choice, please try again.");
            }

        }

        return true;
    }

    private static void showMenu() {
        System.out.println("---- Menu ----");
        System.out.println("1. Get current weather");
        System.out.println("2. Get 10-day forecast");
        System.out.println("3. Enter new zip code");
        System.out.println("0. Quit");
        System.out.print("Enter your choice: ");
    }

    private static String getInput() {
        while(!SCANNER.hasNext()) {
            SCANNER.next();
        }

        return SCANNER.next();
    }

    public static boolean validateMenuInput(String sChoice) {
        try {
            int choice = Integer.parseInt(sChoice);
            if (choice < 0 || choice > 3) {
                // choice was outside of range
                return false;
            }
        }
        catch (NumberFormatException e) {
            // choice was not numeric
            return  false;
        }
        return true;
    }

}
