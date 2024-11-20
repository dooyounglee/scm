package com.doo.scm.svn.infrastructure;

import com.doo.scm.svn.domain.SvnDeploy;

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
@Table(name = "SVNDEPLOY", schema = "SCM1")
@NoArgsConstructor
@ToString
@Builder
public class SvnDeployEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long deployNo;
    
    private long applyNo;
    private long revision;
    private String path;
    private String rgtDtm;

    public SvnDeploy toModel() {
        return SvnDeploy.builder()
            .deployNo(deployNo)
            .applyNo(applyNo)
            .revision(revision)
            .path(path)
            .rgtDtm(rgtDtm)
            .build();
    }

    public static SvnDeployEntity from(SvnDeploy svnDeploy) {
        SvnDeployEntity entity = new SvnDeployEntity();
        entity.deployNo = svnDeploy.getDeployNo();
        entity.applyNo = svnDeploy.getApplyNo();
        entity.revision = svnDeploy.getRevision();
        entity.path = svnDeploy.getPath();
        entity.rgtDtm = svnDeploy.getRgtDtm();
        return entity;
    }
}
