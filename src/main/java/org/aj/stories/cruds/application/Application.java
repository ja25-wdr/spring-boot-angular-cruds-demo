package org.aj.stories.cruds.application;

import org.aj.stories.cruds.application.entities.Story;
import org.aj.stories.cruds.application.repositories.StoryRepository;
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
    CommandLineRunner initialize(StoryRepository storyRepo) {
        return args -> {
            Story story = new Story("Title 1", "Story 1", null);
            storyRepo.save(story);
            storyRepo.findAll().forEach(System.out::println);
        };
    }
}
