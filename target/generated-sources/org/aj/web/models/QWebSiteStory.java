package org.aj.web.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebSiteStory is a Querydsl query type for WebSiteStory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWebSiteStory extends EntityPathBase<WebSiteStory> {

    private static final long serialVersionUID = 1498993417L;

    public static final QWebSiteStory webSiteStory = new QWebSiteStory("webSiteStory");

    public final StringPath contents = createString("contents");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath title = createString("title");

    public QWebSiteStory(String variable) {
        super(WebSiteStory.class, forVariable(variable));
    }

    public QWebSiteStory(Path<? extends WebSiteStory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebSiteStory(PathMetadata metadata) {
        super(WebSiteStory.class, metadata);
    }

}

