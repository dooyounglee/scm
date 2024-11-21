package com.doo.scm.github.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.doo.scm.github.domain.GithubApplyFile;
import com.doo.scm.github.service.port.GithubApplyFileRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GithubApplyFileRepositoryImpl implements GithubApplyFileRepository {
    
    private final GithubApplyFileJpaRepository githubApplyFileJpaRepository;
    
    @Override
    public List<GithubApplyFile> selectReadyApplyFiles() {
        return githubApplyFileJpaRepository.selectReadyApplyFiles().stream().map(GithubApplyFileEntity::toModel).toList();
    }

    @Override
    public GithubApplyFile save(GithubApplyFile githubApplyFile) {
        return githubApplyFileJpaRepository.save(GithubApplyFileEntity.from(githubApplyFile)).toModel();
    }

    @Override
    public List<GithubApplyFile> saveAll(List<GithubApplyFile> githubApplyFiles) {
        return githubApplyFileJpaRepository.saveAll(githubApplyFiles.stream().map(githubApplyFile -> GithubApplyFileEntity.from(githubApplyFile)).toList())
            .stream().map(GithubApplyFileEntity::toModel).toList();
    }

    @Override
    public List<GithubApplyFile> findByApplyNo(long applyNo) {
        return githubApplyFileJpaRepository.findByApplyNo(applyNo).stream().map(GithubApplyFileEntity::toModel).toList();
    }

    // @Override
    // public int checkDeployList(GithubApplyFile githubApplyFile) {
    //     return githubApplyFileJpaRepository.countAllByPathAndRevisionGreaterThanEqual(githubApplyFile.getPath(), githubApplyFile.getCommitId());
    // }
}
