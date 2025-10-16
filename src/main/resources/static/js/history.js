import { Octokit, App } from "https://esm.sh/octokit";
import { githubPersonalAccessToken } from "./secret.js";

const octokit = new Octokit({
    auth: githubPersonalAccessToken,
});

export const btnSelectCommits = () => {
    var page = $('#page').val() || 1
    api("/repos/dooyounglee/betting/commits?per_page=10&page=" + page, "GET", selectCommits);
}

export const selectCommits = (result) => {
    clearCommits();
    clearFiles();
    clearCommitsByFile();
    clearCompare();
    result.forEach(element => {
        var str = "<tr>";
        str += `<td>${element.sha}</td>`;
        str += `<td>${element.commit.message}</td>`;
        str += "<td>" + element.commit.committer.name + "</td>";
        str += "<td>" + element.commit.committer.date + "</td>";
        // str += `<td><button onclick="btnSelectTrees('\${element.sha}')">상세</button></td>`;
        str += "<td><button>상세</button></td>";
        str += "</tr>";
        $("#commits").append(str);
        $("#commits tr:last td:nth-child(5)").on('click', () => btnSelectFiles(element.sha, element.commit.committer.date))
    });
}

export const btnSelectFiles = (sha, commitDt) => {
    api("/repos/dooyounglee/betting/commits/" + sha, "GET", selectFiles, sha, commitDt);
}

const selectFiles = (result, sha, commitDt) => {
    $("#selectedCommitId").text(sha);

    clearFiles();
    clearCommitsByFile();
    clearCompare();
    result.files.forEach(element => {
        var str = "<tr>";
        str += "<td>" + "<input type='checkbox' name='path'>" + "</td>"
        str += "<td>" + element.filename + "</td>";
        str += "<td>" + element.status + "</td>";
        str += "<td>" + element.changes + "</td>";
        str += "<td>" + element.additions + "</td>";
        str += "<td>" + element.deletions + "</td>";
        str += `<td><button>선택</button></td>`;
        str += `<td><button>상세</button></td>`;
        str += "</tr>";
        $("#files").append(str);
        $("#files tr:last td:nth-child(7)").on('click', () => btnSelectCommitsByFile(element.filename));
        $("#files tr:last td:nth-child(8)").on('click', () => btnRawFileContent(element.filename, sha));
        $("#files tr:last td:nth-child(1) input").data('path', element.filename).data('commitDt', commitDt);
    });
}

const btnSelectCommitsByFile = (path) => {
    api(`/repos/dooyounglee/betting/commits?path=${path}`, "GET", selectCommitsByFile, path);
}

const selectCommitsByFile = (result, path) => {
    clearCommitsByFile();
    clearCompare();
    result.forEach(element => {
        var str = "<tr>";
        str += `<td><input type="checkbox" name="commit" value="${element.sha}"></td>`
        str += `<td>${element.sha}</td>`;
        str += `<td>${element.commit.message}</td>`;
        str += "<td>" + element.commit.committer.name + "</td>";
        str += "<td>" + element.commit.committer.date + "</td>";
        str += "<td><button>비교</button></td>";
        str += "<td><button>java비교</button></td>";
        str += "<td><button>script비교</button></td>";
        str += "</tr>";
        $("#commitsByFile").append(str);
        $("#commitsByFile tr:last td:nth-child(6)").on('click', () => btnCompareCommits(path));
        $("#commitsByFile tr:last td:nth-child(7)").on('click', () => btnCompareCommits_java(path));
        $("#commitsByFile tr:last td:nth-child(8)").on('click', () => btnCompareCommits_script(path));
    });
}

const btnRawFileContent = (path, sha) => {
    // api("/repos/dooyounglee/betting/git/blobs/" + sha, "GET", getRawFileContent);
    api(`/repos/dooyounglee/betting/contents/${path}?ref=${sha}`, "GET", getRawFileContent);
}

const getRawFileContent = result => {
    $("#rawFileContent").html(atob(result.content));
}

const btnCompareCommits = (path) => {
    var commit = $("input[name='commit']:checked")[0].value;
    var commit1 = $("input[name='commit']:checked")[1].value;
    api(`/repos/dooyounglee/betting/compare/${commit1}...${commit}`, "GET", compareCommits, path);
}

