package com.doo.scm.svn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doo.scm.common.enums.ApplyStatus;
import com.doo.scm.svn.controller.port.SvnApplyService;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplyFileCreate;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplySelect;
import com.doo.scm.svn.domain.SvnApply;
import com.doo.scm.svn.domain.SvnApplyFile;
import com.doo.scm.svn.domain.SvnDeploy;
import com.doo.scm.svn.service.port.SvnApplyFileRepository;
import com.doo.scm.svn.service.port.SvnApplyRepository;
import com.doo.scm.svn.service.port.SvnDeployRepository;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class SvnApplyServiceImpl implements SvnApplyService {
    
    private final SvnApplyRepository svnApplyReplository;
    private final SvnApplyFileRepository svnApplyFileReplository;
    private final SvnDeployRepository svnDeployRepository;

    public SvnApply getSvnApply(long applyNo) {
        return svnApplyReplository.findById(applyNo)
            .orElseThrow(() -> new RuntimeException("Svn신청이 없어요"));
    }

    public List<SvnApplyFile> deployReady() {
        return svnApplyFileReplository.selectReadyApplyFiles();
    }

    public SvnApply insertApply(List<SvnApplyFileCreate> dto) {
        SvnApply svnApply = svnApplyReplository.save(
            SvnApply.builder()
                .applyDt("20241111")
                .applySt(ApplyStatus.None)
                .build());
        
        dto.forEach(e -> {
            svnApplyFileReplository.save(SvnApplyFile.from(svnApply.getApplyNo(), e));
        });
        return svnApply;
    }

    @Override
    public List<SvnApply> selectApplys() {
        return svnApplyReplository.findAll();
    }

    @Override
    public SvnApply updateApplySt(SvnApplySelect dto, ApplyStatus applySt) {
        SvnApply svnApply = getSvnApply(dto.getApplyNo());
        svnApply = svnApply.updateApplySt(applySt);
        return svnApplyReplository.save(svnApply);
    }

    @Override
    public int updateDeployApply() {
        List<SvnApplyFile> list = svnApplyFileReplository.selectReadyApplyFiles();
        svnDeployRepository.saveAll(list.stream().map(svnApplyFile -> SvnDeploy.from(svnApplyFile)).toList());
        return svnApplyReplository.updateDeployApply();
    }
}
