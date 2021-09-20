//package com.gorley.tweetapp.config;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@Configuration
//@EnableMongoRepositories
//public class MongoConfig {
//
//    @Value("${mongodb.host}")
//    private String mongoConnection;
//
//    @Value("${mongodb.dataBaseName}")
//    private String dataBaseName;
//
//    @Bean
//    public MongoClient mongoClient() {
//        return MongoClients.create(mongoConnection);
//    }
//
//    @Bean MongoTemplate mongoTemplate() {
//        return new MongoTemplate(mongoClient(), dataBaseName);
//    }
//
//}
