package com.harry.winser.personal.blog.web;

import com.harry.winser.personal.blog.services.ArticleService;
import com.harry.winser.personal.blog.services.CacheService;
import com.harry.winser.personal.blog.services.client.Article;
import com.harry.winser.personal.blog.services.client.ArticleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class PagesController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CacheService cacheService;

    @RequestMapping("/")
    public String home(Map<String, Object> model){

        model.put("content", this.articleService.getBlogAndReviews().getContent());

        return "home";
    }

    @RequestMapping("/article/{name}")
    public String getArticle(@PathVariable(value = "name") String articleName, Map<String, Object> model){

        Article article = this.articleService.getArticleByName(articleName);
        model.put("title", article.getTitle());
        model.put("cleanTitle", article.getCleanTitle());
        model.put("data", article.getData());
        model.put("date", article.getDate());

        return "article";
    }

    @RequestMapping("/blog")
    public String getBlogs( Map<String, Object> model){

        model.put("content", this.articleService.getArticleByType(ArticleType.BLOG).getContent());

        return "home";
    }

    @RequestMapping("/review")
    public String getReviews( Map<String, Object> model){

        model.put("content", this.articleService.getArticleByType(ArticleType.REVIEW).getContent());

        return "home";
    }

    @RequestMapping("/about")
    public String about(){

        return "about";
    }

    @RequestMapping("/_clearCache")
    public String clearCache(){

        this.cacheService.clearCache();

        return "clearCache";
    }

}
