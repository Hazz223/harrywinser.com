package com.harry.winser.personal.blog.services.client;


import com.harry.winser.personal.blog.services.Article;
import com.harry.winser.personal.blog.services.ArticleDto;

public interface ArticleClient {

    Article findByName(String name);
    ArticleDto findByType(String type);

}
