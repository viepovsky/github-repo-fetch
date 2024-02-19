package com.viepovsky.client;

import com.viepovsky.client.dto.BranchDTO;
import com.viepovsky.client.dto.GithubRepositoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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

    public List<GithubRepositoryDTO> getAllRepositoriesByUsername(String username) {
        HttpHeaders header = buildHeader();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(header);
        URI url = buildUserUrl(username);
        try {
            ResponseEntity<GithubRepositoryDTO[]> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntityHeaders,
                    GithubRepositoryDTO[].class
            );
            return Arrays.asList(ofNullable(responseEntity.getBody()).orElse(new GithubRepositoryDTO[0]));
        } catch (HttpClientErrorException e) {
            log.error("Error while getting repositories. " + e.getMessage());
            throw e;
        } catch (RestClientException e) {
            log.error("Error while getting repositories. " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private HttpHeaders buildHeader() {
        HttpHeaders header = new HttpHeaders();
        header.set("Accept", ACCEPT_HEADER_GITHUB_JSON);
        return header;
    }

    private URI buildUserUrl(String username) {
        String path = String.format("/users/%s/repos", username);
        return createUri(path);
    }

    private URI buildRepoUrl(String username, String repositoryName) {
        String path = String.format("/repos/%s/%s/branches", username, repositoryName);
        return createUri(path);
    }

    private URI createUri(String path) {
        return UriComponentsBuilder.fromHttpUrl(githubApiConfig.getGithubApiEndpoint())
                                   .path(path)
                                   .build()
                                   .encode()
                                   .toUri();
    }

    public GithubRepositoryDTO getAllBranchesAndLastCommits(GithubRepositoryDTO repository) {
        HttpHeaders header = buildHeader();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(header);
        URI url = buildRepoUrl(
                repository.repositoryOwner()
                          .login(),
                repository.repositoryName()
        );
        try {
            ResponseEntity<BranchDTO[]> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntityHeaders,
                    BranchDTO[].class
            );

            List<BranchDTO> branches = Arrays.asList(ofNullable(responseEntity.getBody()).orElse(new BranchDTO[0]));

            return GithubRepositoryDTO.builder()
                                      .repositoryName(repository.repositoryName())
                                      .repositoryOwner(repository.repositoryOwner())
                                      .isFork(repository.isFork())
                                      .branches(branches)
                                      .build();
        } catch (RestClientException e) {
            log.error("Error while getting branches." + e.getMessage(), e);
            return repository;
        }
    }
}
