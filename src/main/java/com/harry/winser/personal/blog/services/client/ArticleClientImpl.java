package com.harry.winser.personal.blog.services.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ArticleClientImpl implements ArticleClient {

    private String host;
    private String port;
    private RestTemplate restTemplate;

    @Autowired
    public ArticleClientImpl(@Value("${api.server.host}")String host,
                             @Value("${api.server.port}")String port,
                             RestTemplate restTemplate) {
        this.host = host;
        this.port = port;
        this.restTemplate = restTemplate;
    }

    @Override
    public Article findByName(String name) {

        ResponseEntity<Article> articleEntity =
                this.sendRequest(String.format("%s:%s/article/%s", host, port, name), Article.class);

        if(articleEntity.getStatusCode() != HttpStatus.OK){
            throw new HttpClientErrorException(articleEntity.getStatusCode(), "Unexpected status code returned");
        }

        return articleEntity.getBody();
    }

    @Override
    @Cacheable("findByType")
    public ArticleContainer findByType(String type) {
        System.out.println("Not cached yet");

        ResponseEntity<ArticleContainer> articleEntity =
                this.sendRequest(String.format("%s:%s/article/type/%s", host, port, type), ArticleContainer.class);

        if(articleEntity.getStatusCode() != HttpStatus.OK){
            throw new HttpClientErrorException(articleEntity.getStatusCode(), "Unexpected status code returned");
        }

        return articleEntity.getBody();
    }

    @Override
    @Cacheable("findAll")
    public ArticleContainer findAll() {


        ResponseEntity<ArticleContainer> articleEntity =
                this.sendRequest(String.format("%s:%s/article?search&page=0&size=1000", host, port), ArticleContainer.class);

        if(articleEntity.getStatusCode() != HttpStatus.OK){
            throw new HttpClientErrorException(articleEntity.getStatusCode(), "Unexpected status code returned");
        }

        return articleEntity.getBody();
    }

    private ResponseEntity sendRequest(String url, Class containerClass) {

        return this.restTemplate.getForEntity(url, containerClass);

    }
}