const compareCommits = (result, path) => {
    clearCompare();
    
    var compare = result.files.filter(file => file.previous_filename || file.filename == path)[0].patch;
    compare = compare.replace(/ @@ /gi," @@\n ");
    compare = compare.split("\n");
    var skip = 0;
    var line_before = 0;
    var line_after = 0;
    console.log(compare)
    for (var i=0;i<compare.length;i++) {
        console.log(compare[i]);
        if (compare[i][0] == "@" && compare[i][1] == "@") {
            var _minus = compare[i].indexOf("-");
            var _minus_comma = compare[i].indexOf(",", _minus);
            line_before = parseInt(compare[i].substring(_minus+1,_minus_comma));
            line_before = Math.max(line_before,3);
            var _plus = compare[i].indexOf("+");
            var _plus_comma = compare[i].indexOf(",", _plus);
            line_after = parseInt(compare[i].substring(_plus+1,_plus_comma));
            line_after = Math.max(line_after,3);
        }
        var a = compare[i];
        var before, after, before_line, after_line;
        var head = a[0];
        if (head == "@") {
            before_line = "";
            after_line = "";
            before = a;
            after = a;
        } else if (head == "+" && compare[i-1][0] == "-") {
            skip++;
            continue;
        } else if (head == "-" && compare[i+1][0] == "+") {
            before_line = line_before++;
            after_line = line_after++;
            before = a;
            after = compare[i+1];
        } else if (head == "+") {
            before_line = "";
            after_line = line_after++;
            before = "";
            after = a;
        } else if (head == "-") {
            before_line = line_before++;
            after_line = "";
            before = a;
            after = "";
        } else {
            before_line = line_before++;
            after_line = line_after++;
            before = a;
            after = a;
        }
        
        function view() {
            var tb = document.getElementById("tb")
            tb.innerHTML += '<tr><td align="center"></td><td></td><td align="center"></td><td></td></tr>';
            var lastTr = $("#tb tr:last-child").get().reverse()[0];
            var td = lastTr.getElementsByTagName('td');
            td[0].innerText += before_line;
            td[1].innerText += before[0] == "-" ? " " + before.substring(1) : before;
            if (before[0] == "-") {
                td[0].style = "background-color:#ffc4c4;";
                td[1].style = "background-color:#ffc4c4;";
            }
            td[2].innerText += after_line;
            td[3].innerText += after[0] == "+" ? " " + after.substring(1) : after;
            if (after[0] == "+") {
                td[2].style = "background-color:#caffca;";
                td[3].style = "background-color:#caffca;";
            }
        }
        view();

        if (compare[i][0] == "@" && compare[i][1] == "@") {
            i++;
        }
    }
}

const btnCompareCommits_script = (path) => {
    var commit = $("input[name='commit']:checked")[0].value;
    var commit1 = $("input[name='commit']:checked")[1].value;
    // api(`/repos/dooyounglee/betting/compare/${commit1}...${commit}`, "GET", compareCommits, path);

    var oldText,newText;

    const _getRawFileContent1 = result => {
        oldText = decodeURIComponent(escape(atob(result.content)));
        if (oldText && newText) draw(oldText, newText);
    }
    const _getRawFileContent2 = result => {
        newText = decodeURIComponent(escape(atob(result.content)));
        if (oldText && newText) draw(oldText, newText);
    }
    api(`/repos/dooyounglee/betting/contents/${path}?ref=${commit}`, "GET", _getRawFileContent1);
    api(`/repos/dooyounglee/betting/contents/${path}?ref=${commit1}`, "GET", _getRawFileContent2);

    const draw = (o,n) => {
        // Unified diff 생성
        const diff = Diff.createTwoFilesPatch('OldFile.js', 'NewFile.js', o, n, '', '', { context: Number.MAX_SAFE_INTEGER });

        // diff2html 렌더링
        const targetElement = document.getElementById('diff');

        const diff2htmlUi = new Diff2HtmlUI(targetElement, diff, {
            drawFileList: false,
            outputFormat: 'side-by-side',
            matching: 'lines'
        });

        diff2htmlUi.draw();
    }
}

const btnCompareCommits_java = (path) => {
    var commit = $("input[name='commit']:checked")[0].value;
    var commit1 = $("input[name='commit']:checked")[1].value;
    window.open(`/github/compare?commit=${commit}&commit1=${commit1}&path=${path}&repository=dooyounglee/betting&personalAccessToken=${githubPersonalAccessToken}`)
}

const clearCommits = () => {
    $("#commits").html('');
}

const clearFiles = () => {
    $("#files").html('');
}

const clearCommitsByFile = () => {
    $("#commitsByFile").html('');
}

const clearCompare = () => {
    if (document.getElementById("tb") != undefined)
        document.getElementById("tb").innerHTML = '';
    document.getElementById("diff").innerHTML = '';
}

export const api = async (url, method, callback, ...args) => {
    var result = await octokit.request(method + " " + url, {
        owner: 'OWNER',
        repo: 'REPO',
        headers: {
            'X-GitHub-Api-Version': '2022-11-28',
        }
    });
    console.log(result, ...args);
    callback(result.data, ...args);
}

