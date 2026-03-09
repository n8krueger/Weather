import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        //Weather weather = new Weather();

        boolean running = true;

        while (running) {
            System.out.print("Enter your zipcode: ");
            String zipcode = getInput();

            if (validateZipcode(zipcode)) {
                showMenu();
            }
            else {
                System.out.println("Invalid zipcode, please try again.");
            }
        }

        scanner.close();
    }

    public static void subMenu() {
        boolean back = false;

        while (!back) {
            showMenu();
            int choice = getInput();


        }
    }

    private static void showMenu() {
        System.out.println("---- Menu ----");
        System.out.println("1. Get current weather");
        System.out.println("2. Get 5-day forecast");
        System.out.println("0. Enter new zipcode");
        System.out.print("Enter your choice: ");
    }

    private static String getInput() {
        while(!scanner.hasNext()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }

        return scanner.next();
    }

    public static boolean validateZipcode(String zipcode) {


        if (Integer.parseInt(zipcode) < 501 || Integer.parseInt(zipcode) > 99734) {
            return true;
        }
        return false;
    }
}
