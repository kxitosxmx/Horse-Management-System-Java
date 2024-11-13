package org.example.javacwui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

class HorseControllerTest {
    @Test
    public void testValidateHorseIdValid() {
        Assertions.assertTrue(HorseController.validateHorseId("123"));
    }

    @Test
    public void testValidateHorseIdInvalid() {
        Assertions.assertFalse(HorseController.validateHorseId("abc"));
    }

    @Test
    public void testValidateNameValid() {
        Assertions.assertTrue(HorseController.validateName("Secretariat"));
    }

    @Test
    public void testValidateNameInvalid() {
        Assertions.assertFalse(HorseController.validateName("123"));
    }

    @Test
    public void testValidateJockeyValid() {
        Assertions.assertTrue(HorseController.validateJockey("John Smith"));
    }

    @Test
    public void testValidateJockeyInvalid() {
        Assertions.assertFalse(HorseController.validateJockey("123"));
    }

    @Test
    public void testValidateAgeValid() {
        Assertions.assertTrue(HorseController.validateAge("5"));
    }

    @Test
    public void testValidateAgeInvalid() {
        Assertions.assertFalse(HorseController.validateAge("five"));
    }

    @Test
    public void testValidateBreedValid() {
        Assertions.assertTrue(HorseController.validateBreed("Thoroughbred"));
    }

    @Test
    public void testValidateBreedInvalid() {
        Assertions.assertFalse(HorseController.validateBreed("123"));
    }

    @Test
    public void testValidateRacesWonValid() {
        Assertions.assertTrue(HorseController.validateRacesWon("10"));
    }

    @Test
    public void testValidateRacesWonInvalid() {
        Assertions.assertFalse(HorseController.validateRacesWon("ten"));
    }

    @Test
    public void testValidateTotalRacesValid() {
        Assertions.assertTrue(HorseController.validateTotalRaces("20"));
    }

    @Test
    public void testValidateTotalRacesInvalid() {
        Assertions.assertFalse(HorseController.validateTotalRaces("twenty"));
    }
}