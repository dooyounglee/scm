<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="/main.css" rel="stylesheet" type="text/css"></style>
        <script
            src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
            crossorigin="anonymous"></script>
        <script src="/common.js"></script>
        <script type="module">
            import { btnSelectCommits } from "/history.js";

            $(function(){
                $("#btnPage").on('click', () => btnSelectCommits())
                $("#btnPageBack").on('click', () => btnPageBack())
                $("#btnPageForward").on('click', () => btnPageForward())
                btnSelectCommits();
                // api("/repos/dooyounglee/betting/commits", "GET", selectCommits);
                // selectRepository();
                // selectBranches();
                // selectCommits();
                // api("/repos/doo/test/contents", "GET", (result) => console.log(JSON.parse(result)));
                // api("/repos/doo/test/git/blob/17b5cda0be2e2493570476b67eeff15adc8bb787", "GET", console.log);
                // api("/repos/doo/test/contents/first.txt", "GET");
                // api("/repos/doo/test/git/commits/a6c3bd4be0", "GET");
                // api("/repos/doo/test/commits", "GET", console.log);
            });

            const btnPageBack = () => {
                if ($("#page").val() - 1 < 1) return false;
                $("#page").val($("#page").val() - 1);
                btnSelectCommits();
            }

            const btnPageForward = () => {
                $("#page").val(parseInt($("#page").val()) + 1);
                btnSelectCommits();
            }
        </script>
    </head>
    <body>
        <h3>메인</h3>

        <div>
            <table>
                <thead>
                    <tr>
                        <td>sha</td>
                        <td>message</td>
                        <td>author</td>
                        <td>date</td>
                        <td>상세</td>
                    </tr>
                </thead>
                <tbody id="commits">
                </tbody>
            </table>
            <button id="btnPageBack">◀</button>
            <input id="page" value="1"></input>
            <button id="btnPage">page</button>
            <button id="btnPageForward">▶</button>
            <br>
        </div>

        <div>
            <table>
                <thead>
                    <tr>
                        <td>path</td>
                        <td>status</td>
                        <td>changes</td>
                        <td>additions</td>
                        <td>deletions</td>
                        <td>선택</td>
                        <td>내용</td>
                    </tr>
                </thead>
                <tbody id="files"></tbody>
            </table>
        </div>

        <div>
            <table>
                <thead>
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>sha</td>
                        <td>message</td>
                        <td>author</td>
                        <td>date</td>
                        <td>상세</td>
                    </tr>
                </thead>
                <tbody id="commitsByFile">
                </tbody>
            </table>
            <button onclick="selectFileDiff()">비교</button>
            <br>
        </div>

        <table id="tb" border=1 ></table>

        <textarea id="rawFileContent" rows="30"></textarea>
    </body>
</html>