package com.wspa.pa1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

    private String article_url;
    private String title;
    private String description;
    private String published_datetime;
    private String source_name;

    public Article() {
    }

    public String getArticle_url() {
        return article_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublished_datetime() {
        return published_datetime;
    }

    public String getSource_name() {
        return source_name;
    }

    @Override
    public String toString() {
        return "Article{" +
                "article_url='" + article_url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", published_datetime='" + published_datetime + '\'' +
                ", source_name='" + source_name + '\'' +
                '}';
    }

    public String representation() {
        return String.format("Source: %s | Title: %s | Description: %s \n", source_name, title, description);
    }
}
