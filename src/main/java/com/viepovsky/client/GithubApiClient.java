package com.viepovsky.client;

import com.viepovsky.client.dto.GithubClientResponse;
import com.viepovsky.client.dto.GithubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubApiClient {

    private static final String ACCEPT_HEADER_GITHUB_JSON = "application/vnd.github+json";


    private final RestTemplate restTemplate;

    private final GithubApiConfig githubApiConfig;

    public List<GithubRepository> getAllRepositoriesByUsername(String username) {
        HttpHeaders header = buildHeader();
        URI url = buildUrl(username);
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(header);
        try {
            ResponseEntity<GithubRepository[]> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntityHeaders,
                    GithubRepository[].class
            );
            return Arrays.asList(ofNullable(responseEntity.getBody()).orElse(new GithubRepository[0]));
        } catch (RestClientException e) {
            log.error("Error while getting repositories." + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private HttpHeaders buildHeader() {
        HttpHeaders header = new HttpHeaders();
        header.set("Accept", ACCEPT_HEADER_GITHUB_JSON);
        return header;
    }

    private URI buildUrl(String username) {
        String path = String.format("/users/%s/repos", username);
        return UriComponentsBuilder.fromHttpUrl(githubApiConfig.getGithubApiEndpoint())
                                   .path(path)
                                   .build()
                                   .encode()
                                   .toUri();
    }

}
