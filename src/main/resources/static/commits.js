function api(url, method, callback, args) {
    console.log(url)
    $.ajax({
        type: 'post',
        url: '/gitea/api',
        async: true,
        headers: {
            "Content-Type": "application/json",
        },
        dataType: 'text',
        data: JSON.stringify({
            method: method,
            url: url,
        }),
        success: result => {
            callback(JSON.parse(result), args);
        },
        error: (request, status, error) => {
            console.log(error);
        }
    });
}

function btnSelectCommits() {
    api("/repos/doo/test/commits", "GET", selectCommits);
}

function selectCommits(result) {
    $("#commits").html('');
    result.forEach(element => {
        var str = "<tr>";
        // str += "<td>" + element.sha + "</td>";
        str += `<td>${element.sha}</td>`;
        str += `<td>${element.commit.message}</td>`;
        str += "<td>" + element.commit.committer.name + "</td>";
        str += "<td>" + element.commit.committer.date + "</td>";
        str += `<td><button onclick="btnSelectCommit('${element.sha}')">상세</button></td>`;
        str += "</tr>";
        $("#commits").append(str);
    });
}

function btnSelectCommit(sha) {
    api("/repos/doo/test/git/commits/" + sha, "GET", selectCommit);
}

function selectCommit(result) {
    console.log(result)
    $("#commit").html('');
    result.files.forEach(element => {
        var str = "<tr>";
        str += "<td>" + element.filename + "</td>";
        str += "<td>" + element.status + "</td>";
        str += `<td><button onclick="btnSelectCommitsByFile('${element.filename}')">선택</button></td>`;
        str += `<td><button onclick="btnRawFileContent('${element.filename}','${result.sha}')">소스</button></td>`;
        str += "</tr>";
        $("#commit").append(str);
    });
}

function btnSelectCommitsByFile(path) {
    api(`/repos/doo/test/commits?path=${path}`, "GET", selectCommitsByFile, path);
}

function selectCommitsByFile(result, path) {
    $("#commitsByFile").html('');
    result.forEach(element => {
        var str = "<tr>";
        str += `<td><input type="checkbox" name="commit" value="${element.sha}"></td>`
        str += `<td>${element.sha}</td>`;
        str += `<td>${element.commit.message}</td>`;
        str += "<td>" + element.commit.committer.name + "</td>";
        str += "<td>" + element.commit.committer.date + "</td>";
        str += `<td><button onclick="btnCompare('${path}')">비교</button></td>`;
        str += "</tr>";
        $("#commitsByFile").append(str);
    });
}

function btnRawFileContent(path, sha) {
    api(`/repos/doo/test/contents/${path}?ref=${sha}`, "GET", getRawFileContent);
}

function getRawFileContent(result) {
    // $("#rawFileContent").html(atob(unescape(encodeURIComponent(result.content))));
    $("#rawFileContent").html(decodeURIComponent(escape(atob(result.content))));
}

function btnCompare(path) {
    // alert("아직");
    // return false;
    var commit = $("input[name='commit']:checked")[0].value;
    var commit1 = $("input[name='commit']:checked")[1].value;

    api(`/repos/doo/test/compare/${commit}...${commit1}`, "GET", compare);
}

function compare(result) {
    console.log(result);
}