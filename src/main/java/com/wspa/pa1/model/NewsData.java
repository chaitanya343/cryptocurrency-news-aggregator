package com.wspa.pa1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsData {

    @JsonProperty("articles")
    private List<Article> articles;

    public NewsData() {
    }

    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "articles=" + articles +
                '}';
    }
}
