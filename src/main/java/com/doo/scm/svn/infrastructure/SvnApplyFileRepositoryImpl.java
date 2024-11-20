package com.doo.scm.svn.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.doo.scm.svn.domain.SvnApplyFile;
import com.doo.scm.svn.service.port.SvnApplyFileRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SvnApplyFileRepositoryImpl implements SvnApplyFileRepository {

    private final SvnApplyFileJpaRepository svnApplyFileJpaRepository;
    
    @Override
    public List<SvnApplyFile> selectReadyApplyFiles() {
        return svnApplyFileJpaRepository.selectReadyApplyFiles().stream().map(SvnApplyFileEntity::toModel).toList();
    }

    @Override
    public SvnApplyFile save(SvnApplyFile svnApplyFile) {
        return svnApplyFileJpaRepository.save(SvnApplyFileEntity.from(svnApplyFile)).toModel();
    }

    @Override
    public List<SvnApplyFile> saveAll(List<SvnApplyFile> svnApplyFiles) {
        return svnApplyFileJpaRepository.saveAll(svnApplyFiles.stream().map(svnApplyFile -> SvnApplyFileEntity.from(svnApplyFile)).toList())
            .stream().map(SvnApplyFileEntity::toModel).toList();
    }

    @Override
    public List<SvnApplyFile> findByApplyNo(long applyNo) {
        return svnApplyFileJpaRepository.findByApplyNo(applyNo).stream().map(SvnApplyFileEntity::toModel).toList();
    }

    @Override
    public int checkDeployList(SvnApplyFile svnApplyFile) {
        return svnApplyFileJpaRepository.countAllByPathAndRevisionGreaterThanEqual(svnApplyFile.getPath(), svnApplyFile.getRevision());
    }
}
