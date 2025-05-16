package org.aj.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.ja.web.SpringDataRestApplication;
import org.ja.web.models.WebSiteBlog;
import org.ja.web.repositories.BlogRepository;

@SpringBootTest(classes = SpringDataRestApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)

public class SpringDataLiveTest {
    private static final String STORY_ENDPOINT = "http://localhost:8080/blogs";

    @Autowired
    private BlogRepository blogRepo;

    @BeforeEach
    public void setup() {
        if (blogRepo.findById(1L).isEmpty()) {
            WebSiteBlog blog = new WebSiteBlog();
            blog.setTitle("New Blog Title 1");
            blog.setContents("New Blog Content 1");
            blog = blogRepo.save(blog);
        }
    }

    @Test
    public void whenGetBlog_thenOK() {
        final Response response = RestAssured.get(STORY_ENDPOINT + "/1");

        assertEquals(200, response.getStatusCode());
        assertTrue(response.asString().contains("New Blog Title 1"));
        assertTrue(response.asString().contains("New Blog Content 1"));
        // System.out.println(response.asString());
    }

//    @Test
//    public void whenGetBlogProjection_thenOK() {
//        final Response response = RestAssured.get(BOOK_ENDPOINT + "/1?projection=customBook");
//
//        assertEquals(200, response.getStatusCode());
//        assertFalse(response.asString().contains("isbn"));
//        assertTrue(response.asString().contains("authorCount"));
//        // System.out.println(response.asString());
//    }

    @Test
    public void whenGetAllBooks_thenOK() {
        final Response response = RestAssured.get(STORY_ENDPOINT);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.asString().contains("New Blog Title 1"));
        assertTrue(response.asString().contains("New Blog Content 1"));
        // System.out.println(response.asString());
    }

}
