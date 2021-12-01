package com.example.serverpost.component;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ValidatorTest {

    @Test
    void telNumber() {
        assertTrue(Validator.telNumber("+380678755165"));
        assertTrue(Validator.telNumber("0678755165"));
        assertFalse(Validator.telNumber("+380678755"));
        assertFalse(Validator.telNumber("380678755165"));
        assertFalse(Validator.telNumber("80678755165"));
    }

    @Test
    void email() {
        assertTrue(Validator.email("andr@gmail.com"));
        assertFalse(Validator.email("andr@amli.ksad"));
        assertFalse(Validator.email(""));
        assertFalse(Validator.email("asasd"));
    }
}