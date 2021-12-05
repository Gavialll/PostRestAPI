package com.example.serverpost.model;

import com.example.serverpost.exception.post.PostPriceException;
import com.example.serverpost.exception.post.PostValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PostTest {
    private final String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Test
    void setCategory() {
        Post post = new Post();
        post.setCategory(2L);
        assertEquals(2L, post.getCategory());

        post.setCategory(null);
        assertEquals(0, post.getCategory());
    }

    @Test
    void setPrice() {
        Post post = new Post();
        post.setPrice(100);
        assertEquals(100, post.getPrice());

        assertThrows(PostPriceException.class, () -> post.setPrice(null));

        assertThrows(PostPriceException.class, () -> post.setPrice(-1));
    }

    @Test
    void setName() {
        Post post = new Post();

        post.setName(null);
        assertEquals("", post.getName());

        Random random = new Random();
        StringBuilder name = new StringBuilder();

        for(int i = 0; i < 102; i++) {
            char letter = abc.charAt(random.nextInt(abc.length()));
            name.append(letter);
        }

        assertThrows(PostValidationException.class, () -> post.setName(name.toString()));
    }

    @Test
    void setDescription() {
        Post post = new Post();

        post.setDescription(null);
        assertEquals("", post.getDescription());

        Random random = new Random();
        StringBuilder description = new StringBuilder();

        for(int i = 0; i < 510; i++) {
            char letter = abc.charAt(random.nextInt(abc.length()));
            description.append(letter);
        }

        assertThrows(PostValidationException.class, () -> post.setDescription(description.toString()));
    }
}