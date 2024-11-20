package com.doo.scm.svn.infrastructure;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.svn.domain.SvnApply;

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
@Table(name = "SVNAPPLY", schema = "SCM1")
@NoArgsConstructor
@ToString
@Builder
public class SvnApplyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long applyNo;

    private String applyDt;
    private ApplyStatus applySt;

    public SvnApply toModel() {
        return SvnApply.builder()  
            .applyNo(applyNo)
            .applyDt(applyDt)
            .applySt(applySt)
            .build();
    }

    public static SvnApplyEntity from(SvnApply svnApply) {
        SvnApplyEntity svnApplyEntity = new SvnApplyEntity();
        svnApplyEntity.applyNo = svnApply.getApplyNo();
        svnApplyEntity.applyDt = svnApply.getApplyDt();
        svnApplyEntity.applySt = svnApply.getApplySt();
        return svnApplyEntity;
    }
}
