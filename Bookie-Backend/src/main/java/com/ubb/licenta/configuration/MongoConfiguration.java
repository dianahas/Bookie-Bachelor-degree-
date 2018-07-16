package com.ubb.licenta.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.ubb.licenta"})
@EnableMongoAuditing
public class MongoConfiguration extends AbstractMongoConfiguration {
    private static Logger log = LoggerFactory.getLogger(MongoAutoConfiguration.class);

    @Autowired
    Environment environment;

    @Override
    protected String getDatabaseName() {
        return environment.getProperty("spring.data.mongodb.database");
    }

    @Override
    public Mongo mongo() {
        return new MongoClient("127.0.0.1", 27017);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, getDatabaseName());

        return new MongoTemplate(mongoDbFactory);
    }
}
