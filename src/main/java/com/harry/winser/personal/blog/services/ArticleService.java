package com.harry.winser.personal.blog.services;

import com.harry.winser.personal.blog.services.client.Article;
import com.harry.winser.personal.blog.services.client.ArticleContainer;

public interface ArticleService {

    Article getArticleByName(String name);

    ArticleContainer getAllArticles();

}
