package org.ja.blogs.cruds.application.controllers;

import com.querydsl.core.types.Predicate;

import org.ja.blogs.cruds.application.entities.Blog;
import org.ja.blogs.cruds.application.repositories.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    private final BlogRepository blogRepository;

    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @GetMapping("/blogs/{id}")
    public Blog findBlogById(@PathVariable("id") Blog Blog) {
        return Blog;
    }

    @GetMapping("/blogs")
    public Page<Blog> findAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @GetMapping("/sortedblogs")
    public Page<Blog> findAllBlogsSortedByTitle() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("title"));
        return blogRepository.findAll(pageable);
    }

    @GetMapping("/filteredblogs")
    public Iterable<Blog> getBlogsByQuerydslPredicate(@QuerydslPredicate(root = Blog.class) Predicate predicate) {
        return blogRepository.findAll(predicate);
    }

}

