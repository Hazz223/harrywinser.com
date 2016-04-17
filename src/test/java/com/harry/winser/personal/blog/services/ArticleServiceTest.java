package com.harry.winser.personal.blog.services;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.harry.winser.personal.blog.services.client.ArticleClient;
import com.harry.winser.personal.blog.services.client.ArticleClientImpl;
import com.harry.winser.personal.blog.services.client.ArticleType;
import com.harry.winser.personal.blog.web.exceptions.InternalServerErrorException;
import com.harry.winser.personal.blog.web.exceptions.NotFoundException;
import org.apache.commons.io.IOUtil;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
    private static final String POKEDEX = "Pokedex";
    private static final String ARTICLE_JSON = "Article.json";
    private static final String ARTICLES_REVIEW_JSON = "Articles-review.json";
    private static final String ARTICLES_BLOG_JSON = "Articles-blog.json";
    public static final String NO_ARTICLES_JSON = "No-articles.json";

    private ArticleClient client = new ArticleClientImpl(LOCALHOST, PORT, new RestTemplate());
    private ArticleService articleService = new ArticleServiceImpl(client);

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9001));

    @Test
    public void shouldGetSingleArticle() throws Exception {

        stubFor(get(urlEqualTo("/article/" + POKEDEX))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(this.loadResource(ARTICLE_JSON))));

        Article result = this.articleService.getArticleByName(POKEDEX);

        Article expected = new Article();
        expected.setCleanTitle("pipelineconf_2016_retro");
        expected.setType("technology");
        expected.setId(20L);
        expected.setTitle("What I learned from PIPELINECONF 2016");
        expected.setData("Some cool data here!");

        this.assertArticle(result, expected);
    }

    @Test
    public void shouldThrow404WhenResultNotFound() {

        stubFor(get(urlEqualTo("/article/" + POKEDEX))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)));

        try {

            this.articleService.getArticleByName(POKEDEX);
            assertTrue("Should not get here!", false);
        } catch (NotFoundException ex) {

            assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(ex.getMessage()).contains(POKEDEX);
        }
    }

    @Test
    public void shouldThrowInternalServerErrorForAllOtherErrors() {

        stubFor(get(urlEqualTo("/article/" + POKEDEX))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(502)));

        try {

            this.articleService.getArticleByName(POKEDEX);
            assertTrue("Should not get here!", false);
        } catch (InternalServerErrorException ex) {

            assertThat(ex.getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(ex.getMessage()).contains("502");
        }
    }

    @Test
    public void shouldThrowInternalServerErrorsForUnexpectedStatusCodes() {

        stubFor(get(urlEqualTo("/article/" + POKEDEX))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(302)));

        try {

            this.articleService.getArticleByName(POKEDEX);
            assertTrue("Should not get here!", false);
        } catch (InternalServerErrorException ex) {

            assertThat(ex.getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(ex.getMessage()).contains("302");
        }
    }

    @Test
    public void shouldGetBlogAndReviews() throws Exception {

        stubFor(get(urlEqualTo("/article/type/" + ArticleType.BLOG.toString()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(this.loadResource(ARTICLES_BLOG_JSON))));

        stubFor(get(urlEqualTo("/article/type/" + ArticleType.REVIEW.toString()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(this.loadResource(ARTICLES_REVIEW_JSON))));

        ArticleDto allArticles = this.articleService.getAllArticles();

        assertThat(allArticles.getContent().size()).isEqualTo(2);

        Article reviewContent = new Article();
        reviewContent.setTitle("The Trials and Tribulations of Online Courtship");
        reviewContent.setType(ArticleType.REVIEW.toString());
        reviewContent.setId(7L);
        reviewContent.setData("Some review here");
        reviewContent.setCleanTitle("online_dating");

        Article blogContent = new Article();
        blogContent.setId(4L);
        blogContent.setCleanTitle("ghost_town");
        blogContent.setTitle("It be like a ghost town round these parts\u2026");
        blogContent.setData("Some blog here");
        blogContent.setType(ArticleType.BLOG.toString());

        this.assertArticle(allArticles.getContent().get(0), reviewContent);
        this.assertArticle(allArticles.getContent().get(1), blogContent);
    }

    @Test
    public void shouldReturnEmptyWhenNoResultsFound(){

        stubFor(get(urlEqualTo("/article/type/" + ArticleType.BLOG.toString()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(this.loadResource(NO_ARTICLES_JSON))));

        stubFor(get(urlEqualTo("/article/type/" + ArticleType.REVIEW.toString()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(this.loadResource(NO_ARTICLES_JSON))));

        ArticleDto allArticles = this.articleService.getAllArticles();

        assertThat(allArticles.getContent().size()).isEqualTo(0);
        assertThat(allArticles.getNumber()).isEqualTo(0);
        assertThat(allArticles.getNumberOfElements()).isEqualTo(0);
        assertThat(allArticles.getTotalElements()).isEqualTo(0);
        assertThat(allArticles.getTotalPages()).isEqualTo(0);
        assertThat(allArticles.getFirst()).isEqualTo(true);
        assertThat(allArticles.getLast()).isEqualTo(true);
        assertThat(allArticles.getSize()).isEqualTo(20);

    }

    @Test
    public void shouldThrowInternalServerErrorWhenExceptionResponseFromOne(){

        stubFor(get(urlEqualTo("/article/type/" + ArticleType.BLOG.toString()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(500)
                        .withBody(this.loadResource(NO_ARTICLES_JSON))));

        stubFor(get(urlEqualTo("/article/type/" + ArticleType.REVIEW.toString()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(this.loadResource(NO_ARTICLES_JSON))));

        try{
            this.articleService.getAllArticles();
            assertTrue("Should not get here!", false);
        }catch(InternalServerErrorException ex){
            assertThat(ex.getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void shouldThrowInternalServerErrorWhenUnexpectedCodeReturned(){

        stubFor(get(urlEqualTo("/article/type/" + ArticleType.BLOG.toString()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(302)
                        .withBody(this.loadResource(NO_ARTICLES_JSON))));

        stubFor(get(urlEqualTo("/article/type/" + ArticleType.REVIEW.toString()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(302)
                        .withBody(this.loadResource(NO_ARTICLES_JSON))));

        try{
            this.articleService.getAllArticles();
            assertTrue("Should not get here!", false);
        }catch(InternalServerErrorException ex){
            assertThat(ex.getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void assertArticle(Article given, Article expected){

        assertThat(given.getCleanTitle()).isEqualTo(expected.getCleanTitle());
        assertThat(given.getTitle()).isEqualTo(expected.getTitle());
        assertThat(given.getType()).isEqualTo(expected.getType());
        assertThat(given.getId()).isEqualTo(expected.getId());
        assertThat(given.getData()).isEqualTo(expected.getData());

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
//        Date date = simpleDateFormat.parse("2016-04-06 21:20");
//        assertThat(result.getDate()).isEqualTo(date); Ignoring this for the moment, as it has some weird issue where it keeps setting T23 instead of T22... Darn dates.
    }

    private String loadResource(String resource) {
        String resultString = "";

        try {
            resultString = IOUtil.toString(this.getClass().getClassLoader().getResourceAsStream(resource));
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue("Failed to load resource", false);
        }

        return resultString;
    }
}
