package com.harry.winser.personal.blog.services.client;


public interface ArticleClient {

    Article findByName(String name);
    ArticleContainer findByType(String type);

}
