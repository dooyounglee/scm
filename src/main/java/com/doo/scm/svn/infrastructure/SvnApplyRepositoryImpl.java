package com.doo.scm.svn.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.doo.scm.svn.domain.SvnApply;
import com.doo.scm.svn.service.port.SvnApplyRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SvnApplyRepositoryImpl implements SvnApplyRepository {
    
    private final SvnApplyJpaRepository svnApplyJpaRepository;

    @Override
    public List<SvnApply> findAll() {
        return svnApplyJpaRepository.findAll().stream().map(SvnApplyEntity::toModel).toList();
    }

    @Override
    public Optional<SvnApply> findById(long id) {
        return svnApplyJpaRepository.findById(id).map(SvnApplyEntity::toModel);
    }
    
    @Override
    public SvnApply save(SvnApply svnApply) {
        return svnApplyJpaRepository.save(SvnApplyEntity.from(svnApply)).toModel();
    }

    @Override
    public int updateDeployApply() {
        return svnApplyJpaRepository.updateDeployApply();
    }
}
