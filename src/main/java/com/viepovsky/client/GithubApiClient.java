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
import java.util.ArrayList;
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

    public List<GithubRepositoryDTO> getAllRepositoriesAndBranchesByUsername(String username) {
        HttpHeaders header = buildHeader();
        URI url = buildUserUrl(username);
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(header);
        try {
            ResponseEntity<GithubRepositoryDTO[]> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntityHeaders,
                    GithubRepositoryDTO[].class
            );
            List<GithubRepositoryDTO> githubRepositories = Arrays.asList(ofNullable(responseEntity.getBody()).orElse(new GithubRepositoryDTO[0]));

            getAllBranchesAndLastCommits(githubRepositories);
            return githubRepositories;
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

    private void getAllBranchesAndLastCommits(List<GithubRepositoryDTO> repositories) {
        HttpHeaders header = buildHeader();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(header);
        for (GithubRepositoryDTO repository : repositories) {
            URI url = buildRepoUrl(
                    repository.getRepositoryOwner()
                              .getLogin(),
                    repository.getRepositoryName()
            );
            try {
                ResponseEntity<BranchDTO[]> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntityHeaders,
                        BranchDTO[].class
                );

                List<BranchDTO> branches = Arrays.asList(ofNullable(responseEntity.getBody()).orElse(new BranchDTO[0]));
                repository.setBranches(branches);
            } catch (RestClientException e) {
                log.error("Error while getting branches." + e.getMessage(), e);
                repository.setBranches(new ArrayList<>());
            }
        }
    }
}
