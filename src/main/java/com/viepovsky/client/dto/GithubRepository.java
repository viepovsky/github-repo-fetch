package com.viepovsky.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepository {

    @JsonProperty("name")
    private String repoName;

    @JsonProperty("owner")
    private GithubRepositoryOwner owner;

}
