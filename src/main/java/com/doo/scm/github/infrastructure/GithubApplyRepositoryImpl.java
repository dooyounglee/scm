package com.doo.scm.github.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.doo.scm.github.domain.GithubApply;
import com.doo.scm.github.service.port.GithubApplyRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GithubApplyRepositoryImpl implements GithubApplyRepository {
    
    private final GithubApplyJpaRepository githubApplyJpaRepository;

    @Override
    public List<GithubApply> findAll() {
        return githubApplyJpaRepository.findAll().stream().map(GithubApplyEntity::toModel).toList();
    }

    @Override
    public Optional<GithubApply> findById(long id) {
        return githubApplyJpaRepository.findById(id).map(GithubApplyEntity::toModel);
    }
    
    @Override
    public GithubApply save(GithubApply githubApply) {
        return githubApplyJpaRepository.save(GithubApplyEntity.from(githubApply)).toModel();
    }

    @Override
    public int updateDeployApply() {
        return githubApplyJpaRepository.updateDeployApply();
    }
}
