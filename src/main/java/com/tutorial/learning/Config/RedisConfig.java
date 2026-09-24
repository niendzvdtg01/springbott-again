package com.tutorial.learning.Config;

import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration 
public class RedisConfig {
    @Bean 
    RedisCacheManagerBuilderCustomizer cacheManagerBuilderCustomizer(){
        return builder -> builder.transactionAware().enableStatistics();
    }
}
