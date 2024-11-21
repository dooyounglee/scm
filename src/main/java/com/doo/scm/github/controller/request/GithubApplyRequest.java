package com.doo.scm.github.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GithubApplyRequest {
    
    @Getter
    @NoArgsConstructor
    public static class GithubApplyFileCreate {
        private long applyNo;
        private String commitId;
        private String commitDt;
        private String path;

        @Builder
        public GithubApplyFileCreate(long applyNo, String commitId, String commitDt, String path) {
            this.applyNo = applyNo;
            this.commitId = commitId;
            this.commitDt = commitDt;
            this.path = path;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class GithubApplySelect {
        private long applyNo;

        @Builder
        public GithubApplySelect(long applyNo) {
            this.applyNo = applyNo;
        }
    }
}
