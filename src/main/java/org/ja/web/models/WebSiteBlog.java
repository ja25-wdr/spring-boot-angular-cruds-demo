package org.ja.web.models;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
public class WebSiteBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String contents;
    @Lob
    private String image;


}
