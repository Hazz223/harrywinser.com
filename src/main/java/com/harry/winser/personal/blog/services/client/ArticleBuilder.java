package com.harry.winser.personal.blog.services.client;

import java.util.Date;

public class ArticleBuilder {
    private Long id;
    //date: "2016-04-06 21:20"
    private Date date;
    private String type;
    private String title;
    private String data;
    private String cleanTitle;

    private ArticleBuilder() {
    }

    public static ArticleBuilder anArticle() {
        return new ArticleBuilder();
    }

    public ArticleBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ArticleBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    public ArticleBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public ArticleBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder withData(String data) {
        this.data = data;
        return this;
    }

    public ArticleBuilder withCleanTitle(String cleanTitle) {
        this.cleanTitle = cleanTitle;
        return this;
    }

    public ArticleBuilder but() {
        return anArticle().withId(id).withDate(date).withType(type).withTitle(title).withData(data).withCleanTitle(cleanTitle);
    }

    public Article build() {
        Article article = new Article();
        article.setId(id);
        article.setDate(date);
        article.setType(type);
        article.setTitle(title);
        article.setData(data);
        article.setCleanTitle(cleanTitle);
        return article;
    }
}
