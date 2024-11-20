package com.doo.scm.svn.service.port;

import java.util.List;
import java.util.Optional;

import com.doo.scm.svn.domain.SvnApply;

public interface SvnApplyRepository {
    
    List<SvnApply> findAll();
    Optional<SvnApply> findById(long applyNo);
    SvnApply save(SvnApply svnApply);
    int updateDeployApply();
}
