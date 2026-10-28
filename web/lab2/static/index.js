"use strict";

const Y_VALUES = new Set([-3, -2, -1, 0, 1, 2, 3, 4, 5]);
const R_VALUES = new Set([1, 1.5, 2, 2.5, 3]);

const X_MIN = -5;
const X_MAX = 3;

const MAX_ROWS = 13;
const STORAGE_KEY = "results";
const X_PATTERN = /^-?\d{1,2}(\.\d+)?$/;

const canvas = document.getElementById("graph");
const ctx = canvas.getContext("2d");
const form = document.getElementById("data-form");
const errorEl = document.getElementById("error");
const table = document.getElementById("result-table");
const submitBtn = form.querySelector('button[type="submit"]');

let lastPoint = null;

function cssVar(name, fallback) {
    const value = getComputedStyle(document.documentElement)
        .getPropertyValue(name)
        .trim();
    return value || fallback;
}

function getCurrentR() {
    const el = form.querySelector('input[name="R"]:checked');
    return el ? Number(el.value) : 1;
}

function drawGraph() {
    if (!canvas.style.width) {
        const w = canvas.clientWidth;
        if (w > 0) {
            canvas.style.width = w + "px";
            canvas.style.height = w + "px";
        }
    }

    const size = Math.round(canvas.clientWidth);
    if (size < 2) return;

    const dpr = window.devicePixelRatio || 1;
    const px = Math.round(size * dpr);
    if (canvas.width !== px || canvas.height !== px) {
        canvas.width = px;
        canvas.height = px;
    }

    ctx.setTransform(dpr, 0, 0, dpr, 0, 0);
    ctx.clearRect(0, 0, size, size);

    const bg     = cssVar("--graph-bg", "#f8fafb");
    const axis   = cssVar("--graph-axis", "#7c8695");
    const label  = cssVar("--graph-label", "#5d6573");
    const area   = cssVar("--graph-area", "rgba(13, 122, 107, 0.20)");
    const accent = cssVar("--graph-stroke", "#0d7a6b");

    const cx = size / 2;
    const cy = size / 2;
    const R = size * 0.26;
    const half = R / 2;
    const pad = size * 0.05;
    const tick = size * 0.013;
    const head = size * 0.016;
    const fontSize = Math.max(11, Math.round(size * 0.034));

    ctx.font = `500 ${fontSize}px "Inter", system-ui, -apple-system, "Segoe UI", Roboto, sans-serif`;
    ctx.textBaseline = "middle";

    const drawLabel = (text, x, y) => {
        ctx.lineWidth = 3;
        ctx.strokeStyle = bg;
        ctx.lineJoin = "round";
        ctx.strokeText(text, x, y);
        ctx.fillStyle = label;
        ctx.fillText(text, x, y);
    };

    ctx.fillStyle = area;
    ctx.strokeStyle = accent;
    ctx.lineWidth = 1;
    ctx.lineJoin = "round";

    ctx.beginPath();
    ctx.moveTo(cx, cy);
    ctx.lineTo(cx, cy + half);
    ctx.lineTo(cx - R, cy);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();

    ctx.beginPath();
    ctx.rect(cx, cy, half, R);
    ctx.fill();
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(cx, cy);
    ctx.arc(cx, cy, R, Math.PI, -Math.PI / 2, false);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();

    ctx.strokeStyle = axis;
    ctx.lineWidth = 1.5;
    ctx.lineCap = "round";

    ctx.beginPath();
    ctx.moveTo(cx, size - pad);
    ctx.lineTo(cx, pad);
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(cx - head, pad + head * 1.8);
    ctx.lineTo(cx, pad);
    ctx.lineTo(cx + head, pad + head * 1.8);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(pad, cy);
    ctx.lineTo(size - pad, cy);
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(size - pad - head * 1.8, cy - head);
    ctx.lineTo(size - pad, cy);
    ctx.lineTo(size - pad - head * 1.8, cy + head);
    ctx.stroke();

    const marks = [
        { k: 0.5, t: "R/2" },
        { k: 1,   t: "R"   },
    ];

    for (const { k, t } of marks) {
        for (const sign of [-1, 1]) {
            const x = cx + sign * k * R;
            ctx.strokeStyle = axis;
            ctx.lineWidth = 1.5;
            ctx.beginPath();
            ctx.moveTo(x, cy - tick);
            ctx.lineTo(x, cy + tick);
            ctx.stroke();

            ctx.textAlign = "center";
            drawLabel((sign < 0 ? "−" : "") + t, x, cy + tick + fontSize);

            const y = cy + sign * k * R;
            ctx.beginPath();
            ctx.moveTo(cx - tick, y);
            ctx.lineTo(cx + tick, y);
            ctx.stroke();

            ctx.textAlign = "right";
            drawLabel((sign > 0 ? "−" : "") + t, cx - tick - 6, y);
        }
    }

    ctx.textAlign = "right";
    drawLabel("0", cx - tick - 4, cy + tick + fontSize);

    if (lastPoint) {
        const r = getCurrentR();
        if (r > 0) {
            const pxX = cx + (lastPoint.x / r) * R;
            const pxY = cy - (lastPoint.y / r) * R;
            const radius = Math.max(3, size * 0.012);

            ctx.beginPath();
            ctx.arc(pxX, pxY, radius, 0, Math.PI * 2);
            ctx.fillStyle = lastPoint.hit ? "#1f9e6e" : "#d9534f";
            ctx.fill();
            ctx.lineWidth = 2;
            ctx.strokeStyle = bg;
            ctx.stroke();
        }
    }
}

