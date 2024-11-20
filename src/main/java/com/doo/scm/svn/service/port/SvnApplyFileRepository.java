package com.doo.scm.svn.service.port;

import java.util.List;

import com.doo.scm.svn.domain.SvnApplyFile;

public interface SvnApplyFileRepository {
    
    List<SvnApplyFile> selectReadyApplyFiles();
    // List<SvnApplyFile> findAllByOrderByPathAscAndRevisionAsc();
    SvnApplyFile save(SvnApplyFile svnApplyFile);
    List<SvnApplyFile> saveAll(List<SvnApplyFile> svnApplyFiles);
    List<SvnApplyFile> findByApplyNo(long applyNo);
    int checkDeployList(SvnApplyFile svnApplyFile);
}
