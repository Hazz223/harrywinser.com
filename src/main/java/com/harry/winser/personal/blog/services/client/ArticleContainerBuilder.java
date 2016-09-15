package com.harry.winser.personal.blog.services.client;

import java.util.List;

/**
 * Created by Windfall on 17/04/2016.
 */
public class ArticleContainerBuilder {
    private List<Article> content;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean last;
    private String sort;
    private Boolean first;
    private Integer numberOfElements;
    private Integer size;
    private Integer number;

    private ArticleContainerBuilder() {
    }

    public static ArticleContainerBuilder anArticleContainer() {
        return new ArticleContainerBuilder();
    }

    public ArticleContainerBuilder withContent(List<Article> content) {
        this.content = content;
        return this;
    }

    public ArticleContainerBuilder withTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public ArticleContainerBuilder withTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public ArticleContainerBuilder withLast(Boolean last) {
        this.last = last;
        return this;
    }

    public ArticleContainerBuilder withSort(String sort) {
        this.sort = sort;
        return this;
    }

    public ArticleContainerBuilder withFirst(Boolean first) {
        this.first = first;
        return this;
    }

    public ArticleContainerBuilder withNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
        return this;
    }

    public ArticleContainerBuilder withSize(Integer size) {
        this.size = size;
        return this;
    }

    public ArticleContainerBuilder withNumber(Integer number) {
        this.number = number;
        return this;
    }

    public ArticleContainerBuilder but() {
        return anArticleContainer().withContent(content).withTotalElements(totalElements).withTotalPages(totalPages).withLast(last).withSort(sort).withFirst(first).withNumberOfElements(numberOfElements).withSize(size).withNumber(number);
    }

    public ArticleContainer build() {
        ArticleContainer articleContainer = new ArticleContainer();
        articleContainer.setContent(content);
        articleContainer.setTotalElements(totalElements);
        articleContainer.setTotalPages(totalPages);
        articleContainer.setLast(last);
        articleContainer.setSort(sort);
        articleContainer.setFirst(first);
        articleContainer.setNumberOfElements(numberOfElements);
        articleContainer.setSize(size);
        articleContainer.setNumber(number);
        return articleContainer;
    }
}
