package com.doo.scm.github.controller.response;

import com.doo.scm.github.domain.GithubApplyFile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubApplyFileResponse {
    
    private String commitId;
    private String commitDt;
    private String path;

    public static GithubApplyFileResponse from(GithubApplyFile githubApplyFile) {
        return GithubApplyFileResponse.builder()
            .commitId(githubApplyFile.getCommitId())
            .commitDt(githubApplyFile.getCommitDt())
            .path(githubApplyFile.getPath())
            .build();
    }
}
