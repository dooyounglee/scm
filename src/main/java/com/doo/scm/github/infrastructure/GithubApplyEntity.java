package com.doo.scm.github.infrastructure;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.github.domain.GithubApply;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "GITHUBAPPLY", schema = "SCM1")
@NoArgsConstructor
@ToString
@Builder
public class GithubApplyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long applyNo;

    private String applyDt;
    private ApplyStatus applySt;

    public GithubApply toModel() {
        return GithubApply.builder()  
            .applyNo(applyNo)
            .applyDt(applyDt)
            .applySt(applySt)
            .build();
    }

    public static GithubApplyEntity from(GithubApply githubApply) {
        GithubApplyEntity githubApplyEntity = new GithubApplyEntity();
        githubApplyEntity.applyNo = githubApply.getApplyNo();
        githubApplyEntity.applyDt = githubApply.getApplyDt();
        githubApplyEntity.applySt = githubApply.getApplySt();
        return githubApplyEntity;
    }
}
