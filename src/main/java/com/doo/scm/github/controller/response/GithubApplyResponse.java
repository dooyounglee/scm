package com.doo.scm.github.controller.response;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.github.domain.GithubApply;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubApplyResponse {
    
    private long applyNo;
    private String applyDt;
    private long commitId;
    private String path;
    private ApplyStatus applySt;

    public static GithubApplyResponse from(GithubApply githubApply) {
        return GithubApplyResponse.builder()
            .applyNo(githubApply.getApplyNo())
            .applyDt(githubApply.getApplyDt())
            .applySt(githubApply.getApplySt())
            .build();
    }
}
