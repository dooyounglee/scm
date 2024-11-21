package com.doo.scm.github.service.port;

import java.util.List;
import java.util.Optional;

import com.doo.scm.github.domain.GithubApply;

public interface GithubApplyRepository {
    
    List<GithubApply> findAll();
    Optional<GithubApply> findById(long applyNo);
    GithubApply save(GithubApply githubApply);
    int updateDeployApply();
}
