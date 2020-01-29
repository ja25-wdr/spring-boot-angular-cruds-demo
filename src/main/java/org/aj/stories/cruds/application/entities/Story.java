package org.aj.stories.cruds.application.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String contents;
    @Lob
    private String image;

    public Story(String title, String contents, String image) {
        this.title = title;
        this.contents = contents;
        this.image = image;
    }

}
