package org.skywise.skywise;

import org.junit.jupiter.api.Test;
import org.skywise.skywise.services.ValidatorService;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ValidatorServiceTests {
    private final ValidatorService validatorService = new ValidatorService();

    @Test
    void testValidateFlightParameters() {

        assertTrue(validatorService.validateFlightParameters(1, 2), "Should be true because");
        assertFalse(validatorService.validateFlightParameters(1, 1), "Should be false because");
    }
}
