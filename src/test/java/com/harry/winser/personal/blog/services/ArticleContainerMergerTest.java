package com.harry.winser.personal.blog.services;


import com.google.common.collect.Lists;
import com.harry.winser.personal.blog.services.client.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ArticleContainerMergerTest {

    private ArticleContainerMerger articleContainerMerger;
    private Article firstArticle;
    private Article secondArticle;

    @Before
    public void init(){

        this.articleContainerMerger = new ArticleContainerMerger();

        this.firstArticle = ArticleBuilder.anArticle()
                .withId(20L)
                .withCleanTitle("Pokemon_Red_and_Blue")
                .withData("The first in the series of Pokemon Games")
                .withDate(new Date())
                .withTitle("The first Pokemon games!")
                .withType(ArticleType.BLOG.toString())
                .build();

        this.secondArticle = ArticleBuilder.anArticle()
                .withId(21L)
                .withCleanTitle("Pokemon_Gold_and_Silver")
                .withData("The second in the series of Pokemon Games")
                .withDate(new Date())
                .withTitle("The second Pokemon games!")
                .withType(ArticleType.BLOG.toString())
                .build();
    }

    // Todo: move this code into the api server. Basically I'm working around a problem that should just be a single request
    @Test
    public void shouldReturnMergedArticleContainers(){

        ArticleContainer firstArticleContainer = ArticleContainerBuilder.anArticleContainer()
                .withContent(Lists.newArrayList(firstArticle))
                .withTotalElements(25)
                .withNumber(1) // the current page
                .withSize(1) // the size of each page
                .withFirst(false)
                .withTotalPages(25)
                .withLast(false)
                .withSort(null)
                .withNumberOfElements(1)
                .build();

        ArticleContainer secondArticleContainer = ArticleContainerBuilder.anArticleContainer()
                .withContent(Lists.newArrayList(secondArticle))
                .withTotalElements(10)
                .withNumber(0)
                .withSize(1)
                .withFirst(true)
                .withTotalPages(3)
                .withLast(false)
                .withSort(null)
                .withNumberOfElements(1)
                .build();

        ArticleContainer result = this.articleContainerMerger.merge(firstArticleContainer, secondArticleContainer);

        List<Article> articles = result.getContent();

        assertThat(articles).hasSameElementsAs(Lists.newArrayList(this.firstArticle, this.secondArticle));

        assertThat(result.getFirst()).isTrue();
        assertThat(result.getLast()).isFalse();
        assertThat(result.getNumberOfElements()).isEqualTo(2);
        assertThat(result.getSize()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(35);
        assertThat(result.getNumberOfElements()).isEqualTo(2);
    }

}
