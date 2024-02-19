package com.viepovsky.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Getter
@Configuration
public class GithubApiConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                         .baseUrl(githubApiEndpoint)
                         .build();
    }

    @Value("${github.api.endpoint}")
    private String githubApiEndpoint;
}
