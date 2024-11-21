package com.doo.scm.github.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.github.controller.port.GithubApplyService;
import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplyFileCreate;
import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplySelect;
import com.doo.scm.github.domain.GithubApply;
import com.doo.scm.github.domain.GithubApplyFile;
import com.doo.scm.github.service.port.GithubApplyFileRepository;
import com.doo.scm.github.service.port.GithubApplyRepository;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class GithubApplyServiceImpl implements GithubApplyService {
    
    private final GithubApplyRepository githubApplyReplository;
    private final GithubApplyFileRepository githubApplyFileReplository;

    public GithubApply getGithubApply(long applyNo) {
        return githubApplyReplository.findById(applyNo)
            .orElseThrow(() -> new RuntimeException("Github신청이 없어요"));
    }

    public List<GithubApplyFile> deployReady() {
        return githubApplyFileReplository.selectReadyApplyFiles();
    }

    public GithubApply insertApply(List<GithubApplyFileCreate> dto) {
        GithubApply githubApply = githubApplyReplository.save(
            GithubApply.builder()
                .applyDt("20241111")
                .applySt(ApplyStatus.None)
                .build());
        
        dto.forEach(e -> {
            githubApplyFileReplository.save(GithubApplyFile.from(githubApply.getApplyNo(), e));
        });
        return githubApply;
    }

    @Override
    public List<GithubApply> selectApplys() {
        return githubApplyReplository.findAll();
    }

    @Override
    public GithubApply updateApplySt(GithubApplySelect dto, ApplyStatus applySt) {
        GithubApply githubApply = getGithubApply(dto.getApplyNo());
        githubApply = githubApply.updateApplySt(applySt);
        return githubApplyReplository.save(githubApply);
    }

    @Override
    public int updateDeployApply() {
        return githubApplyReplository.updateDeployApply();
    }
}
