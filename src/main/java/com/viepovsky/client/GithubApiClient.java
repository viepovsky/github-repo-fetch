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

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubApiClient {

    private final RestClient restClient;

    public List<GithubRepositoryDTO> getAllRepositoriesByUsername(String username) {
        try {
            return restClient.get()
                             .uri("/users/{username}/repos", username)
                             .accept(MediaType.APPLICATION_JSON)
                             .retrieve()
                             .body(new ParameterizedTypeReference<>() {
                             });
        } catch (RestClientException e) {
            log.error("Error while getting repositories. " + e.getMessage());
            throw e;
        }
    }

    public GithubRepositoryDTO getAllBranchesAndLastCommits(GithubRepositoryDTO repository) {
        String username = repository.repositoryOwner()
                                    .login();
        String repositoryName = repository.repositoryName();
        try {
            List<BranchDTO> response = restClient.get()
                                                 .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
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
