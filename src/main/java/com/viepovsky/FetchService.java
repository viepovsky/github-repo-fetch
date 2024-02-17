package com.viepovsky;

import com.viepovsky.client.GithubApiClient;
import com.viepovsky.client.dto.GithubRepositoryDTO;
import com.viepovsky.dto.RepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class FetchService {

    private final GithubApiClient client;

    private final Mapper mapper;

    List<RepositoryResponse> getAllRepositoriesByUsername(String username) {
        List<GithubRepositoryDTO> repositoryDTO = client.getAllRepositoriesByUsername(username);
        return repositoryDTO.stream()
                            .map(mapper::mapToRepositoryResponse)
                            .toList();
    }
}
