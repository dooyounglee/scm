package com.doo.scm.github.controller.port;

import java.util.List;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplyFileCreate;
import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplySelect;
import com.doo.scm.github.domain.GithubApply;
import com.doo.scm.github.domain.GithubApplyFile;

public interface GithubApplyService {
    
    public List<GithubApplyFile> deployReady();
    public GithubApply insertApply(List<GithubApplyFileCreate> dto);
    public List<GithubApply> selectApplys();
    public GithubApply updateApplySt(GithubApplySelect dto, ApplyStatus applySt);
    public int updateDeployApply();
}
