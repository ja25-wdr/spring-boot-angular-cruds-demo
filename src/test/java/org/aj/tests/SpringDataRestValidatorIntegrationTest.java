package org.aj.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aj.web.SpringDataRestApplication;
import org.aj.web.models.WebSiteStory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SpringDataRestValidatorIntegrationTest {
    public static final String URL = "http://localhost";
    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void whenStartingApplication_thenCorrectStatusCode() throws Exception {
        mockMvc.perform(get("/stories")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void whenAddingNewCorrectStory_thenCorrectStatusCodeAndResponse() throws Exception {
        WebSiteStory story = new WebSiteStory();
        story.setTitle("AJ Title 1");
        story.setContents("Amit John Story Contents");

        mockMvc.perform(post("/stories", story).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(story))).andExpect(status().is2xxSuccessful()).andExpect(redirectedUrl("http://localhost/stories/1"));
    }

    @Test
    public void whenAddingNewUserWithoutTitle_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteStory user = new WebSiteStory();


        mockMvc.perform(post("/stories", user).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user))).andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewUserWithEmptyContents_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteStory story = new WebSiteStory();
        story.setTitle("AJ Title 1");
        mockMvc.perform(post("/stories", story).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(story))).andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewStoryWithoutTitle_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteStory story = new WebSiteStory();
        story.setContents("Contents 1");

        mockMvc.perform(post("/stories", story).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(story))).andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewStoryWithEmptyTitle_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteStory story = new WebSiteStory();
        story.setTitle("");
        story.setContents("Contents 1");
        mockMvc.perform(post("/stories", story).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(story))).andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void whenDeletingCorrectStory_thenCorrectStatusCodeAndResponse() throws Exception {
        WebSiteStory story = new WebSiteStory();
        story.setTitle("AJ Title 1");
        story.setContents("Amit John Story Contents");
        mockMvc.perform(post("/stories", story).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(story))).andExpect(status().is2xxSuccessful()).andExpect(redirectedUrl("http://localhost/stories/1"));
        mockMvc.perform(delete("/stories/1").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(story))).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void whenSearchingByTitle_thenCorrectStatusCodeAndResponse() throws Exception {
        WebSiteStory story = new WebSiteStory();
        story.setTitle("AJ Title 1");
        story.setContents("Amit John Story Contents");
        mockMvc.perform(post("/stories", story).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(story))).andExpect(status().is2xxSuccessful()).andExpect(redirectedUrl("http://localhost/stories/1"));
        mockMvc.perform(get("/stories/search/byTitle").param("title", story.getTitle()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void whenSearchingByTitleWithOriginalMethodName_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteStory story = new WebSiteStory();
        story.setTitle("AJ Title 1");
        story.setContents("Amit John Story Contents");
//        mockMvc.perform(post("/stories", story).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(story))).andExpect(status().is2xxSuccessful()).andExpect(redirectedUrl("http://localhost/stories/1"));
        mockMvc.perform(get("/stories/search/findByTitle").param("title", story.getTitle()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
}
