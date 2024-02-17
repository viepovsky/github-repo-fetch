package com.viepovsky.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class GithubClientResponse {

    private List<GithubRepository> repositoryClientResponses;

}
