package com.viepovsky.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
