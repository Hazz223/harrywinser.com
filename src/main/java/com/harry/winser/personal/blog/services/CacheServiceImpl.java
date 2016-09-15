package com.harry.winser.personal.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Scheduled(cron = "0 0,12 * * * *")
    public void clearCache() {

        System.out.println("Clearing cache");

        this.cacheManager.getCacheNames().stream()
                .forEach(cacheName -> this.cacheManager.getCache(cacheName).clear());
    }

}
