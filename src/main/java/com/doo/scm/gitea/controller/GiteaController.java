package com.doo.scm.gitea.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doo.scm.gitea.controller.request.GiteaRequest;
import com.doo.scm.gitea.service.GiteaServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/gitea")
@RequiredArgsConstructor
public class GiteaController {
    
    private final GiteaServiceImpl giteaService;

    @ResponseBody
    @RequestMapping("/selectCommits")
    public ResponseEntity<String> selectRevisions() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(giteaService.selectCommits());
    }

    @ResponseBody
    @RequestMapping("/api")
    public ResponseEntity<String> api(@RequestBody GiteaRequest dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(giteaService.api(dto));
    }
}
