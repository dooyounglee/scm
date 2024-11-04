package com.doo.scm.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.ISVNFileRevisionHandler;
import org.tmatesoft.svn.core.io.SVNFileRevision;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDiffWindow;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.doo.scm.svn.domain.ChangedPath;
import com.doo.scm.svn.domain.Revision;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SvnUtil {
    
    private static String url = "svn://localhost";
    private static String username = "doo";
    private static String password = "doo";

    public static SVNURL getSvnUrl() {
        SVNURL svnUrl = null;
        try {
            svnUrl = SVNURL.parseURIEncoded(url);
        } catch (SVNException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return svnUrl;
    }

    public static SVNURL getSvnUrl(String filePath) {
        try {
            return SVNURL.parseURIEncoded(filePath);
        } catch (SVNException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static SVNClientManager clientManagerInstance() {
        SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true),"doo","doo");
        clientManager.isIgnoreExternals();
        return clientManager;
    }

    public static SVNRepository getSvnRepository() {
        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        } catch (SVNException e) {
            e.printStackTrace();
        }

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        repository.setAuthenticationManager(authManager);

        return repository;
    }

    public static long getEndRevision() {
        SVNRepository repository = getSvnRepository();
        try {
            return repository.getLatestRevision();
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Revision getRevision(long endRevision) {
        return getRevisions(endRevision, endRevision).get(0);
    }

    public static List<Revision> getRevisions(long startRevision, long endRevision) {
        List<Revision> list = new ArrayList<>();

        SVNRepository repository = getSvnRepository();

        Collection<SVNLogEntry> logEntries = null;
        try {
            logEntries = repository.log(new String[] {""}, null, startRevision, endRevision, true, true);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        
        for (Iterator<SVNLogEntry> entries = logEntries.iterator(); entries.hasNext();) {
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();
            
            List<ChangedPath> list2 = new ArrayList<>();
            Map<String, SVNLogEntryPath> map = logEntry.getChangedPaths();
            map.forEach((key,value) -> {
                list2.add(ChangedPath.from(value));
            });

            list.add(Revision.from(logEntry, list2));
        }

        return list;
    }

    public static List<Revision> getFileRevisions(String path, long startRevision, long endRevision) {
        List<Revision> list = new ArrayList<>();

        SVNRepository repository = getSvnRepository();

        try {
            repository.getFileRevisions(path, startRevision, endRevision, false, new ISVNFileRevisionHandler() {
                public void applyTextDelta(String path, String baseChecksum) throws SVNException {
                }
 
             
                public void closeRevision(String token) throws SVNException {
                }
 
                public void openRevision(SVNFileRevision fileRevision) throws SVNException {
                    list.add(getRevision(fileRevision.getRevision()));
                }
 
                public OutputStream textDeltaChunk(String path, SVNDiffWindow diffWindow) throws SVNException {
                    return null;
                }
 
                public void textDeltaEnd(String path) throws SVNException {
                }
             });
        } catch (SVNException e) {
            e.printStackTrace();
            throw new RuntimeException("파일이 아닙니다.");
        }

        return list;
    }

    public static String compareRevision(String filePath, long revision1, long revision2) {
        SVNClientManager clientManager = clientManagerInstance();
        SVNDiffClient diffClient = clientManager.getDiffClient();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SVNURL svnUrl = getSvnUrl(url + "/" + filePath);
        try {
            // diffClient.doDiff(svnUrl, SVNRevision.create(1), svnUrl, SVNRevision.HEAD, false, false, System.out);
            diffClient.doDiff(svnUrl, SVNRevision.create(revision1), svnUrl, SVNRevision.create(revision2), false, false, baos);
            return new String(baos.toByteArray(), "UTF-8");
        } catch (SVNException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String displayFileContent(String filePath, long revision) {
        SVNProperties fileProperties = new SVNProperties();
        SVNRepository repository = getSvnRepository();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            repository.getFile(filePath, revision, fileProperties, baos);
            // baos.writeTo(System.out);
            return new String(baos.toByteArray(), "UTF-8");
        } catch (SVNException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