function showError(msg) {
    errorEl.textContent = msg;
    errorEl.classList.add("visible");
}

function clearError() {
    errorEl.textContent = "";
    errorEl.classList.remove("visible");
}

function formatTime(timestamp) {
    const d = new Date(timestamp);
    if (Number.isNaN(d.getTime())) return "—";
    return d.toLocaleString("ru-RU", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
    });
}

function isXInRange(xStr) {
    const negative = xStr.startsWith("-");
    const abs = negative ? xStr.slice(1) : xStr;
    const [intPart, fracPart = ""] = abs.split(".");

    const intNum = parseInt(intPart, 10);
    if (negative && intNum > 5) return false;
    if (!negative && intNum > 3) return false;
    if (negative && intNum < 5) return true;
    if (!negative && intNum < 3) return true;
    
    return /^0*$/.test(fracPart);
}

function validate(xRaw, yRaw, rRaw) {
    const xStr = String(xRaw ?? "").trim();
    if (!/^-?\d{1,2}(\.\d+)?$/.test(xStr)) {
        return { ok: false, msg: "X must be a number (e.g. -3.14 or 2.5)" };
    }
    if (!isXInRange(xStr)){
        return { ok: false, msg: `X must be in range [${X_MIN}, ${X_MAX}]` };
    }
    const x = Number(xStr);
    if (!Number.isFinite(x)) {
        return { ok: false, msg: "X must be a finite number" };
    }

    if (yRaw == null || yRaw === "") {
        return { ok: false, msg: "Y is required" };
    }
    const y = Number(yRaw);
    if (!Number.isFinite(y) || !Y_VALUES.has(y)) {
        return { ok: false, msg: `Y must be one of: ${[...Y_VALUES].join(", ")}` };
    }

    if (rRaw == null || rRaw === "") {
        return { ok: false, msg: "R is required" };
    }
    const r = Number(rRaw);
    if (!Number.isFinite(r) || !R_VALUES.has(r)) {
        return { ok: false, msg: `R must be one of: ${[...R_VALUES].join(", ")}` };
    }

    return { ok: true, value: { x, y, r } };
}

function loadResults() {
    try {
        const raw = localStorage.getItem(STORAGE_KEY);
        if (!raw) return [];
        const parsed = JSON.parse(raw);
        return Array.isArray(parsed) ? parsed : [];
    } catch {
        return [];
    }
}

function saveResults(list) {
    try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(list));
    } catch (err) {
        console.warn("Не удалось сохранить результаты:", err);
    }
}

function renderRow(rec) {
    const row = table.insertRow(-1);
    row.insertCell(0).textContent = String(rec.x);
    row.insertCell(1).textContent = String(rec.y);
    row.insertCell(2).textContent = String(rec.r);
    row.insertCell(3).textContent = formatTime(rec.time);
    row.insertCell(4).textContent = rec.execTime;
    row.insertCell(5).textContent =
        typeof rec.result === "boolean" ? String(rec.result) : rec.result;
    return row;
}

function trimTable() {
    while (table.rows.length > MAX_ROWS + 1) {
        table.deleteRow(1);
    }
}

function restoreResults() {
    const saved = loadResults();
    saved.forEach(renderRow);
    trimTable();
}

form.addEventListener("submit", async (ev) => {
    ev.preventDefault();
    clearError();

    const fd = new FormData(form);
    const check = validate(fd.get("x"), fd.get("Y"), fd.get("R"));
    if (!check.ok) {
        showError(check.msg);
        return;
    }

    const { x, y, r } = check.value;

    submitBtn.disabled = true;

    const params = new URLSearchParams({ x, y, r });

    const record = {
        x,
        y,
        r,
        time: Date.now(),
        execTime: "N/A",
        result: "error",
    };

    let gotValidResult = false;

    try {
        const response = await fetch("/fcgi-bin/lab1.jar?" + params.toString(), {
            cache: "no-store",
            headers: { Accept: "application/json" },
        });

        if (response.ok) {
            const data = await response.json();
            record.time = typeof data.now === "number" ? data.now : Date.now();
            record.execTime = typeof data.time === "number" ? `${data.time} ns` : "N/A";
            record.result = Boolean(data.result);
            gotValidResult = true;
        } else if (response.status === 400) {
            const data = await response.json().catch(() => ({}));
            record.time = typeof data.now === "number" ? data.now : Date.now();
            record.execTime = "N/A";
            record.result = `error: ${data.reason ?? "bad request"}`;
        } else {
            record.result = `error: HTTP ${response.status}`;
        }
    } catch (err) {
        console.error("Request failed:", err);
        record.result = "request error";
    } finally {
        submitBtn.disabled = false;
    }

    renderRow(record);
    trimTable();

    if (gotValidResult) {
        lastPoint = { x: record.x, y: record.y, hit: record.result };
        drawGraph();
    }

    const updated = [...loadResults(), record];
    while (updated.length > MAX_ROWS) updated.shift();
    saveResults(updated);
});

form.querySelectorAll('input[name="R"]').forEach((radio) => {
    radio.addEventListener("change", () => {
        lastPoint = null;
        drawGraph();
    });
});

if ("ResizeObserver" in window) {
    new ResizeObserver(drawGraph).observe(canvas);
} else {
    window.addEventListener("resize", drawGraph);
}

const themeQuery = window.matchMedia("(prefers-color-scheme: dark)");
if (themeQuery.addEventListener) {
    themeQuery.addEventListener("change", drawGraph);
} else if (themeQuery.addListener) {
    themeQuery.addListener(drawGraph);
}

restoreResults();
drawGraph();