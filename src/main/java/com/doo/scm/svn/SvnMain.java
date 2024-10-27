package com.doo.scm.svn;

import java.util.List;

import com.doo.scm.common.SvnUtil;
import com.doo.scm.svn.domain.Revision;

public class SvnMain {
    
    public static void main(String[] args) {
        long endRevision = SvnUtil.getEndRevision();
        List<Revision> list = SvnUtil.getFileRevisions("/svn/commit2.txt",1,-1);
        list.forEach(System.out::println);
    }
}
