package com.doo.scm.svn.domain;

import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplyFileCreate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SvnApplyFile {
    
    private long applyFileNo;
    private long applyNo;
    private long revision;
    private String path;

    @Builder
    public SvnApplyFile(long applyFileNo, long applyNo, long revision, String path) {
        this.applyFileNo = applyFileNo;
        this.applyNo = applyNo;
        this.revision = revision;
        this.path = path;
    }

    public static SvnApplyFile from(SvnApplyFileCreate dto) {
        return SvnApplyFile.builder()
            .applyNo(dto.getApplyNo())
            .revision(dto.getRevision())
            .path(dto.getPath())
            .build();
    }

    public static SvnApplyFile from(long applyNo, SvnApplyFileCreate dto) {
        return SvnApplyFile.builder()
            .applyNo(applyNo)
            .revision(dto.getRevision())
            .path(dto.getPath())
            .build();
    }
}
