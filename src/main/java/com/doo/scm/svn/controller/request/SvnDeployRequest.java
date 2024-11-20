package com.doo.scm.svn.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SvnDeployRequest {
    
    @Getter
    @NoArgsConstructor
    public static class SvnApplyFileCreate {
        private long applyNo;
        private long revision;
        private String path;

        @Builder
        public SvnApplyFileCreate(long applyNo, long revision, String path) {
            this.applyNo = applyNo;
            this.revision = revision;
            this.path = path;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SvnApplySelect {
        private long applyNo;

        @Builder
        public SvnApplySelect(long applyNo) {
            this.applyNo = applyNo;
        }
    }
}
