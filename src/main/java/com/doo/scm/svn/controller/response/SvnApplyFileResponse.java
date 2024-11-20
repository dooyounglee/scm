package com.doo.scm.svn.controller.response;

import com.doo.scm.svn.domain.SvnApplyFile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SvnApplyFileResponse {
    
    private long revision;
    private String path;

    public static SvnApplyFileResponse from(SvnApplyFile svnApplyFile) {
        return SvnApplyFileResponse.builder()
            .revision(svnApplyFile.getRevision())
            .path(svnApplyFile.getPath())
            .build();
    }
}
