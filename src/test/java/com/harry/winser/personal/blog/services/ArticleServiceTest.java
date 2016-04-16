package com.harry.winser.personal.blog.services;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.harry.winser.personal.blog.services.client.ArticleClient;
import com.harry.winser.personal.blog.services.client.ArticleClientImpl;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

public class ArticleServiceTest {


    private static final String LOCALHOST = "localhost";
    private static final String PORT = "9001";

    private ArticleClient client = new ArticleClientImpl(LOCALHOST, PORT, new RestTemplate());
    private ArticleService articleService = new ArticleServiceImpl(client);

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9001));

    @Test
    public void shouldGetBlogAndReviews(){

        ArticleDto allArticles = this.articleService.getAllArticles();

        assertThat(allArticles.getContent().size()).isEqualTo(2);
    }
}
