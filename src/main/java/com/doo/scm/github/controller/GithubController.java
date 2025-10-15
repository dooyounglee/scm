package com.doo.scm.github.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doo.scm.github.controller.port.GithubJavaService;
import com.doo.scm.github.controller.request.GithubJavaRequest.GithubJavaCompareRequest;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubJavaService githubJavaService;
    
    // @ResponseBody
    // @RequestMapping("/api")
    // public ResponseEntity<String> api(@RequestBody GithubRequest dto) {
    //     return ResponseEntity.status(HttpStatus.OK)
    //         .body(githubService.api(dto));
    // }

    @ResponseBody
    @RequestMapping(value = "/compare", produces = MediaType.TEXT_HTML_VALUE)
    public String compare(GithubJavaCompareRequest dto) {
        return githubJavaService.compare(dto);
    }
}
