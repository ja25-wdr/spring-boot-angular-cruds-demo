package org.aj.web.repositories;

import org.aj.web.models.WebSiteStory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "stories", path = "stories")
public interface StoryRepository extends CrudRepository<WebSiteStory, Long> {

    @Override
    @RestResource(exported = false)
    void delete(WebSiteStory entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends WebSiteStory> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @RestResource(path = "byTitle", rel = "customFindMethod")
    WebSiteStory findByTitle(@Param("title") String title);
}
