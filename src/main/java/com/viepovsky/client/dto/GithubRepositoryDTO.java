package com.viepovsky.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoryDTO(
        @JsonProperty("name")
        String repositoryName,
        @JsonProperty("owner")
        OwnerDTO repositoryOwner,
        @JsonProperty("fork")
        boolean isFork,
        List<BranchDTO> branches
) {

}
