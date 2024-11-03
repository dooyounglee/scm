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
    $("#commits").html('');
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
        $("#commits tr:last td:nth-child(5)").on('click', () => btnSelectFiles(element.sha))
    });
}

export const btnSelectFiles = (sha) => {
    api("/repos/dooyounglee/betting/commits/" + sha, "GET", selectFiles, sha);
}

const selectFiles = (result, sha) => {
    $("#files").html('');
    result.files.forEach(element => {
        var str = "<tr>";
        str += "<td>" + element.filename + "</td>";
        str += "<td>" + element.status + "</td>";
        str += "<td>" + element.changes + "</td>";
        str += "<td>" + element.additions + "</td>";
        str += "<td>" + element.deletions + "</td>";
        str += `<td><button>선택</button></td>`;
        str += `<td><button>상세</button></td>`;
        str += "</tr>";
        $("#files").append(str);
        $("#files tr:last td:nth-child(6)").on('click', () => btnSelectCommitsByFile(element.filename))
        $("#files tr:last td:nth-child(7)").on('click', () => btnRawFileContent(element.filename, sha))
    });
}

const btnSelectCommitsByFile = (path) => {
    api(`/repos/dooyounglee/betting/commits?path=${path}`, "GET", selectCommitsByFile, path);
}

const selectCommitsByFile = (result, path) => {
    $("#commitsByFile").html('');
    result.forEach(element => {
        var str = "<tr>";
        str += `<td><input type="checkbox" name="commit" value="${element.sha}"></td>`
        str += `<td>${element.sha}</td>`;
        str += `<td>${element.commit.message}</td>`;
        str += "<td>" + element.commit.committer.name + "</td>";
        str += "<td>" + element.commit.committer.date + "</td>";
        str += "<td><button>비교</button></td>";
        str += "</tr>";
        $("#commitsByFile").append(str);
        $("#commitsByFile tr:last td:nth-child(6)").on('click', () => btnCompareCommits(path))
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
    document.getElementById("tb").innerHTML = '';
    
    var compare = result.files.filter(file => file.previous_filename || file.filename == path)[0].patch;
    compare = compare.replace(/ @@ /gi," @@\n ");
    compare = compare.split("\n");
    var skip = 0;
    for (var i=0;i<compare.length;i++) {
        console.log(compare[i]);
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
        // console.log(a,before,after)
        
        function view() {
            var tb = document.getElementById("tb")
            tb.innerHTML += '<tr><td></td><td align="center"></td><td></td></tr>';
            var lastTr = $("#tb tr:last-child").get().reverse()[0];
            var td = lastTr.getElementsByTagName('td');
            td[0].innerText += before[0] == "-" ? " " + before.substring(1) : before;
            if (before[0] == "-") td[0].style = "background-color:#ffc4c4;";
            td[1].innerText += i-skip;
            td[2].innerText += after[0] == "+" ? " " + after.substring(1) : after;
            if (after[0] == "+") td[2].style = "background-color:#caffca;";
        }
        view();
    }
}

export const api = async (url, method, callback, args) => {
    var result = await octokit.request(method + " " + url, {
        owner: 'OWNER',
        repo: 'REPO',
        headers: {
            'X-GitHub-Api-Version': '2022-11-28',
        }
    });
    console.log(result, args);
    callback(result.data, args);
}