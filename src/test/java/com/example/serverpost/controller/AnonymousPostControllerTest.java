package com.example.serverpost.controller;

import com.example.serverpost.dto.PostDto;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AnonymousPostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private AnonymousPostController anonymousPostController;

    private final Post post3 = new Post();
    private final Post post1 = new Post();
    private final Post post2 = new Post();
    private final Post post4 = new Post();

    @BeforeEach
    public void before (){
        post3.setPrice(3);
        post3.setName("b");
        post3.setDate(LocalDateTime.now());

        post1.setPrice(1);
        post1.setName("a");
        post1.setDate(LocalDateTime.now());

        post2.setPrice(2);
        post2.setName("c");
        post2.setDate(LocalDateTime.now());

        post4.setPrice(4);
        post4.setName("d");
        post4.setDate(LocalDateTime.now());
        List<Post> list = List.of(post2, post1, post4, post3, post1, post4);
        Mockito.when(postService.getAllPost()).thenReturn(list);
    }

    @Test
    void getByRangePrice() {
        List<PostDto> listOne = anonymousPostController.getByRangePrice(1,3);
        listOne.sort(Comparator.comparing(PostDto::getPrice));

        assertEquals(1, listOne.get(0).getPrice());
        assertEquals(2, listOne.get(2).getPrice());


        List<PostDto> listTwo = anonymousPostController.getByRangePrice(4,56);
        listTwo.sort(Comparator.comparing(PostDto::getPrice));

        assertEquals(4, listTwo.get(0).getPrice());
        assertEquals(4, listTwo.get(1).getPrice());
    }

    @Test
    void sort() {
        List<PostDto> listByName = anonymousPostController.sort("name");
        assertEquals("a", listByName.get(0).getName());
        assertEquals("d", listByName.get(5).getName());

        List<PostDto> listByPrice = anonymousPostController.sort("price");
        assertEquals(1, listByPrice.get(0).getPrice());
        assertEquals(4, listByPrice.get(5).getPrice());

        List<PostDto> listByDefault = anonymousPostController.sort("");
        assertEquals(1, listByDefault.get(0).getPrice());
        assertEquals(4, listByDefault.get(5).getPrice());

        List<PostDto> listByDate = anonymousPostController.sort("date");
        assertEquals(post3.getDate(), listByDate.get(0).getDate());
        assertEquals(post4.getDate(), listByDate.get(5).getDate());
    }
}