package org.ja.web.repositories;

import org.ja.web.models.WebSiteBlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "blogs", path = "blogs")
public interface BlogRepository extends CrudRepository<WebSiteBlog, Long> {

    @Override
    @RestResource(exported = false)
    void delete(WebSiteBlog entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends WebSiteBlog> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @RestResource(path = "byTitle", rel = "customFindMethod")
    WebSiteBlog findByTitle(@Param("title") String title);
}
