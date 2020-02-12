package org.aj.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.aj.web.SpringDataRestApplication;
import org.aj.web.models.WebSiteStory;
import org.aj.web.repositories.StoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRestApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)

public class SpringDataLiveTest {
    private static final String STORY_ENDPOINT = "http://localhost:8080/stories";

    @Autowired
    private StoryRepository storyRepo;

    @Before
    public void setup() {
        if (!storyRepo.findById(1L).isPresent()) {
            WebSiteStory story = new WebSiteStory();
            story.setTitle("New Story Title 1");
            story.setContents("New Story Content 1");
            story = storyRepo.save(story);
        }
    }

    @Test
    public void whenGetStory_thenOK() {
        final Response response = RestAssured.get(STORY_ENDPOINT + "/1");

        assertEquals(200, response.getStatusCode());
        assertTrue(response.asString().contains("New Story Title 1"));
        assertTrue(response.asString().contains("New Story Content 1"));
        // System.out.println(response.asString());
    }

//    @Test
//    public void whenGetStoryProjection_thenOK() {
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
        assertTrue(response.asString().contains("New Story Title 1"));
        assertTrue(response.asString().contains("New Story Content 1"));
        // System.out.println(response.asString());
    }

}
