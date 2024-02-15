package com.viepovsky.dto;

import java.util.List;

public record RepositoryResponse(
        String repoName,
        String username,
        List<RepositoryBranchResponse> branches
) {
}
