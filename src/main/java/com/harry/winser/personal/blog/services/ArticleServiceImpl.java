package com.harry.winser.personal.blog.services;

import com.harry.winser.personal.blog.services.client.Article;
import com.harry.winser.personal.blog.services.client.ArticleClient;
import com.harry.winser.personal.blog.services.client.ArticleContainer;
import com.harry.winser.personal.blog.services.client.ArticleType;
import com.harry.winser.personal.blog.web.exceptions.InternalServerErrorException;
import com.harry.winser.personal.blog.web.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleClient articleClient;
    private ArticleContainerMerger articleContainerMerger;

    @Autowired
    public ArticleServiceImpl(ArticleClient articleClient) {

        this.articleClient = articleClient;
        this.articleContainerMerger = new ArticleContainerMerger();
    }

    @Override
    public Article getArticleByName(String name) {

        try{
            return this.articleClient.findByName(name);
        }catch(HttpClientErrorException | HttpServerErrorException ex){

            HttpStatus statusCode = ex.getStatusCode();

            if(HttpStatus.NOT_FOUND == statusCode){
                throw new NotFoundException("Could not find Article with name: " + name);
            }

            throw new InternalServerErrorException("Something went wrong contact the Api service. It returned a " + statusCode + " status code.");
        }
    }

    @Override
    public ArticleContainer getAllArticles() {

        try{
            ArticleContainer all = this.articleClient.findAll();

            return all;
        }catch(HttpClientErrorException | HttpServerErrorException ex){

            //todo logging
            throw new InternalServerErrorException("Something went wrong contact the Api service. It returned a " + ex.getStatusCode() + " status code.");
        }
    }

    @Override
    public ArticleContainer getArticleByType(ArticleType type) {
        return this.articleClient.findByType(type.toString());
    }

}
