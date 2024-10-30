package com.doo.scm.svn.controller.response;

import java.util.Date;
import java.util.List;

import com.doo.scm.svn.domain.ChangedPath;
import com.doo.scm.svn.domain.Revision;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SvnResponse {

    private long revision;
    private String author;
    private Date date;
    private String message;
    private List<ChangedPath> changedPath;
    private String fileContent;

    public static SvnResponse from(Revision revision) {
        return SvnResponse.builder()
            .revision(revision.getRevision())
            .author(revision.getAuthor())
            .date(revision.getDate())
            .message(revision.getMessage())
            .changedPath(revision.getChangedPath())
            .build();
    }

    public static SvnResponse from(String selectFileContent) {
        return SvnResponse.builder()
            .fileContent(selectFileContent)
            .build();
    }
}
