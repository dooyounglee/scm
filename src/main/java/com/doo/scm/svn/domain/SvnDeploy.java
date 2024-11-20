package com.doo.scm.svn.domain;

import com.doo.scm.common.util.DateUtil;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SvnDeploy {
    
    private long deployNo;
    private long applyNo;
    private long revision;
    private String path;
    private String rgtDtm;

    @Builder
    public SvnDeploy(long deployNo, long applyNo, long revision, String path, String rgtDtm) {
        this.deployNo = deployNo;
        this.applyNo = applyNo;
        this.revision = revision;
        this.path = path;
        this.rgtDtm = rgtDtm;
    }

    public static SvnDeploy from(SvnApplyFile svnApplyFile) {
        return SvnDeploy.builder()
            .applyNo(svnApplyFile.getApplyNo())
            .revision(svnApplyFile.getRevision())
            .path(svnApplyFile.getPath())
            .rgtDtm(DateUtil.now("YYYYMMDDHHmmssSSS"))
            .build();
    }
}
