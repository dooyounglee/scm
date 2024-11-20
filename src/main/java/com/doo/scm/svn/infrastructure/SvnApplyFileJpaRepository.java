package com.doo.scm.svn.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SvnApplyFileJpaRepository extends JpaRepository<SvnApplyFileEntity, Long> {
    
    @Query("SELECT saf FROM SvnApplyEntity sa JOIN SvnApplyFileEntity saf ON sa.applyNo = saf.applyNo WHERE sa.applySt = ApplyStatus.Ready ORDER BY saf.path, saf.revision")
    List<SvnApplyFileEntity> selectReadyApplyFiles();
    // List<SvnApplyFileEntity> findAllByOrderByPathAscRevisionAsc();
    List<SvnApplyFileEntity> findByApplyNo(long applyNo);
    int countAllByPathAndRevisionGreaterThanEqual(String path, long revision);
}
