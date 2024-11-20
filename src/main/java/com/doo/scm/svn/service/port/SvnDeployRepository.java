package com.doo.scm.svn.service.port;

import java.util.List;

import com.doo.scm.svn.domain.SvnDeploy;

public interface SvnDeployRepository {
    
    List<SvnDeploy> findAll();
    List<SvnDeploy> saveAll(List<SvnDeploy> list);
}
