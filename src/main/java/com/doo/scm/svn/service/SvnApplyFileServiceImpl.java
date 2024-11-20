package com.doo.scm.svn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.doo.scm.svn.controller.port.SvnApplyFileService;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplyFileCreate;
import com.doo.scm.svn.controller.request.SvnDeployRequest.SvnApplySelect;
import com.doo.scm.svn.domain.SvnApplyFile;
import com.doo.scm.svn.service.port.SvnApplyFileRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class SvnApplyFileServiceImpl implements SvnApplyFileService {
    
    private final SvnApplyFileRepository svnApplyFileRepository;

    @Override
    public List<SvnApplyFile> selectApplyFiles(SvnApplySelect dto) {
        return svnApplyFileRepository.findByApplyNo(dto.getApplyNo());
    }

    @Override
    public List<SvnApplyFile> checkDeployList(List<SvnApplyFileCreate> dto) {
        List<SvnApplyFile> list = new ArrayList<>();

        dto.forEach(SvnApplyFileCreate -> {
            SvnApplyFile svnApplyFile = SvnApplyFile.from(SvnApplyFileCreate);
            if (svnApplyFileRepository.checkDeployList(svnApplyFile) > 0)
                list.add(svnApplyFile);
        });

        return list;
    }
}
