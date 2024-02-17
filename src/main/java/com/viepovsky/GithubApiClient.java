package com.viepovsky;

import com.viepovsky.dto.ClientRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
class GithubApiClient {

    private final RestTemplate restTemplate;

    private final GithubApiConfig githubApiConfig;

    List<ClientRepositoryResponse> getAllRepositoriesByUsername(String username) {
        return null;
    }


}
