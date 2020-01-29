package org.aj.web.config;

import org.aj.web.models.WebSiteStory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration) {
//        repositoryRestConfiguration.getProjectionConfiguration().addProjection(CustomBook.class);
        ExposureConfiguration config = repositoryRestConfiguration.getExposureConfiguration();
        config.forDomainType(WebSiteStory.class).withItemExposure((metadata, httpMethods) -> httpMethods.enable(HttpMethod.values()));

//        config.forDomainType(WebSiteStory.class).withItemExposure((metadata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));
    }
}
