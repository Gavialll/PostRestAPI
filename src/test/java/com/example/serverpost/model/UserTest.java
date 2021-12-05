package com.example.serverpost.model;

import com.example.serverpost.exception.user.UserValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserTest {
    private User user;

    @BeforeEach
    public void before(){
        user = new User();
    }

    @Test
    void setNumber() {
        user.setNumber("+380678755165");
        assertEquals("+380678755165", user.getNumber());

        user.setNumber("0678755165");
        assertEquals("0678755165", user.getNumber());

        assertThrows(UserValidationException.class, () -> user.setNumber("30678755165"));
        assertThrows(UserValidationException.class, () -> user.setNumber("80678755165"));
        assertThrows(UserValidationException.class, () -> user.setNumber("8067875sdbfbfs5165"));
        assertThrows(UserValidationException.class, () -> user.setNumber("8067875"));
    }

    @Test
    void setFirstName() {
        user.setFirstName("first name");
        assertEquals("first name", user.getFirstName());

        String message;
        message = assertThrows(UserValidationException.class, () -> user.setFirstName(""))
                .getMessage();
        assertEquals("First name empty", message);

        message = assertThrows(UserValidationException.class, () -> user.setFirstName("qwertyuiopsdfgasdfghjklgddszxcvbnmgdff"))
                .getMessage();
        assertEquals("Field exceeds 30 characters, first name = 38", message);
    }


    @Test
    void setLastName() {
        user.setLastName("last name");
        assertEquals("last name", user.getLastName());

        user.setLastName(null);
        assertEquals("", user.getLastName());

        String message = assertThrows(UserValidationException.class, () -> user.setLastName("qwertyuiopsdfgasdfghjklgddszxcvbnmgdff"))
                .getMessage();
        assertEquals("field exceeds 30 characters, last name = 38", message);
    }

    @Test
    void setLogin() {
        user.setLogin("login@email.com");
        assertEquals("login@email.com", user.getLogin());

        String message = assertThrows(UserValidationException.class, () -> user.setLogin("sdf"))
                .getMessage();
        assertEquals("Email not valid", message);
    }
}