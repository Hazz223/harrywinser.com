package com.harry.winser.personal.blog.services.client;

public enum ArticleType {

    BLOG("blog"),
    REVIEW("review");

    private final String type;

    ArticleType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
