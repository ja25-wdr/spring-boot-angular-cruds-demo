package org.aj.stories.cruds.application.repositories;

import org.aj.stories.cruds.application.entities.Story;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends PagingAndSortingRepository<Story, Long>, QuerydslPredicateExecutor<Story> {
}