import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static String zipcode = "";
    private static Weather weather;

    public static void main(String[] args) {
        weather = new Weather();

        boolean running = true;

        while (running) {
            System.out.print("Enter your zipcode: ");
            zipcode = getInput();

            if (validateZipcode(zipcode)) {
                running = menu();
            }
            else {
                System.out.println("Invalid zipcode, please try again.");
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
                        weather.getCurrentWeather(zipcode);
                        break;
                    // get 10-day forecast
                    case "2":
                        weather.getTenDayForecast(zipcode);
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
        System.out.println("3. Enter new zipcode");
        System.out.println("0. Quit");
        System.out.print("Enter your choice: ");
    }

    private static String getInput() {
        while(!SCANNER.hasNext()) {
            SCANNER.next();
        }

        return SCANNER.next();
    }

    public static boolean validateZipcode(String sZipcode) {
        try {
            int zipcode = Integer.parseInt(sZipcode);
            if (zipcode<501||zipcode>99734) {
                // zipcode was outside range
                return false;
            }
        }
        catch (NumberFormatException e) {
            // zipcode was not numeric
            return false;
        }

        // else zipcode valid
        return true;
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
