package com.harry.winser.personal.blog.services;

import com.google.common.collect.Lists;
import com.harry.winser.personal.blog.services.client.ArticleClient;
import com.harry.winser.personal.blog.web.exceptions.InternalServerErrorException;
import com.harry.winser.personal.blog.web.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleClient articleClient;

    @Autowired
    public ArticleServiceImpl(ArticleClient articleClient) {

        this.articleClient = articleClient;
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
    public ArticleDto getAllArticles() {

        ArticleDto reviews = this.articleClient.findByType("review");
        ArticleDto blogs = this.articleClient.findByType("blog");


        return this.mergeArticleDtos(reviews, blogs);
    }

    private ArticleDto mergeArticleDtos(ArticleDto first, ArticleDto second){

        List<Article> firstContent = first.getContent();
        List<Article> secondContent = second.getContent();
        List<Article> articles = new ArrayList<>();
        articles.addAll(firstContent);
        articles.addAll(secondContent);

        ArticleDto result = new ArticleDto();
        result.setContent(articles);
        result.setTotalPages(first.getTotalPages() + second.getTotalPages());
        result.setTotalElements(first.getTotalElements() + second.getTotalElements());
        result.setNumberOfElements(articles.size());
        result.setFirst(first.getFirst() || second.getFirst() ? true : false);
        result.setLast(first.getLast() && second.getLast() ? true : false);
        result.setNumber(first.getNumber() <= second.getNumber() ? first.getNumber() : second.getNumber());

        return result;
    }
}
