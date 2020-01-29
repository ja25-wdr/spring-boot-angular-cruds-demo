package org.aj.web.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class WebSiteStory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String contents;
    @Lob
    private String image;


}
