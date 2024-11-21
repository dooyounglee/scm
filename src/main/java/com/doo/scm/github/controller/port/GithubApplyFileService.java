package com.doo.scm.github.controller.port;

import java.util.List;

import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplySelect;
import com.doo.scm.github.domain.GithubApplyFile;

public interface GithubApplyFileService {
    
    public List<GithubApplyFile> selectApplyFiles(GithubApplySelect dto);
    // public List<GithubApplyFile> checkDeployList(List<GithubApplyFileCreate> dto);
}
