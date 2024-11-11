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
        <script>
            $(function(){
                selectRevisions();
            });
        </script>
    </head>
    <body>
        <h3>메인</h3>

        <div>
            <table>
                <thead>
                    <tr>
                        <td>revision</td>
                        <td>author</td>
                        <td>date</td>
                        <td>message</td>
                        <td>#file</td>
                        <td>상세</td>
                    </tr>
                </thead>
                <tbody id="revisions">
                </tbody>
            </table>
            <br>
        </div>

        <div>
            <div id="selectedRevision"></div>
            <table>
                <thead>
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>type</td>
                        <td>path</td>
                        <td>copyPath</td>
                        <td>copyRevision</td>
                        <td>kind</td>
                        <td>선택</td>
                        <td>내용</td>
                    </tr>
                </thead>
                <tbody id="changedPath"></tbody>
            </table>
            <br>
        </div>

        <div>
            <table>
                <thead>
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>revision</td>
                        <td>author</td>
                        <td>date</td>
                        <td>message</td>
                    </tr>
                </thead>
                <tbody id="revisionsByFile">
                </tbody>
            </table>
            <button onclick="selectFileDiff()">비교</button>
            <br>
        </div>

        <table id="tb" border=1 ></table>

        <textarea id="fileContent" rows="30"></textarea>
    </body>
</html>