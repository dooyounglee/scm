package com.doo.scm.svn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doo.scm.common.SvnUtil;
import com.doo.scm.svn.controller.request.RevisionRequest.RevisionSelect;
import com.doo.scm.svn.domain.Revision;

@Service
public class SvnServiceImpl {

    public List<Revision> selectRevisions() {
        return SvnUtil.getRevisions(1, -1);
    }

    public Revision selectRevision(long revision) {
        return SvnUtil.getRevision(revision);
    }

    public List<Revision> selectRevisionsByFile(RevisionSelect dto) {
        return SvnUtil.getFileRevisions(dto.getFile(), 1, -1);
    }
    
}
