package org.ja.blogs.cruds.application;

import org.ja.blogs.cruds.application.entities.Blog;
import org.ja.blogs.cruds.application.repositories.BlogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initialize(BlogRepository blogRepo) {
        return args -> {
            Blog blog = new Blog("Title 1", "Blog 1", null);
            blogRepo.save(blog);
            blogRepo.findAll().forEach(System.out::println);
        };
    }
}
