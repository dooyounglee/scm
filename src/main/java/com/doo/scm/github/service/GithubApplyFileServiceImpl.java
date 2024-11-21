package com.doo.scm.github.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doo.scm.github.controller.port.GithubApplyFileService;
import com.doo.scm.github.controller.request.GithubApplyRequest.GithubApplySelect;
import com.doo.scm.github.domain.GithubApplyFile;
import com.doo.scm.github.service.port.GithubApplyFileRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class GithubApplyFileServiceImpl implements GithubApplyFileService {
    
    private final GithubApplyFileRepository githubApplyFileRepository;

    @Override
    public List<GithubApplyFile> selectApplyFiles(GithubApplySelect dto) {
        return githubApplyFileRepository.findByApplyNo(dto.getApplyNo());
    }

    // @Override
    // public List<GithubApplyFile> checkDeployList(List<GithubApplyFileCreate> dto) {
    //     List<GithubApplyFile> list = new ArrayList<>();
    // 
    //     dto.forEach(githubApplyFileCreate -> {
    //         GithubApplyFile githubApplyFile = GithubApplyFile.from(githubApplyFileCreate);
    //         if (githubApplyFileRepository.checkDeployList(githubApplyFile) > 0)
    //             list.add(githubApplyFile);
    //     });
    // 
    //     return list;
    // }
}
