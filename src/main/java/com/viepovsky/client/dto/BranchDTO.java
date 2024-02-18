package com.viepovsky.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchDTO {

    @JsonProperty("name")
    private String branchName;

    @JsonProperty("commit")
    private Map<String, String> lastCommitShaAndUrl;

}
