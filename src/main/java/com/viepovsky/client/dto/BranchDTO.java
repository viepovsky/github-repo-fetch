package com.viepovsky.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchDTO(
        @JsonProperty("name")
        String branchName,
        @JsonProperty("commit")
        CommitDTO lastCommit
) {

}
