package org.ja.blogs.cruds.application.entities;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String contents;
    @Lob
    private String image;

    public Blog(String title, String contents, String image) {
        this.title = title;
        this.contents = contents;
        this.image = image;
    }

}
