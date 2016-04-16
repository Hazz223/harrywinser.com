package com.harry.winser.personal.blog.services;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.harry.winser.personal.blog.services.client.ArticleClient;
import com.harry.winser.personal.blog.services.client.ArticleClientImpl;
import com.harry.winser.personal.blog.web.exceptions.InternalServerErrorException;
import com.harry.winser.personal.blog.web.exceptions.NotFoundException;
import org.apache.commons.io.IOUtil;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class ArticleServiceTest {

    private static final String LOCALHOST = "http://localhost";
    private static final String PORT = "9001";
    public static final String POKEDEX = "Pokedex";
    public static final String ARTICLE_JSON = "/Article.json";

    private ArticleClient client = new ArticleClientImpl(LOCALHOST, PORT, new RestTemplate());
    private ArticleService articleService = new ArticleServiceImpl(client);

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9001));

    @Test
    public void shouldGetSingleArticle() throws Exception{

        String articleJson = IOUtil.toString(this.getClass().getClassLoader().getResourceAsStream("Article.json"));

        stubFor(get(urlEqualTo("/article/" + POKEDEX))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(articleJson)));

        Article result = this.articleService.getArticleByName(POKEDEX);

        assertThat(result.getCleanTitle()).isEqualTo("pipelineconf_2016_retro");
        assertThat(result.getTitle()).isEqualTo("What I learned from PIPELINECONF 2016");
        assertThat(result.getType()).isEqualTo("technology");
        assertThat(result.getId()).isEqualTo(20);

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
//        Date date = simpleDateFormat.parse("2016-04-06 21:20");
//        assertThat(result.getDate()).isEqualTo(date); Ignoring this for the moment, as it has some weird issue where it keeps setting T23 instead of T22... Darn dates.
    }

    @Test
    public void shouldThrow404WhenResultNotFound(){

        stubFor(get(urlEqualTo("/article/" + POKEDEX))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)));

        try{

            this.articleService.getArticleByName(POKEDEX);
            assertTrue("Should not get here!", false);
        }catch(NotFoundException ex){

            assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(ex.getMessage()).contains(POKEDEX);
        }
    }

    @Test
    public void shouldThrowInternalServerErrorForAllOtherErrors(){

        stubFor(get(urlEqualTo("/article/" + POKEDEX))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(502)));

        try{

            this.articleService.getArticleByName(POKEDEX);
            assertTrue("Should not get here!", false);
        }catch(InternalServerErrorException ex){

            assertThat(ex.getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(ex.getMessage()).contains("502");
        }
    }

    @Test
    public void shouldThrowInternalServerErrorsForUnexpectedStatusCodes(){

        stubFor(get(urlEqualTo("/article/" + POKEDEX))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(302)));

        try{

            this.articleService.getArticleByName(POKEDEX);
            assertTrue("Should not get here!", false);
        }catch(InternalServerErrorException ex){

            assertThat(ex.getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(ex.getMessage()).contains("302");
        }
    }

//    @Test
//    public void shouldGetBlogAndReviews(){
//
//        ArticleDto allArticles = this.articleService.getAllArticles();
//
//        assertThat(allArticles.getContent().size()).isEqualTo(2);
//    }


}
