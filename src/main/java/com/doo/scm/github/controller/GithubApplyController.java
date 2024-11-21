package com.doo.scm.github.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.github.controller.port.GithubApplyFileService;
import com.doo.scm.github.controller.port.GithubApplyService;
import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplyFileCreate;
import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplySelect;
import com.doo.scm.github.controller.response.GithubApplyFileResponse;
import com.doo.scm.github.controller.response.GithubApplyResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/github")
@RequiredArgsConstructor
public class GithubApplyController {
    
    private final GithubApplyService githubApplyService;
    private final GithubApplyFileService githubApplyFileService;

    @ResponseBody
    @RequestMapping("/deployReady")
    public ResponseEntity<List<GithubApplyFileResponse>> deployReady() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(githubApplyService.deployReady().stream().map(githubDeploy -> GithubApplyFileResponse.from(githubDeploy)).toList());
    }

    @ResponseBody
    @RequestMapping("/selectApplys")
    public ResponseEntity<List<GithubApplyResponse>> selectApplys() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(githubApplyService.selectApplys().stream().map(githubApply -> GithubApplyResponse.from(githubApply)).toList());
    }

    @ResponseBody
    @RequestMapping("/selectApplyFiles")
    public ResponseEntity<List<GithubApplyFileResponse>> selectApplyFiles(@RequestBody GithubApplySelect dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(githubApplyFileService.selectApplyFiles(dto).stream().map(githubApplyFile -> GithubApplyFileResponse.from(githubApplyFile)).toList());
    }

    // @ResponseBody
    // @RequestMapping("/checkDeployList")
    // public ResponseEntity<List<GithubApplyFileResponse>> checkDeployList(@RequestBody List<GithubApplyFileCreate> dto) {
    //     return ResponseEntity.status(HttpStatus.OK)
    //         .body(githubApplyFileService.checkDeployList(dto).stream().map(githubApplyFile -> GithubApplyFileResponse.from(githubApplyFile)).toList());
    // }

    @ResponseBody
    @RequestMapping("/insertApply")
    public ResponseEntity<GithubApplyResponse> deployApply(@RequestBody List<GithubApplyFileCreate> dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(GithubApplyResponse.from(githubApplyService.insertApply(dto)));
    }

    @ResponseBody
    @RequestMapping("/updateReadyApply")
    public ResponseEntity<GithubApplyResponse> updateReadyApply(@RequestBody GithubApplySelect dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(GithubApplyResponse.from(githubApplyService.updateApplySt(dto, ApplyStatus.Ready)));
    }

    @ResponseBody
    @RequestMapping("/cancleReadyApply")
    public ResponseEntity<GithubApplyResponse> cancleReadyApply(@RequestBody GithubApplySelect dto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(GithubApplyResponse.from(githubApplyService.updateApplySt(dto, ApplyStatus.None)));
    }

    @ResponseBody
    @RequestMapping("/updateDeployApply")
    public ResponseEntity<Integer> updateDeployApply() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(githubApplyService.updateDeployApply());
    }
}
