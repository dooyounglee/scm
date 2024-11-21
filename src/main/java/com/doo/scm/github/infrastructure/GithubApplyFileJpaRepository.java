package com.doo.scm.github.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GithubApplyFileJpaRepository extends JpaRepository<GithubApplyFileEntity, Long> {
    
    @Query("SELECT saf FROM GithubApplyEntity sa JOIN GithubApplyFileEntity saf ON sa.applyNo = saf.applyNo WHERE sa.applySt = ApplyStatus.Ready ORDER BY saf.path, saf.commitDt desc")
    List<GithubApplyFileEntity> selectReadyApplyFiles();
    // List<GithubApplyFileEntity> findAllByOrderByPathAscRevisionAsc();
    List<GithubApplyFileEntity> findByApplyNo(long applyNo);
    // int countAllByPathAndRevisionGreaterThanEqual(String path, long revision);
}
