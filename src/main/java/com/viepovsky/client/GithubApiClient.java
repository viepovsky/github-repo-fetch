package com.viepovsky.client;

import com.viepovsky.client.dto.BranchDTO;
import com.viepovsky.client.dto.GithubRepositoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubApiClient {

    private final RestClient restClient;

    private final GithubApiConfig githubApiConfig;

    public List<GithubRepositoryDTO> getAllRepositoriesByUsername(String username) {
        URI url = buildUserUrl(username);
        try {
            return restClient.get()
                             .uri(url)
                             .accept(MediaType.APPLICATION_JSON)
                             .retrieve()
                             .body(new ParameterizedTypeReference<>() {
                             });
        } catch (RestClientException e) {
            log.error("Error while getting repositories. " + e.getMessage());
            throw e;
        }
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
        URI url = buildRepoUrl(
                repository.repositoryOwner()
                          .login(),
                repository.repositoryName()
        );
        try {
            List<BranchDTO> response = restClient.get()
                                                 .uri(url)
                                                 .accept(MediaType.APPLICATION_JSON)
                                                 .retrieve()
                                                 .body(new ParameterizedTypeReference<>() {
                                                 });
            return GithubRepositoryDTO.builder()
                                      .repositoryName(repository.repositoryName())
                                      .repositoryOwner(repository.repositoryOwner())
                                      .isFork(repository.isFork())
                                      .branches(response)
                                      .build();
        } catch (RestClientException e) {
            log.error("Error while getting branches." + e.getMessage(), e);
            throw e;
        }
    }
}