export const selectApplys = () => {
    $.ajax({
        type: 'get',
        url: '/github/selectApplys',
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
                str += `<td><button>상세</button></td>`;
                if (element.applySt == "None") str += `<td><button>배포준비</button></td>`;
                if (element.applySt == "Ready") str += `<td><button>배포취소</button></td>`;
                if (element.applySt == "Deploy") str += `<td>완료</td>`;
                str += "</tr>";
                $("#applys").append(str);
                $("#applys tr:last td:nth-child(4)").on('click', () => selectApply(element.applyNo));
                if (element.applySt == "None") $("#applys tr:last td:nth-child(5)").on('click', () => updateReadyApply(element.applyNo));
                if (element.applySt == "Ready") $("#applys tr:last td:nth-child(5)").on('click', () => cancleReadyApply(element.applyNo));
            });
        },
        error: (request, status, error) => {
            console.log(error);
        }
    });
}

const selectApply = (applyNo) => {
    $.ajax({
        type: 'post',
        url: '/github/selectApplyFiles',
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
                return 0;
            });
            result.forEach(element => {
                var str = "<tr>";
                str += "<td>" + element.path + "</td>";
                str += "<td>" + element.commitId + "</td>";
                str += "<td>" + element.commitDt + "</td>";
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
                
export const addDeployList = () => {
    var selectedCommitId = $("#selectedCommitId").text();
    $("input[name='path']:checked").each((i,e) => {
        var index = deployList.findIndex(i => i.path == e.value && i.commitId == selectedCommitId);
        if (index > -1) return false;

        var obj = {};
        obj["commitId"] = selectedCommitId;
        obj["path"] = $(e).data("path");
        obj["commitDt"] = $(e).data("commitDt");
        deployList.push(obj);
    });

    showList();
}

// const checkDeployList = () => {
//     $.ajax({
//         type: 'post',
//         url: '/github/checkDeployList',
//         async: true,
//         headers: {
//             "Content-Type": "application/json",
//         },
//         dataType: 'json',
//         data: JSON.stringify(deployList),
//         success: (result) => {
//             result.forEach(e => {
//                 var index = deployList.findIndex(i => i.path == e.path);
//                 if (index > -1) {
//                     deployList[index]["deployed"] = "O";
//                 }
//             });
//             showList();
//         },
//         error: (request, status, error) => {
//             console.log(request);
//         }
//     });
// }

const removeDeployList = (path, commitId) => {
    var index = deployList.findIndex(i => i.path == path && i.commitId == commitId);
    deployList.splice(index,1);

    showList();
}

const showList= () => {
    $("#deployList").html('');
    /* deployList.sort((a,b) => {
        if (a.path < b.path) return -1;
        if (a.path > b.path) return 1;
        return 0;
    }); */
    deployList.sort((a,b) => sortObject(a,b,"path","commitId"));
    deployList.forEach(element => {
        var str = "<tr>";
        str += "<td>" + element.path + "</td>";
        str += "<td>" + element.commitId + "</td>";
        str += "<td>" + element.commitDt + "</td>";
        // str += "<td>" + (element.deployed || "") + "</td>";
        str += `<td><button>삭제</button></td>`;
        str += "</tr>";
        $("#deployList").append(str);
        $("#deployList tr:last td:nth-child(4)").on('click', () => removeDeployList(element.path, element.commitId));
    });

    releaseAll();
}

const sortObject = (a,b, ...param) => {
    for (var i=0; i<param.length; i++) {
        if (a[param[i]] < b[param[i]]) return -1;
        if (a[param[i]] > b[param[i]]) return 1;
    }
    return 0;
}

export const insertApply = () => {
    $.ajax({
        type: 'post',
        url: '/github/insertApply',
        async: true,
        headers: {
            "Content-Type": "application/json",
        },
        dataType: 'json',
        data: JSON.stringify(deployList),
        success: (result) => {
            selectApplys();

            $("#selectedCommitId").html('');
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

const updateReadyApply = (applyNo) => {
    $.ajax({
        type: 'post',
        url: '/github/updateReadyApply',
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

            $("#selectedCommitId").html('');
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

const cancleReadyApply = (applyNo) => {
    $.ajax({
        type: 'post',
        url: '/github/cancleReadyApply',
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

            $("#selectedCommitId").html('');
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

export const selectAll = (input_check) => {
    $("input[name='path']").prop("checked", input_check.target.checked);
}

export const releaseAll = () => {
    $("input:checkbox").prop("checked", false);
}