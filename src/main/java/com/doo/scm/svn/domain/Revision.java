package com.doo.scm.svn.domain;

import java.util.Date;
import java.util.List;

import org.tmatesoft.svn.core.SVNLogEntry;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Revision {
    
    private long revision;
    private String author;
    private Date date;
    private String message;
    private List<ChangedPath> changedPath;

    public static Revision from(SVNLogEntry logEntry, List<ChangedPath> changedPath) {
        return Revision.builder()
            .revision(logEntry.getRevision())
            .author(logEntry.getAuthor())
            .date(logEntry.getDate())
            .message(logEntry.getMessage())
            .changedPath(changedPath)
            .build();
    }
}
