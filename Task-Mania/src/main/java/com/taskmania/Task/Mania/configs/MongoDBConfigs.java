package com.taskmania.Task.Mania.configs;

import org.springframework.beans.factory.annotation.Value;

public class MongoDBConfigs {
    @Value("${mongodb.username}")
    private String username;

    @Value("${mongodb.password}")
    private String password;

    @Value("${mongodb.cluster-url}")
    private String clusterUrl;
}
