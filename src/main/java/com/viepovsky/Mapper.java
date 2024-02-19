package com.viepovsky;

import com.viepovsky.client.dto.BranchDTO;
import com.viepovsky.client.dto.GithubRepositoryDTO;
import com.viepovsky.dto.RepositoryBranchResponse;
import com.viepovsky.dto.RepositoryResponse;
import org.springframework.stereotype.Component;

@Component
class Mapper {
    RepositoryResponse mapToRepositoryResponse(GithubRepositoryDTO githubRepository) {
        return new RepositoryResponse(
                githubRepository.repositoryName(),
                githubRepository.repositoryOwner()
                                .login(),
                githubRepository.branches()
                                .stream()
                                .map(this::mapToRepositoryBranchResponse)
                                .toList());
    }

    RepositoryBranchResponse mapToRepositoryBranchResponse(BranchDTO branch) {
        return new RepositoryBranchResponse(
                branch.branchName(),
                branch.lastCommit()
                      .sha()
        );
    }
}
