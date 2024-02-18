package com.viepovsky.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryDTO {

    @JsonProperty("name")
    private String repositoryName;

    @JsonProperty("owner")
    private OwnerDTO repositoryOwner;

    @JsonProperty("fork")
    private boolean isFork;

    private List<BranchDTO> branches;

}
