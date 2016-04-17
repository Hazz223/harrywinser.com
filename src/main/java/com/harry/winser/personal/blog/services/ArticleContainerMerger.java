package com.harry.winser.personal.blog.services;

import com.harry.winser.personal.blog.services.client.Article;
import com.harry.winser.personal.blog.services.client.ArticleContainer;

import java.util.ArrayList;
import java.util.List;

public class ArticleContainerMerger {

    public ArticleContainer merge(ArticleContainer first, ArticleContainer second){

        List<Article> firstContent = first.getContent();
        List<Article> secondContent = second.getContent();
        List<Article> articles = new ArrayList<>();
        articles.addAll(firstContent);
        articles.addAll(secondContent);

        ArticleContainer result = new ArticleContainer();
        result.setContent(articles);
        result.setTotalPages(first.getTotalPages() + second.getTotalPages());
        result.setTotalElements(first.getTotalElements() + second.getTotalElements());
        result.setNumberOfElements(articles.size());
        result.setFirst(first.getFirst() || second.getFirst());
        result.setLast(first.getLast() && second.getLast());
        result.setNumber(first.getNumber() <= second.getNumber() ? first.getNumber() : second.getNumber());
        result.setSize(first.getSize());

        return result;
    }

}
