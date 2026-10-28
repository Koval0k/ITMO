"use strict";

const canvas = document.getElementById('graph');
    const ctx = canvas.getContext('2d');

    const width = canvas.width;
    const height = canvas.height;
    const R = 100;
    const centerX = width / 2;
    const centerY = height / 2;

    ctx.fillStyle = 'rgba(86, 156, 226, 0.45)';

    //triangle
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX, centerY + (R / 2));
    ctx.lineTo(centerX+R/2, centerY);
    ctx.fill();

    //rectangle
    ctx.beginPath();
    ctx.rect(centerX, centerY-R, R/2, R);
    ctx.fill();

    //circle
    ctx.beginPath();
    ctx.moveTo(centerX,centerY);
    ctx.arc(centerX,centerY, R, Math.PI, -Math.PI/2, false);
    ctx.lineTo(centerX, centerY);
    ctx.fill();

    ctx.strokeStyle = "#1e1e1e";
    ctx.lineWidth = 1.5;

    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, height);
    ctx.moveTo(centerX - 5, 10);
    ctx.lineTo(centerX, 0);
    ctx.lineTo(centerX + 5, 10);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(width, centerY);
    ctx.moveTo(width - 10, centerY - 5);
    ctx.lineTo(width, centerY);
    ctx.lineTo(width - 10, centerY + 5);
    ctx.stroke();

    ctx.font = "12px sans-serif";

    ctx.strokeText("0", centerX + 6, centerY - 6);
    ctx.strokeText("R/2", centerX + R / 2 - 6, centerY - 6);
    ctx.strokeText("R", centerX + R - 6, centerY - 6);

    ctx.strokeText("-R/2", centerX - R / 2 - 18, centerY - 6);
    ctx.strokeText("-R", centerX - R - 6, centerY - 6);

    ctx.strokeText("R/2", centerX + 6, centerY - R / 2 + 6);
    ctx.strokeText("R", centerX + 6, centerY - R + 6);

    ctx.strokeText("-R/2", centerX + 6, centerY + R / 2 + 6);
    ctx.strokeText("-R", centerX + 6, centerY + R + 6);

    const state = {
    x: 0,
    y: 0,
    r: 1.0,
    };

    const table = document.getElementById("result-table");
const error = document.getElementById("error");
const possibleXs = new Set([-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]);
const possibleRs = new Set([1.0, 1.5, 2.0, 2.5, 3.0]);

function isYInRange(yStr) {
    const negative = yStr.startsWith("-");
    const abs = negative ? yStr.slice(1) : yStr;
    const [intPart, fracPart = ""] = abs.split(".");

    const intNum = parseInt(intPart, 10);
    if (intNum > 5) return false;
    if (intNum < 5) return true;

    return /^0*$/.test(fracPart);
}

function showError(msg) {
    error.innerText = msg;
    error.classList.add("visible");
}

function clearError() {
    error.innerText = "";
    error.classList.remove("visible");
}

const validateState = (state) => {
    if (isNaN(state.x) || !possibleXs.has(state.x)) {
        showError(`x must be in [${[...possibleXs].join(" ,")}]`);
        throw new Error("Invalid state");
    }

    if (!/^-?\d{1,2}(\.\d+)?$/.test(state.y)) {
        showError("Y must be a valid number (e.g., -3.14 or 2.5)");
        throw new Error("Invalid state");
    }
    
    if (!isYInRange(state.y)) {
        showError("y must be in range [-5, 5]");
        throw new Error("Invalid state");
    }
    if (isNaN(state.r) || !possibleRs.has(state.r)) {
        showError(`r must be in [${[...possibleRs].join(" ,")}]`);
        throw new Error("Invalid state");
    }

    clearError();
}

const xSelect = document.getElementById("xs");
xSelect.addEventListener("change", (ev) => {
    state.x = parseFloat(ev.target.value);
    clearError();
});

state.x = parseFloat(xSelect.value);

    let selectedRBtn = null;

Array.from(document.getElementById("rs").children)
    .filter(c => c.tagName === "INPUT")
    .forEach(btn => {
        btn.style.border = "";
        btn.addEventListener("click", function (ev) {
            if (selectedRBtn !== null) {
                selectedRBtn.style.border = "";
            }
            selectedRBtn = btn;
            state.r = parseFloat(ev.target.value);
            selectedRBtn.style.border = "#FF6961 1px solid";
        });
});

document.getElementById("y").addEventListener("input", (ev) => {
    state.y = ev.target.value;
    clearError();
});

function checkHit(x,y,r){

        if (x>=0 && y>=0){
            return x<=r/2 && y <= r;
        }

        if (x<=0 && y>=0){
            return x*x + y*y <= r*r;
        }

        if (x<=0 && y<=0){
            return false;
        }

        if (x>=0 && y<=0){
            return x - r/2 <= y;
        }

}

function formatTime(timestamp) {
    return new Date(timestamp).toLocaleString('ru-RU', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
    });
}

function appendRow(result) {
    const row = table.insertRow(-1);
    row.insertCell(0).innerText = result.x.toString();
    row.insertCell(1).innerText = result.y.toString();
    row.insertCell(2).innerText = result.r.toString();
    row.insertCell(3).innerText = formatTime(result.time);
    row.insertCell(4).innerText = result.result;
}

function restoreResults() {
    const saved = JSON.parse(localStorage.getItem("results") || "[]");
    saved.forEach(appendRow);
}

//проверка
document.getElementById("data-form").addEventListener("submit", function (ev) {
    ev.preventDefault();

    try {
        validateState(state);
    } catch (e) {
        return;
    }

    const hit = checkHit(state.x, parseFloat(state.y), state.r); 

    const results = {
        x: state.x,
        y: parseFloat(state.y),
        r: state.r,    
        time: Date.now(),
        result: hit.toString(),
    };

    const prevResults = JSON.parse(localStorage.getItem("results") || "[]");
    const updatedResults = [...prevResults, results];
    if (updatedResults.length > 13) {
        updatedResults.splice(0, updatedResults.length - 13);
    }
    localStorage.setItem("results", JSON.stringify(updatedResults));

    appendRow(results);

    if (table.rows.length > 12) {
        table.deleteRow(1);
    }
});

restoreResults();