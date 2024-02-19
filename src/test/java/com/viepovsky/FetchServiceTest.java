package com.viepovsky;

import com.viepovsky.client.GithubApiClient;
import com.viepovsky.client.dto.BranchDTO;
import com.viepovsky.client.dto.CommitDTO;
import com.viepovsky.client.dto.GithubRepositoryDTO;
import com.viepovsky.client.dto.OwnerDTO;
import com.viepovsky.dto.RepositoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fetch Service Tests")
class FetchServiceTest {

    @InjectMocks
    private FetchService fetchService;

    @Mock
    private static GithubApiClient client;

    @Spy
    private Mapper mapper = new Mapper();

    @Test
    void getAllRepositoriesByUsername() {
        //Given
        List<GithubRepositoryDTO> githubRepositoryDTOs = createDummyGithubRepositoryDTOs();
        when(client.getAllRepositoriesByUsername(anyString())).thenReturn(githubRepositoryDTOs);
        doAnswer(invocation -> invocation.getArgument(0)).when(client)
                                                         .getAllBranchesAndLastCommits(any(GithubRepositoryDTO.class));
        //When
        List<RepositoryResponse> response = fetchService.getAllRepositoriesByUsername("SampleLogin");
        //Then
        assertEquals(1,
                response.size());
        assertEquals("Sample-Repository",
                response.get(0)
                        .repositoryName());
        assertEquals("SampleLogin",
                response.get(0)
                        .ownerLogin());
        assertEquals("main",
                response.get(0)
                        .branches()
                        .get(0)
                        .branchName());
        assertEquals("8b04ddb794776628b30b84aeab31977c7ab620e8",
                response.get(0)
                        .branches()
                        .get(0)
                        .lastCommitSha());
    }

    private List<GithubRepositoryDTO> createDummyGithubRepositoryDTOs() {
        OwnerDTO ownerDTO = createDummyOwnerDTO();
        List<BranchDTO> branchDTOs = createDummyBranchDTOs();
        GithubRepositoryDTO githubRepositoryDTO = GithubRepositoryDTO.builder()
                                                                     .repositoryName("Sample-Repository")
                                                                     .repositoryOwner(ownerDTO)
                                                                     .isFork(false)
                                                                     .branches(branchDTOs)
                                                                     .build();
        GithubRepositoryDTO githubRepositoryDTO2 = GithubRepositoryDTO.builder()
                                                                      .repositoryName("Sample-Repository2")
                                                                      .repositoryOwner(ownerDTO)
                                                                      .isFork(true)
                                                                      .build();
        return new ArrayList<>(List.of(githubRepositoryDTO, githubRepositoryDTO2));
    }

    private OwnerDTO createDummyOwnerDTO() {
        return OwnerDTO.builder()
                       .login("SampleLogin")
                       .build();
    }

    private List<BranchDTO> createDummyBranchDTOs() {
        CommitDTO commitDTO = new CommitDTO("8b04ddb794776628b30b84aeab31977c7ab620e8");
        BranchDTO branchDTO = BranchDTO.builder()
                                       .branchName("main")
                                       .lastCommit(commitDTO)
                                       .build();
        CommitDTO commitDTO2 = new CommitDTO("c4e677f63855db39dc9d53617c50e59d6f6d4932");
        BranchDTO branchDTO2 = BranchDTO.builder()
                                        .branchName("feature-2.0")
                                        .lastCommit(commitDTO2)
                                        .build();

        return new ArrayList<>(List.of(branchDTO, branchDTO2));
    }
}