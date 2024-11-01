package com.doo.scm.github.service;

import org.springframework.stereotype.Service;

import com.doo.scm.common.GithubUtil;
import com.doo.scm.github.controller.request.GithubRequest;

@Service
public class GithubServiceImpl {

    public String api(GithubRequest dto) {
        return GithubUtil.api(dto);
    }
    
}
