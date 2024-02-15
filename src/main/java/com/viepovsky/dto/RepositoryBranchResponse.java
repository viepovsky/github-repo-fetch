package com.viepovsky.dto;

public record RepositoryBranchResponse(
        String branchName,
        String lastCommitSha
) {
}
