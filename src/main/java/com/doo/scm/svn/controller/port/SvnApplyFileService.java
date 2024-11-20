package com.doo.scm.svn.controller.port;

import java.util.List;

import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplyFileCreate;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplySelect;
import com.doo.scm.svn.domain.SvnApplyFile;

public interface SvnApplyFileService {
    
    public List<SvnApplyFile> selectApplyFiles(SvnApplySelect dto);
    public List<SvnApplyFile> checkDeployList(List<SvnApplyFileCreate> dto);
}
