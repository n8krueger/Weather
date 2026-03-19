import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

}