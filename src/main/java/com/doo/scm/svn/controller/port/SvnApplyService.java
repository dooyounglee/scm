package com.doo.scm.svn.controller.port;

import java.util.List;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplyFileCreate;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplySelect;
import com.doo.scm.svn.domain.SvnApply;
import com.doo.scm.svn.domain.SvnApplyFile;

public interface SvnApplyService {
    
    public List<SvnApplyFile> deployReady();
    public SvnApply insertApply(List<SvnApplyFileCreate> dto);
    public List<SvnApply> selectApplys();
    public SvnApply updateApplySt(SvnApplySelect dto, ApplyStatus applySt);
    public int updateDeployApply();
}
