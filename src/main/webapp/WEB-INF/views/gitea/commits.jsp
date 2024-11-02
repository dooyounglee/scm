<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="/main.css" rel="stylesheet" type="text/css"></style>
        <script
            src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
            crossorigin="anonymous"></script>
        <script src="/commits.js"></script>
        <script>
            $(function(){
                btnSelectCommits();
            });
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
            <br>
        </div>

        <div>
            <table>
                <thead>
                    <tr>
                        <td>filename</td>
                        <td>status</td>
                        <td>선택</td>
                        <td>내용</td>
                    </tr>
                </thead>
                <tbody id="commit"></tbody>
            </table>
        </div>

        <div>
            <table>
                <thead>
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>sha</td>
                        <td>author</td>
                        <td>date</td>
                        <td>비교</td>
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