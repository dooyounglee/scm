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

            function selectRevisions() {
                $.ajax({
                    type: 'get',
                    url: '/svn/selectRevisions',
                    async: true,
                    headers: {
                        "Content-Type": "application/json",
                    },
                    // dataType: 'text',
                    // data: JSON.stringify({}),
                    success: (result) => {
                        $("#revisions").html('');
                        result.reverse().forEach(element => {
                            var str = "<tr>";
                            str += "<td>" + element.revision + "</td>";
                            str += "<td>" + element.author + "</td>";
                            str += "<td>" + element.date + "</td>";
                            str += "<td>" + element.message + "</td>";
                            str += "<td>" + element.changedPath.length + "</td>";
                            str += "<td><button onclick='selectRevision("+element.revision+")'>상세</button></td>";
                            // str += "<td><button onclick='selectBet("+element.betNo+")'>수정</button></td>";
                            str += "</tr>";
                            $("#revisions").append(str);
                        });
                    },
                    error: (request, status, error) => {
                        console.log(error);
                    }
                });
            }

            function selectRevision(revision) {
                $.ajax({
                    type: 'get',
                    url: '/svn/selectRevision',
                    async: true,
                    headers: {
                        "Content-Type": "application/json",
                    },
                    // dataType: 'text',
                    data: {
                        revision: revision,
                    },
                    success: (result) => {
                        $("#changedPath").html('');
                        result.changedPath.forEach(element => {
                            var str = "<tr>";
                            str += "<td>" + element.type + "</td>";
                            str += "<td>" + element.path + "</td>";
                            str += "<td>" + element.copyPath + "</td>";
                            str += "<td>" + element.copyRevision + "</td>";
                            str += "<td>" + element.kindNm + "</td>";
                            str += "<td><button onclick='selectRevisionsByFile(\""+element.path+"\",\""+element.kindId+"\")'>선택</button></td>";
                            str += "</tr>";
                            $("#changedPath").append(str);
                        });

                        $("#revisionsByFile").html('');
                    },
                    error: (request, status, error) => {
                        console.log(request);
                    }
                });
            }

            function selectRevisionsByFile(file,kindId) {
                if (kindId != 1) {
                    alert("파일이 아닙니다.");
                    return false;
                }
                
                $.ajax({
                    type: 'post',
                    url: '/svn/selectRevisionsByFile',
                    async: true,
                    headers: {
                        "Content-Type": "application/json",
                    },
                    dataType: 'json',
                    data: JSON.stringify({
                        file: file
                    }),
                    success: (result) => {
                        $("#revisionsByFile").html('');
                        result.reverse().forEach(element => {
                            var str = "<tr>";
                            str += "<td>" + element.revision + "</td>";
                            str += "<td>" + element.author + "</td>";
                            str += "<td>" + element.date + "</td>";
                            str += "<td>" + element.message + "</td>";
                            str += "</tr>";
                            $("#revisionsByFile").append(str);
                        });
                    },
                    error: (request, status, error) => {
                        alert(request.responseJSON.message);
                    }
                });
            }
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
            <table>
                <thead>
                    <tr>
                        <td>type</td>
                        <td>path</td>
                        <td>copyPath</td>
                        <td>copyRevision</td>
                        <td>kind</td>
                        <td>선택</td>
                    </tr>
                </thead>
                <tbody id="changedPath"></tbody>
            </table>
        </div>

        <div>
            <table>
                <thead>
                    <tr>
                        <td>revision</td>
                        <td>author</td>
                        <td>date</td>
                        <td>message</td>
                    </tr>
                </thead>
                <tbody id="revisionsByFile">
                </tbody>
            </table>
            <br>
        </div>
    </body>
</html>