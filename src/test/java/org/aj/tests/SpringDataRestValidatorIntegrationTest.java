package org.aj.tests;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.ja.web.SpringDataRestApplication;
import org.ja.web.models.WebSiteBlog;

@SpringBootTest(classes = SpringDataRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SpringDataRestValidatorIntegrationTest {
    public static final String URL = "http://localhost";
    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void whenStartingApplication_thenCorrectStatusCode() throws Exception {
        mockMvc.perform(get("/blogs")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void whenAddingNewCorrectBlog_thenCorrectStatusCodeAndResponse() throws Exception {
        WebSiteBlog blog = new WebSiteBlog();
        blog.setTitle("AJ Title 1");
        blog.setContents("Amit John Blog Contents");

        mockMvc.perform(post("/blogs", blog).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog))).andExpect(status().is2xxSuccessful()).andExpect(redirectedUrl("http://localhost/blogs/1"));
    }

    @Test
    public void whenAddingNewUserWithoutTitle_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteBlog user = new WebSiteBlog();


        mockMvc.perform(post("/blogs", user).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user))).andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewUserWithEmptyContents_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteBlog blog = new WebSiteBlog();
        blog.setTitle("AJ Title 1");
        mockMvc.perform(post("/blogs", blog).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog))).andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewBlogWithoutTitle_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteBlog blog = new WebSiteBlog();
        blog.setContents("Contents 1");

        mockMvc.perform(post("/blogs", blog).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog))).andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewBlogWithEmptyTitle_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteBlog blog = new WebSiteBlog();
        blog.setTitle("");
        blog.setContents("Contents 1");
        mockMvc.perform(post("/blogs", blog).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog))).andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void whenDeletingCorrectBlog_thenCorrectStatusCodeAndResponse() throws Exception {
        WebSiteBlog blog = new WebSiteBlog();
        blog.setTitle("AJ Title 1");
        blog.setContents("Amit John Blog Contents");
        mockMvc.perform(post("/blogs", blog).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog))).andExpect(status().is2xxSuccessful()).andExpect(redirectedUrl("http://localhost/blogs/1"));
        mockMvc.perform(delete("/blogs/1").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog))).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void whenSearchingByTitle_thenCorrectStatusCodeAndResponse() throws Exception {
        WebSiteBlog blog = new WebSiteBlog();
        blog.setTitle("AJ Title 1");
        blog.setContents("Amit John Blog Contents");
        mockMvc.perform(post("/blogs", blog).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog))).andExpect(status().is2xxSuccessful()).andExpect(redirectedUrl("http://localhost/blogs/1"));
        mockMvc.perform(get("/blogs/search/byTitle").param("title", blog.getTitle()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void whenSearchingByTitleWithOriginalMethodName_thenErrorStatusCodeAndResponse() throws Exception {
        WebSiteBlog blog = new WebSiteBlog();
        blog.setTitle("AJ Title 1");
        blog.setContents("Amit John Blog Contents");
//        mockMvc.perform(post("/blogs", blog).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog))).andExpect(status().is2xxSuccessful()).andExpect(redirectedUrl("http://localhost/blogs/1"));
        mockMvc.perform(get("/blogs/search/findByTitle").param("title", blog.getTitle()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
}
