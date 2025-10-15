package com.doo.scm.github.controller.port;

import com.doo.scm.github.controller.request.GithubJavaRequest.GithubJavaCompareRequest;

public interface GithubJavaService {
    
    public String compare(GithubJavaCompareRequest dto);
}
