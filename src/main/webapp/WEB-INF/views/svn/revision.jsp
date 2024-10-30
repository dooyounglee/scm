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
                            str += "<td><button onclick='selectFileContent(\""+element.path+"\",\""+revision+"\",\""+element.kindId+"\")'>내용</button></td>";
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
                            str += "<td>" + "<input type='checkbox' name='revision' value='" + element.revision + "'>" + "</td>"
                            str += "<td>" + element.revision + "</td>";
                            str += "<td>" + element.author + "</td>";
                            str += "<td>" + element.date + "</td>";
                            str += "<td>" + element.message + "</td>";
                            str += "<td><button onclick='selectFileDiff(\""+file+"\")'>비교</button></td>";
                            str += "</tr>";
                            $("#revisionsByFile").append(str);
                        });
                    },
                    error: (request, status, error) => {
                        alert(request.responseJSON.message);
                    }
                });
            }

            function selectFileContent(path, revision, kindId) {
                if (kindId != 1) {
                    alert("파일이 아닙니다.");
                    return false;
                }

                $.ajax({
                    type: 'post',
                    url: '/svn/selectFileContent',
                    async: true,
                    headers: {
                        "Content-Type": "application/json",
                    },
                    dataType: 'json',
                    data: JSON.stringify({
                        revision: revision,
                        file: path,
                    }),
                    success: (result) => {
                        console.log(result)
                        $("#fileContent").val(result.fileContent);
                    },
                    error: (request, status, error) => {
                        console.log(request);
                    }
                });
            }

            function selectFileDiff(path) {
                var revision = $("input[name='revision']:checked")[0].value;
                var revision1 = $("input[name='revision']:checked")[1].value;

                $.ajax({
                    type: 'post',
                    url: '/svn/selectFileDiff',
                    async: true,
                    headers: {
                        "Content-Type": "application/json",
                    },
                    dataType: 'json',
                    data: JSON.stringify({
                        revision: revision1,
                        revision1: revision,
                        file: path,
                    }),
                    success: (result) => {
                        document.getElementById("tb").innerHTML = '';
                        
                        var compare = result.fileContent.split("\r\n");
                        for (var i=compare.length-1;i>=0;i--) {
                            if (compare[i] == "\\ No newline at end of file") compare.splice(i,1);
                        }
                        var skip = 0;
                        for (var i=4;i<compare.length-1;i++) {
                            var a = compare[i];
                            var before, after;
                            var head = a[0];
                            if (head == "+" && compare[i-1][0] == "-") {
                                skip++;
                                continue;
                            } else if (head == "-" && compare[i+1][0] == "+") {
                                before = a;
                                after = compare[i+1];
                            } else if (head == "+") {
                                before = "";
                                after = a;
                            } else if (head == "-") {
                                before = a;
                                after = "";
                            } else {
                                before = a;
                                after = a;
                            }

                            function view() {
                                var tb = document.getElementById("tb")
                                tb.innerHTML += '<tr><td></td><td align="center"></td><td></td></tr>';
                                var tr = tb.getElementsByTagName('tr');
                                var td = tr[i-4-skip].getElementsByTagName('td');
                                td[0].innerText += before[0] == "-" ? before.substring(1) : before;
                                if (before[0] == "-") td[0].style = "background-color:#ffc4c4;";
                                td[1].innerText += i-4-skip;
                                td[2].innerText += after[0] == "+" ? after.substring(1) : after;
                                if (after[0] == "+") td[2].style = "background-color:#caffca;";
                            }
                            view();
                        }
                    },
                    error: (request, status, error) => {
                        console.log(request);
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
                        <td>내용</td>
                    </tr>
                </thead>
                <tbody id="changedPath"></tbody>
            </table>
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