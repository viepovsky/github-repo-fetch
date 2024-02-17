package com.viepovsky.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryDTO {

    @JsonProperty("name")
    private String repositoryName;

    @JsonProperty("owner")
    private OwnerDTO repositoryOwner;

    private List<BranchDTO> branches;

}
