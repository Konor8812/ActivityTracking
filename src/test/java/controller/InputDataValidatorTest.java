package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InputDataValidatorTest {

    @Test
    void validateLogin() {

        Assertions.assertTrue(InputDataValidator.validateLogin("ivor"));
        Assertions.assertFalse(InputDataValidator.validateLogin("ivor ranger"));

        Assertions.assertTrue(InputDataValidator.validateLogin("iv123an"));
        Assertions.assertFalse(InputDataValidator.validateLogin("ivan@i"));
    }

    @Test
    void validatePass() {
        Assertions.assertTrue(InputDataValidator.validatePass("ivor"));
        Assertions.assertFalse(InputDataValidator.validatePass("ivor ranger"));

        Assertions.assertTrue(InputDataValidator.validatePass("iv123an"));
        Assertions.assertTrue(InputDataValidator.validatePass("ivan@i"));
    }

    @Test
    void validateActivity() {

        boolean shouldBeValid = InputDataValidator.validateActivity("name", "5 hours", "description", "5.");
        boolean shouldBeInValid1 = InputDataValidator.validateActivity("name1", "5 hours", "description", "5.2");
        boolean shouldBeInValid2 = InputDataValidator.validateActivity("name", "5 hour", "description", "5.2");
        boolean shouldBeInValid3 = InputDataValidator.validateActivity("name", "5 hours", "description3", "5.2");
        boolean shouldBeInValid4 = InputDataValidator.validateActivity("name", "5 hours", "description", "five");


        Assertions.assertTrue(shouldBeValid);
        Assertions.assertFalse(shouldBeInValid1);
        Assertions.assertFalse(shouldBeInValid2);
        Assertions.assertFalse(shouldBeInValid3);
        Assertions.assertFalse(shouldBeInValid4);

    }
}