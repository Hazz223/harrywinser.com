package com.harry.winser.personal.blog.services.client;


import com.harry.winser.personal.blog.services.ArticleDto;

public interface ArticleClient {

    ArticleDto findByType(String type);

}
