package com.doo.scm.github.domain;

import com.doo.scm.common.enums.ApplyStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GithubApply {
    
    private long applyNo;
    private String applyDt;
    // private String usrNo;
    private ApplyStatus applySt;

    @Builder
    public GithubApply(long applyNo, String applyDt, ApplyStatus applySt) {
        this.applyNo = applyNo;
        this.applyDt = applyDt;
        this.applySt = applySt;
    }

    public GithubApply updateApplySt(ApplyStatus ready) {
        return GithubApply.builder()
            .applyNo(applyNo)
            .applyDt(applyDt)
            .applySt(ready)
            .build();
    }
}
