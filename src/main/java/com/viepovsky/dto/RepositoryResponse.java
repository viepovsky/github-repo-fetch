package com.viepovsky.dto;

import java.util.List;

public record RepositoryResponse(
        String repositoryName,
        String ownerLogin,
        List<RepositoryBranchResponse> branches
) {
}
