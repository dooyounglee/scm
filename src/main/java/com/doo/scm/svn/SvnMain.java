package com.doo.scm.svn;

import com.doo.scm.common.SvnUtil;

public class SvnMain {
    
    public static void main(String[] args) {
        System.out.println(SvnUtil.compareRevision("commit1.txt",4,5));
    }
}
