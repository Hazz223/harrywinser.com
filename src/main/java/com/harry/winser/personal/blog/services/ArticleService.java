package com.harry.winser.personal.blog.services;

import com.harry.winser.personal.blog.services.client.Article;
import com.harry.winser.personal.blog.services.client.ArticleContainer;
import com.harry.winser.personal.blog.services.client.ArticleType;

public interface ArticleService {

    Article getArticleByName(String name);

    ArticleContainer getBlogAndReviews();

    ArticleContainer getArticleByType(ArticleType articleType);

}
