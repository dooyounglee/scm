package com.doo.scm.github.infrastructure;

import com.doo.scm.github.domain.GithubApplyFile;

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
@Table(name = "GITHUBAPPLYFILE", schema = "SCM1")
@NoArgsConstructor
@ToString
@Builder
public class GithubApplyFileEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long applyFileNo;

    private long applyNo;
    private String commitId;
    private String commitDt;
    private String path;

    public GithubApplyFile toModel() {
        return GithubApplyFile.builder()
            .applyFileNo(applyFileNo)
            .applyNo(applyNo)
            .commitId(commitId)
            .commitDt(commitDt)
            .path(path)
            .build();
    }

    public static GithubApplyFileEntity from(GithubApplyFile githubApplyFile) {
        GithubApplyFileEntity githubApplyFileEntity = new GithubApplyFileEntity();
        githubApplyFileEntity.applyFileNo = githubApplyFile.getApplyFileNo();
        githubApplyFileEntity.applyNo = githubApplyFile.getApplyNo();
        githubApplyFileEntity.commitId = githubApplyFile.getCommitId();
        githubApplyFileEntity.commitDt = githubApplyFile.getCommitDt();
        githubApplyFileEntity.path = githubApplyFile.getPath();
        return githubApplyFileEntity;
    }
}
