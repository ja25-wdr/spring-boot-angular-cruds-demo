package org.aj.stories.cruds.application.controllers;

import com.querydsl.core.types.Predicate;
import org.aj.stories.cruds.application.entities.Story;
import org.aj.stories.cruds.application.repositories.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoryController {

    private final StoryRepository storyRepository;

    @Autowired
    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @GetMapping("/stories/{id}")
    public Story findStoryById(@PathVariable("id") Story Story) {
        return Story;
    }

    @GetMapping("/stories")
    public Page<Story> findAllStories(Pageable pageable) {
        return storyRepository.findAll(pageable);
    }

    @GetMapping("/sortedstories")
    public Page<Story> findAllStoriesSortedByTitle() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("title"));
        return storyRepository.findAll(pageable);
    }

    @GetMapping("/filteredstories")
    public Iterable<Story> getStoriesByQuerydslPredicate(@QuerydslPredicate(root = Story.class) Predicate predicate) {
        return storyRepository.findAll(predicate);
    }

}

