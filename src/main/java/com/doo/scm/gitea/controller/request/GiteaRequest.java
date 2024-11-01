package com.doo.scm.gitea.controller.request;

import lombok.Data;

@Data
public class GiteaRequest {
    
    private String url;
    private String method;
}
