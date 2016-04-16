package com.harry.winser.personal.blog.services;

public interface ArticleService {

    Article getArticleByName(String name);

    ArticleDto getAllArticles();

}
