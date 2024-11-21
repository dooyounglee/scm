package com.doo.scm.github.service.port;

import java.util.List;

import com.doo.scm.github.domain.GithubApplyFile;

public interface GithubApplyFileRepository {
    
    List<GithubApplyFile> selectReadyApplyFiles();
    // List<GithubApplyFile> findAllByOrderByPathAscAndRevisionAsc();
    GithubApplyFile save(GithubApplyFile githubApplyFile);
    List<GithubApplyFile> saveAll(List<GithubApplyFile> githubApplyFiles);
    List<GithubApplyFile> findByApplyNo(long applyNo);
    // int checkDeployList(GithubApplyFile githubApplyFile);
}
