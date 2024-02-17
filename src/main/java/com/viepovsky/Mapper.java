package com.viepovsky;

import com.viepovsky.client.dto.GithubRepositoryDTO;
import com.viepovsky.dto.RepositoryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
class Mapper {
    RepositoryResponse mapToRepositoryResponse(GithubRepositoryDTO githubRepository) {
        return new RepositoryResponse(
                githubRepository.getRepositoryName(),
                githubRepository.getRepositoryOwner()
                                .getLogin(),
                new ArrayList<>());
    }
}
