package com.doo.scm.github.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Service;

import com.doo.scm.github.controller.port.GithubJavaService;
import com.doo.scm.github.controller.request.GithubJavaRequest.GithubJavaCompareRequest;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

@Service
public class GithubJavaServiceImpl implements GithubJavaService {

    @Override
    public String compare(GithubJavaCompareRequest dto) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(dto.getPersonalAccessToken()).build();
            GHRepository repository = github.getRepository(dto.getRepository());
            String file = dto.getPath();
            String base = dto.getCommit1();
            String head = dto.getCommit();

            // 두 버전 파일 내용 가져오기
            String baseContent = readFile(repository, file, base);
            String headContent = readFile(repository, file, head);

            List<String> original = Arrays.asList(baseContent.split("\n"));
            List<String> revised = Arrays.asList(headContent.split("\n"));

            Patch<String> patch = DiffUtils.diff(original, revised);

            return generateSideBySideHtmlDiff(original, revised, patch, file, base, head);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private String readFile(GHRepository repo, String path, String ref) throws IOException {
        GHContent content = repo.getFileContent(path, ref);
        return new String(content.read().readAllBytes(), StandardCharsets.UTF_8);
    }

    // === HTML Diff 생성 ===
    private String generateSideBySideHtmlDiff(List<String> original, List<String> revised, Patch<String> patch, String filePath, String baseSha, String headSha) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
<html>
<head>
<meta charset='UTF-8'>
<style>
body { font-family: monospace; background: #f8f9fa; padding: 20px; }
table { width: 100%; border-collapse: collapse; border: 1px solid #ccc; }
th { background: #e9ecef; padding: 8px; text-align: left; }
td { white-space: pre; padding: 2px 8px; vertical-align: top; }
.del { background-color: #ffe6e6; } /* 삭제 */
.add { background-color: #e6ffe6; } /* 추가 */
.same { background-color: #ffffff; }
.lineno { color: #999; text-align: right; width: 40px; }
.meta { font-size: 14px; color: #666; margin-bottom: 10px; }
</style>
</head>
<body>
                """);

        sb.append("<h2>파일 비교: ").append(filePath).append("</h2>");
        sb.append("<div >Base: ").append(baseSha)
                .append("<br>Head: ").append(headSha).append("</div>");

        sb.append("<table>");
        sb.append("<tr><th colspan='2'>Base</th><th colspan='2'>Head</th></tr>");

        int baseIndex = 0;
        int headIndex = 0;

        for (AbstractDelta<String> delta : patch.getDeltas()) {
            int unchangedBefore = delta.getSource().getPosition() - baseIndex;

            // 변경 전 동일 라인 출력
            // for (int i = 0; i < unchangedBefore && baseIndex < original.size() && headIndex < revised.size(); i++) {
            //     appendRow(sb, original.get(baseIndex), baseIndex, revised.get(headIndex), headIndex, "same");
            //     baseIndex++;
            //     headIndex++;
            // }
            for (int i = 0; i < unchangedBefore && baseIndex < original.size() && headIndex < revised.size(); i++) {
                appendRow(sb, original.get(baseIndex), baseIndex, revised.get(headIndex), headIndex, "same");
                baseIndex++;
                headIndex++;
            }

            // 변경 블록 처리
            switch (delta.getType()) {
                case DELETE -> {
                    for (String line : delta.getSource().getLines()) {
                        appendRow(sb, line, baseIndex++, "", "", "del");
                    }
                }
                case INSERT -> {
                    for (String line : delta.getTarget().getLines()) {
                        appendRow(sb, "", "", line, headIndex++, "add");
                    }
                }
                case CHANGE -> {
                    List<String> src = delta.getSource().getLines();
                    List<String> tgt = delta.getTarget().getLines();
                    int max = Math.max(src.size(), tgt.size());
                    for (int i = 0; i < max; i++) {
                        String oldLine = (i < src.size()) ? src.get(i) : "";
                        String newLine = (i < tgt.size()) ? tgt.get(i) : "";
                        appendRow(sb, oldLine, i < src.size() ? baseIndex : "", newLine, i < tgt.size() ? headIndex : "", "change");
                        if (i < src.size()) baseIndex++;
                        if (i < tgt.size()) headIndex++;
                    }
                }
                case EQUAL -> throw new UnsupportedOperationException("Unimplemented case: " + delta.getType());
                // default -> throw new IllegalArgumentException("Unexpected value: " + delta.getType());
            }
        }

        // 남은 동일 부분 출력
        while (baseIndex < original.size() || headIndex < revised.size()) {
            String left = (baseIndex < original.size()) ? original.get(baseIndex) : "";
            String right = (headIndex < revised.size()) ? revised.get(headIndex) : "";
            appendRow(sb, left, ++baseIndex, right, ++headIndex, "same");
        }

        sb.append("</table></body></html>");
        return sb.toString();
    }

    private void appendRow(StringBuilder sb, String leftLine, Object leftNo,
            String rightLine, Object rightNo, String cls) {
        sb.append("<tr>")
                .append("<td >").append(leftNo).append("</td>")
                .append("<td class='").append(cls).append("'>")
                .append(escapeHtml(leftLine)).append("</td>")
                .append("<td >").append(rightNo).append("</td>")
                .append("<td class='").append(cls).append("'>")
                .append(escapeHtml(rightLine)).append("</td>")
                .append("</tr>");
    }

    private String escapeHtml(String s) {
        return s == null ? ""
                : s.replace("&", "&")
                        .replace("<", "<")
                        .replace(">", ">");
    }
}
