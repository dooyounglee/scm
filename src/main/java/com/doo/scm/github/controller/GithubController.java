package com.doo.scm.github.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doo.scm.github.controller.request.GithubRequest;
import com.doo.scm.github.service.GithubServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/github")
@RequiredArgsConstructor
public class GithubController {

    private GithubServiceImpl githubService;
    
    @ResponseBody
    @RequestMapping("/api")
    public ResponseEntity<String> api(@RequestBody GithubRequest dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(githubService.api(dto));
    }
}
