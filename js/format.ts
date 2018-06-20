import * as vscode from 'vscode';
const { formatText, cljify, jsify } = require('../cljc_out/calva_fmt');

function readConfiguration() {
    let workspaceConfig = vscode.workspace.getConfiguration("calva.fmt")
    return {
        "indentation?": workspaceConfig.get("indentation"),
        "remove-surrounding-whitespace?": workspaceConfig.get("removeSurroundingWhitespace"),
        "remove-trailing-whitespace?": workspaceConfig.get("removeTrailingWhitespace"),
        "insert-missin-whitespace?": workspaceConfig.get("insertMissingWhitespace")
    };
}

function getConfig() {
    let config = readConfiguration();
    config["remove-consecutive-blank-lines?"] = false; // Until we understand its behaviour better
    return config;
}

export function format(text) {
    let d = { "text": text, "config": getConfig() };
    d = formatText(cljify(d));
    d = jsify(d);
    if (!d["error"]) {
        return d["new-text"];
    }
    else {
        console.log(d["error"]);
        return text;
    }
}