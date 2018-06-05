package com.rajatcube.popularmovies.model;

import java.io.Serializable;

/**
 * Created by Rajat Kumar Gupta on 05/06/2018.
 */
public class ReviewResult {
    private String author;
    private String content;
    private String  id;
    private String url;

    public ReviewResult(String author, String content, String id, String url) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
    }
    public ReviewResult(String author, String content) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
