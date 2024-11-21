package com.doo.scm.github.domain;

import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplyFileCreate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GithubApplyFile {
    
    private long applyFileNo;
    private long applyNo;
    private String commitId;
    private String commitDt;
    private String path;

    @Builder
    public GithubApplyFile(long applyFileNo, long applyNo, String commitId, String commitDt, String path) {
        this.applyFileNo = applyFileNo;
        this.applyNo = applyNo;
        this.commitId = commitId;
        this.commitDt = commitDt;
        this.path = path;
    }

    public static GithubApplyFile from(GithubApplyFileCreate dto) {
        return GithubApplyFile.builder()
            .applyNo(dto.getApplyNo())
            .commitId(dto.getCommitId())
            .commitDt(dto.getCommitDt())
            .path(dto.getPath())
            .build();
    }

    public static GithubApplyFile from(long applyNo, GithubApplyFileCreate dto) {
        return GithubApplyFile.builder()
            .applyNo(applyNo)
            .commitId(dto.getCommitId())
            .commitDt(dto.getCommitDt())
            .path(dto.getPath())
            .build();
    }
}
