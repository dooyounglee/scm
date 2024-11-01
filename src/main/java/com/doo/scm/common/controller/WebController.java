package com.doo.scm.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {
    
    @RequestMapping("/revisions")
    public String revisions() {
        return "svn/revision";
    }

    @RequestMapping("/commits")
    public String commits() {
        return "gitea/commits";
    }

    @RequestMapping("/history")
    public String history() {
        return "github/history";
    }
}
