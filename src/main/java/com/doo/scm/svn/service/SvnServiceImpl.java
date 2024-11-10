package com.doo.scm.svn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String selectFileContent(RevisionSelect dto) {
        System.out.println(SvnUtil.displayFileContent(dto.getFile(), dto.getRevision()));
        return SvnUtil.displayFileContent(dto.getFile(), dto.getRevision());
    }

    public String selectFileDiff(RevisionSelect dto) {
        return SvnUtil.compareRevision(dto.getFile(), dto.getRevision(), dto.getRevision1());
    }


    public List<Map<String, String>> deployReady() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("path","commit1.txt");
        map.put("revision","1");
        list.add(map);

        map = new HashMap<>();
        map.put("path","svn");
        map.put("revision","2");
        list.add(map);

        map = new HashMap<>();
        map.put("path","svn/commit2.txt");
        map.put("revision","2");
        list.add(map);

        // return "echo 'jenkins' | sudo -kS svn update --username=doo --password=doo " + map.get("path") + " -r" + map.get("revision") + " --depth empty";
        // return "1 commit1.txt,2 svn,2 svn/commit2.txt";
        //return map;
        return list;
    }
    
}
