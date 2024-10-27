package com.doo.scm.svn.domain;

import org.tmatesoft.svn.core.SVNLogEntryPath;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangedPath {
    
    private char type;
    private String path;
    private String copyPath;
    private long copyRevision;
    private int kindId;
    private String kindNm;

    public static ChangedPath from(SVNLogEntryPath logEntryPath) {
        return ChangedPath.builder()
        .type(logEntryPath.getType())
        .path(logEntryPath.getPath())
        .copyPath(logEntryPath.getCopyPath())
        .copyRevision(logEntryPath.getCopyRevision())
        .kindId(logEntryPath.getKind().getID())
        .kindNm(logEntryPath.getKind().toString())
        .build();
    }
}
