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
}
