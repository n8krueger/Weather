import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Nested
    class MenuOptionsTests {

        @Test
        void validateMenuInput_validInput() {
            assertTrue(Main.validateMenuInput("0"));
        }

        @Test
        void validateMenuInput_invalidInput_notInRange() {
            assertFalse(Main.validateMenuInput("5"));
        }

        @Test
        void validateMenuInput_invalidInput_nonnumeric() {
            assertFalse(Main.validateMenuInput("a"));
        }
    }

    @Nested
    class ParseArgumentsTests {

        @Test
        void parseArguments_validInput_zipCodeCurrent() {
            String[] args = {"-z","28412","-c"};

            Map<String, String> expected = new HashMap<>();
            expected.put("zip","28412");
            expected.put("option","1");

            Map<String, String> actual = Main.parseArguments(args);

            assertEquals(expected,actual);
        }

        @Test
        void parseArguments_validInput_zipCodeForecast() {
            String[] args = {"-z","28412","-f"};

            Map<String, String> expected = new HashMap<>();
            expected.put("zip","28412");
            expected.put("option","2");

            Map<String, String> actual = Main.parseArguments(args);

            assertEquals(expected,actual);
        }

        @Test
        void parseArguments_invalidInput_oneArg() {
            String[] args = {"-z"};
            assertThrows(IllegalArgumentException.class, () -> Main.parseArguments(args));
        }

        @Test
        void parseArguments_invalidInput_fourArgs() {
            String[] args = {"-z", "28412", "-c", "-f"};
            assertThrows(IllegalArgumentException.class, () -> Main.parseArguments(args));
        }

        @Test
        void parseArguments_invalidInput_unknownArg() {
            String[] args = {"z"};
            assertThrows(IllegalArgumentException.class, () -> Main.parseArguments(args));
        }

    }

}