package com.doo.scm.svn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doo.scm.svn.controller.request.RevisionRequest.RevisionSelect;
import com.doo.scm.svn.controller.response.SvnResponse;
import com.doo.scm.svn.service.SvnServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/svn")
@RequiredArgsConstructor
public class SvnController {
    
    private final SvnServiceImpl svnService;

    @ResponseBody
    @RequestMapping("/selectRevisions")
    public ResponseEntity<List<SvnResponse>> selectRevisions() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(svnService.selectRevisions().stream().map(revision -> SvnResponse.from(revision)).toList());
    }

    @ResponseBody
    @RequestMapping("/selectRevision")
    public ResponseEntity<SvnResponse> selectRevisions(@RequestParam("revision") long revision) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(SvnResponse.from(svnService.selectRevision(revision)));
    }

    @ResponseBody
    @RequestMapping("/selectRevisionsByFile")
    public ResponseEntity<List<SvnResponse>> selectRevisionsByFile(@RequestBody RevisionSelect dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(svnService.selectRevisionsByFile(dto).stream().map(revision -> SvnResponse.from(revision)).toList());
    }
}
