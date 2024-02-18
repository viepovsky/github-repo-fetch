package com.viepovsky;

import com.viepovsky.dto.RepositoryResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/repositories")
@RequiredArgsConstructor
class FetchController {

    private final FetchService fetchService;

    @GetMapping
    ResponseEntity<List<RepositoryResponse>> getAllRepositoriesByUsername(
            @RequestParam(name = "username") @NotBlank String username
    ) {
        List<RepositoryResponse> repositories = fetchService.getAllRepositoriesByUsername(username);
        return ResponseEntity.ok(repositories);
    }
}
