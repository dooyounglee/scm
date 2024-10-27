package com.doo.scm.svn.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RevisionRequest {
    
    @Getter
    @NoArgsConstructor
    public static class RevisionSelect {
        private long revision;
        private String file;

        @Builder
        public RevisionSelect(long revision, String file) {
            this.revision = revision;
            this.file = file;
        }
    }
}
