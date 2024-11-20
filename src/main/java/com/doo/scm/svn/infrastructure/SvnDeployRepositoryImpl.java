package com.doo.scm.svn.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.doo.scm.svn.domain.SvnDeploy;
import com.doo.scm.svn.service.port.SvnDeployRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SvnDeployRepositoryImpl implements SvnDeployRepository {
    
    private final SvnDeployJpaRepository svnDeployJpaRepository;

    @Override
    public List<SvnDeploy> findAll() {
        return svnDeployJpaRepository.findAll()
            .stream().map(SvnDeployEntity::toModel).toList();
    }

    @Override
    public List<SvnDeploy> saveAll(List<SvnDeploy> list) {
        return svnDeployJpaRepository.saveAll(list.stream().map(svnDeploy -> SvnDeployEntity.from(svnDeploy)).toList())
            .stream().map(SvnDeployEntity::toModel).toList();
    }
}
