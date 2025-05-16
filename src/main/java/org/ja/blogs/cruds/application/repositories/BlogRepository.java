package org.ja.blogs.cruds.application.repositories;

import org.ja.blogs.cruds.application.entities.Blog;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long>, PagingAndSortingRepository<Blog, Long>, QuerydslPredicateExecutor<Blog> {
}