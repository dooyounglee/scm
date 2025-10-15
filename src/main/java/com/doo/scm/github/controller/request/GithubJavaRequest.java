package com.doo.scm.github.controller.request;

import lombok.Data;

@Data
public class GithubJavaRequest {
    
    @Data
    public class GithubJavaCompareRequest {
        String path;
        String commit;
        String commit1;
        String repository;
        String personalAccessToken;
    }
}
