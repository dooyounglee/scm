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
            $("#selectedRevision").text(revision);

            $("#changedPath").html('');
            result.changedPath.forEach(element => {
                var str = "<tr>";
                str += "<td>" + "<input type='checkbox' name='path' value='" + element.path + "'>" + "</td>"
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

function selectApplys() {
    $.ajax({
        type: 'get',
        url: '/svn/selectApplys',
        async: true,
        headers: {
            "Content-Type": "application/json",
        },
        // dataType: 'text',
        // data: JSON.stringify({}),
        success: (result) => {
            $("#applys").html('');
            result.reverse().forEach(element => {
                var str = "<tr>";
                str += "<td>" + element.applyNo + "</td>";
                str += "<td>" + element.applyDt + "</td>";
                str += "<td>" + element.applySt + "</td>";
                str += `<td><button onclick='selectApply(${element.applyNo})'>상세</button></td>`;
                if (element.applySt == "None") str += `<td><button onclick='updateReadyApply(${element.applyNo})'>배포준비</button></td>`;
                if (element.applySt == "Ready") str += `<td><button onclick='cancleReadyApply(${element.applyNo})'>배포취소</button></td>`;
                if (element.applySt == "Deploy") str += `<td>완료</td>`;
                str += "</tr>";
                $("#applys").append(str);
            });
        },
        error: (request, status, error) => {
            console.log(error);
        }
    });
}

function selectApply(applyNo) {
    $.ajax({
        type: 'post',
        url: '/svn/selectApplyFiles',
        async: true,
        headers: {
            "Content-Type": "application/json",
        },
        dataType: 'json',
        data: JSON.stringify({
            applyNo: applyNo
        }),
        success: (result) => {
            $("#selectedApplyNo").text(applyNo);

            $("#applyFiles").html('');
            result.sort((a,b) => {
                if (a.path < b.path) return -1;
                if (a.path > b.path) return 1;
                return a.revision - b.revision;
            });
            result.forEach(element => {
                var str = "<tr>";
                str += "<td>" + element.path + "</td>";
                str += "<td>" + element.revision + "</td>";
                str += "</tr>";
                $("#applyFiles").append(str);
            });
        },
        error: (request, status, error) => {
            console.log(error);
        }
    });
}

let deployList = [];
                
function addDeployList() {
    var selectedRevision = $("#selectedRevision").text();
    $("input[name='path']:checked").each((i,e) => {
        var index = deployList.findIndex(i => i.path == e.value.substring(1) && i.revision == selectedRevision);
        if (index > -1) return false;

        var obj = {};
        obj["revision"] = selectedRevision;
        obj["path"] = e.value.substring(1);
        deployList.push(obj);
    });

    showList();
}

function checkDeployList() {
    $.ajax({
        type: 'post',
        url: '/svn/checkDeployList',
        async: true,
        headers: {
            "Content-Type": "application/json",
        },
        dataType: 'json',
        data: JSON.stringify(deployList),
        success: (result) => {
            result.forEach(e => {
                var index = deployList.findIndex(i => i.path == e.path);
                if (index > -1) {
                    deployList[index]["deployed"] = "O";
                }
            });
            showList();
        },
        error: (request, status, error) => {
            console.log(request);
        }
    });
}

function removeDeployList(path, revision) {
    var index = deployList.findIndex(i => i.path == path && i.revision == revision);
    deployList.splice(index,1);

    showList();
}

function showList() {
    $("#deployList").html('');
    deployList.sort((a,b) => {
        if (a.path < b.path) return -1;
        if (a.path > b.path) return 1;
        return a.revision - b.revision;
    });
    deployList.forEach(element => {
        var str = "<tr>";
        str += "<td>" + element.path + "</td>";
        str += "<td>" + element.revision + "</td>";
        str += "<td>" + (element.deployed || "") + "</td>";
        str += `<td><button onclick='removeDeployList("${element.path}","${element.revision}")'>삭제</button></td>`;
        str += "</tr>";
        $("#deployList").append(str);
    });

    releaseAll();
}

function insertApply() {
    $.ajax({
        type: 'post',
        url: '/svn/insertApply',
        async: true,
        headers: {
            "Content-Type": "application/json",
        },
        dataType: 'json',
        data: JSON.stringify(deployList),
        success: (result) => {
            selectApplys();

            $("#selectedRevision").html('');
            $("#changedPath").html('');
            $("#deployList").html('');
            deployList = [];
            showList();
        },
        error: (request, status, error) => {
            console.log(request);
        }
    });
}

function updateReadyApply(applyNo) {
    $.ajax({
        type: 'post',
        url: '/svn/updateReadyApply',
        async: true,
        headers: {
            "Content-Type": "application/json",
        },
        dataType: 'json',
        data: JSON.stringify({
            applyNo: applyNo,
        }),
        success: (result) => {
            selectApplys();

            $("#selectedRevision").html('');
            $("#changedPath").html('');
            $("#deployList").html('');
            deployList = [];
            showList();
        },
        error: (request, status, error) => {
            console.log(request);
        }
    });
}

function cancleReadyApply(applyNo) {
    $.ajax({
        type: 'post',
        url: '/svn/cancleReadyApply',
        async: true,
        headers: {
            "Content-Type": "application/json",
        },
        dataType: 'json',
        data: JSON.stringify({
            applyNo: applyNo,
        }),
        success: (result) => {
            selectApplys();

            $("#selectedRevision").html('');
            $("#changedPath").html('');
            $("#deployList").html('');
            deployList = [];
            showList();
        },
        error: (request, status, error) => {
            console.log(request);
        }
    });
}

function selectAll(input_check) {
    $("input[name='path']").prop("checked", input_check.checked);
}

function releaseAll() {
    $("input:checkbox").prop("checked", false);
}