package com.doo.scm.github.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GithubApplyJpaRepository extends JpaRepository<GithubApplyEntity, Long> {
    
    @Modifying
    @Query("UPDATE GithubApplyEntity sa SET sa.applySt = ApplyStatus.Deploy WHERE sa.applySt = ApplyStatus.Ready")
    int updateDeployApply();
}
