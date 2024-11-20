package com.doo.scm.svn.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SvnApplyJpaRepository extends JpaRepository<SvnApplyEntity, Long> {
    
    @Modifying
    @Query("UPDATE SvnApplyEntity sa SET sa.applySt = ApplyStatus.Deploy WHERE sa.applySt = ApplyStatus.Ready")
    int updateDeployApply();
}
