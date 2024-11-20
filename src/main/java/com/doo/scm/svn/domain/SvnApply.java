package com.doo.scm.svn.domain;

import com.doo.scm.common.enums.ApplyStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SvnApply {
    
    private long applyNo;
    private String applyDt;
    // private String usrNo;
    private ApplyStatus applySt;

    @Builder
    public SvnApply(long applyNo, String applyDt, ApplyStatus applySt) {
        this.applyNo = applyNo;
        this.applyDt = applyDt;
        this.applySt = applySt;
    }

    public SvnApply updateApplySt(ApplyStatus ready) {
        return SvnApply.builder()
            .applyNo(applyNo)
            .applyDt(applyDt)
            .applySt(ready)
            .build();
    }
}
