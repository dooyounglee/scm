package com.doo.scm.gitea.service;

import org.springframework.stereotype.Service;

import com.doo.scm.common.GiteaUtil;
import com.doo.scm.gitea.controller.request.GiteaRequest;

@Service
public class GiteaServiceImpl {

    public String selectCommits() {
        return GiteaUtil.getCommits();
    }

    public String api(GiteaRequest dto) {
        return GiteaUtil.api(dto);
    }
}
