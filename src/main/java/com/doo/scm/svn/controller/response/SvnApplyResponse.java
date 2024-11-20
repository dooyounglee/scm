package com.doo.scm.svn.controller.response;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.svn.domain.SvnApply;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SvnApplyResponse {
    
    private long applyNo;
    private String applyDt;
    private long revision;
    private String path;
    private ApplyStatus applySt;

    public static SvnApplyResponse from(SvnApply svnApply) {
        return SvnApplyResponse.builder()
            .applyNo(svnApply.getApplyNo())
            .applyDt(svnApply.getApplyDt())
            .applySt(svnApply.getApplySt())
            .build();
    }
}
