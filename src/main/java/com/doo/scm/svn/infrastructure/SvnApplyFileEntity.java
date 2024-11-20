package com.doo.scm.svn.infrastructure;

import com.doo.scm.svn.domain.SvnApplyFile;

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
@Table(name = "SVNAPPLYFILE", schema = "SCM1")
@NoArgsConstructor
@ToString
@Builder
public class SvnApplyFileEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long applyFileNo;

    private long applyNo;
    private long revision;
    private String path;

    public SvnApplyFile toModel() {
        return SvnApplyFile.builder()
            .applyFileNo(applyFileNo)
            .applyNo(applyNo)
            .revision(revision)
            .path(path)
            .build();
    }

    public static SvnApplyFileEntity from(SvnApplyFile svnApplyFile) {
        SvnApplyFileEntity svnApplyFileEntity = new SvnApplyFileEntity();
        svnApplyFileEntity.applyFileNo = svnApplyFile.getApplyFileNo();
        svnApplyFileEntity.applyNo = svnApplyFile.getApplyNo();
        svnApplyFileEntity.revision = svnApplyFile.getRevision();
        svnApplyFileEntity.path = svnApplyFile.getPath();
        return svnApplyFileEntity;
    }
}
