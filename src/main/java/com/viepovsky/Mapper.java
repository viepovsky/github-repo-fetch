package com.viepovsky;

import com.viepovsky.client.dto.BranchDTO;
import com.viepovsky.client.dto.GithubRepositoryDTO;
import com.viepovsky.dto.RepositoryBranchResponse;
import com.viepovsky.dto.RepositoryResponse;
import org.springframework.stereotype.Component;

@Component
class Mapper {
    private static final String COMMIT_SHA_PROPERTY_NAME = "sha";

    RepositoryResponse mapToRepositoryResponse(GithubRepositoryDTO githubRepository) {
        return new RepositoryResponse(
                githubRepository.getRepositoryName(),
                githubRepository.getRepositoryOwner()
                                .getLogin(),
                githubRepository.getBranches()
                                .stream()
                                .map(this::mapToRepositoryBranchResponse)
                                .toList());
    }

    RepositoryBranchResponse mapToRepositoryBranchResponse(BranchDTO branch) {
        return new RepositoryBranchResponse(
                branch.getBranchName(),
                branch.getLastCommitShaAndUrl()
                      .get(COMMIT_SHA_PROPERTY_NAME)
        );
    }
}
