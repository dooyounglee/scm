package com.doo.scm.svn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.svn.controller.port.SvnApplyFileService;
import com.doo.scm.svn.controller.port.SvnApplyService;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplyFileCreate;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplySelect;
import com.doo.scm.svn.controller.response.SvnApplyFileResponse;
import com.doo.scm.svn.controller.response.SvnApplyResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/svn")
@RequiredArgsConstructor
public class SvnApplyController {
    
    private final SvnApplyService svnApplyService;
    private final SvnApplyFileService svnApplyFileService;

    @ResponseBody
    @RequestMapping("/deployReady")
    public ResponseEntity<List<SvnApplyFileResponse>> deployReady() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(svnApplyService.deployReady().stream().map(svnDeploy -> SvnApplyFileResponse.from(svnDeploy)).toList());
    }

    @ResponseBody
    @RequestMapping("/selectApplys")
    public ResponseEntity<List<SvnApplyResponse>> selectApplys() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(svnApplyService.selectApplys().stream().map(svnApply -> SvnApplyResponse.from(svnApply)).toList());
    }

    @ResponseBody
    @RequestMapping("/selectApplyFiles")
    public ResponseEntity<List<SvnApplyFileResponse>> selectApplyFiles(@RequestBody SvnApplySelect dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(svnApplyFileService.selectApplyFiles(dto).stream().map(svnApplyFile -> SvnApplyFileResponse.from(svnApplyFile)).toList());
    }

    @ResponseBody
    @RequestMapping("/checkDeployList")
    public ResponseEntity<List<SvnApplyFileResponse>> checkDeployList(@RequestBody List<SvnApplyFileCreate> dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(svnApplyFileService.checkDeployList(dto).stream().map(svnApplyFile -> SvnApplyFileResponse.from(svnApplyFile)).toList());
    }

    @ResponseBody
    @RequestMapping("/insertApply")
    public ResponseEntity<SvnApplyResponse> deployApply(@RequestBody List<SvnApplyFileCreate> dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(SvnApplyResponse.from(svnApplyService.insertApply(dto)));
    }

    @ResponseBody
    @RequestMapping("/updateReadyApply")
    public ResponseEntity<SvnApplyResponse> updateReadyApply(@RequestBody SvnApplySelect dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(SvnApplyResponse.from(svnApplyService.updateApplySt(dto, ApplyStatus.Ready)));
    }

    @ResponseBody
    @RequestMapping("/cancleReadyApply")
    public ResponseEntity<SvnApplyResponse> cancleReadyApply(@RequestBody SvnApplySelect dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(SvnApplyResponse.from(svnApplyService.updateApplySt(dto, ApplyStatus.None)));
    }

    @ResponseBody
    @RequestMapping("/updateDeployApply")
    public ResponseEntity<Integer> updateDeployApply() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(svnApplyService.updateDeployApply());
    }
}
