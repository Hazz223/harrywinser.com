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

            // Todo: add proper logging!!
            throw new InternalServerErrorException("Something went wrong contact the Api service. It returned a " + statusCode + " status code.");
        }
    }

    @Override
    public ArticleContainer getAllArticles() {

        try{
            ArticleContainer reviews = this.articleClient.findByType(ArticleType.REVIEW.toString());
            ArticleContainer blogs = this.articleClient.findByType(ArticleType.BLOG.toString());

            return this.articleContainerMerger.merge(reviews, blogs);
        }catch(HttpClientErrorException | HttpServerErrorException ex){

            //todo logging
            throw new InternalServerErrorException("Something went wrong contact the Api service. It returned a " + ex.getStatusCode() + " status code.");
        }
    }

    // This needs proper testing
    @Override
    public ArticleContainer getArticleByType(ArticleType type) {
        return this.articleClient.findByType(type.toString());
    }


}
