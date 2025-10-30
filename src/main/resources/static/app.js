// RUTAS base API
const API_BASE = "/api";
const ALDEAS_API = `${API_BASE}/aldeas`;
const NINJAS_API = `${API_BASE}/ninjas`;
const JUTSUS_API = `${API_BASE}/jutsus`;
const MISIONES_API = `${API_BASE}/misiones`;
const REPORTES_API = `${API_BASE}/reportes`;

/* ---------- HELPERS ---------- */
function el(selector) { return document.querySelector(selector); }
function elAll(selector) { return document.querySelectorAll(selector); }
function createElem(tag, inner = '') { const e = document.createElement(tag); e.innerHTML = inner; return e; }
async function fetchJson(url, opts) {
  const r = await fetch(url, opts);
  if (!r.ok) throw new Error(`HTTP ${r.status} ${r.statusText}`);
  return r.json();
}
function showList(container, itemsHtml) {
  const ul = el(container);
  ul.innerHTML = "";
  itemsHtml.forEach(h => {
    const li = createElem("li", h);
    li.className = "item";
    ul.appendChild(li);
  });
}
function downloadBlob(blob, filename, type) {
  const url = URL.createObjectURL(new Blob([blob], { type }));
  const a = document.createElement("a");
  a.href = url; a.download = filename; document.body.appendChild(a);
  a.click(); a.remove(); URL.revokeObjectURL(url);
}

/* ---------- ALDEAS ---------- */
async function crearAldea() {
  const nombre = el("#aldeaNombre").value.trim();
  const nacion = el("#aldeaNacion").value.trim();
  const descripcion = el("#aldeaDescripcion").value.trim();
  if (!nombre || !nacion) { alert("Nombre y Nación son obligatorios."); return; }
  await fetchJson(ALDEAS_API, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nombre, nacion, descripcion })
  });
  alert("Aldea creada.");
  el("#aldeaNombre").value = el("#aldeaNacion").value = el("#aldeaDescripcion").value = "";
  await listarAldeas(); await refrescarSelects();
}

async function listarAldeas() {
  try {
    const aldeas = await fetchJson(ALDEAS_API);
    const items = aldeas.map(a => {
      const ninjas = a.ninjas && a.ninjas.length ? a.ninjas.map(n => `${n.id}:${n.nombre}`).join(", ") : "Sin ninjas";
      return `<div>
                <div><strong>ID:</strong> ${a.id} — <strong>${a.nombre}</strong> (${a.nacion})</div>
                <div class="muted">${a.descripcion || ""}</div>
                <div><small><strong>Ninjas:</strong> ${ninjas}</small></div>
              </div>`;
    });
    showList("#listaAldeas", items);
  } catch (e) {
    alert("Error listando aldeas: " + e.message);
  }
}

/* ---------- JUTSUS ---------- */
async function crearJutsu() {
  const nombre = el("#jutsuNombre").value.trim();
  const tipo = el("#jutsuTipo").value.trim();
  const danio = parseInt(el("#jutsudanio").value);
  const chakra = parseInt(el("#jutsuChakra").value);
  if (!nombre || !tipo || isNaN(danio) || isNaN(chakra)) { alert("Completa todos los campos de jutsu."); return; }
  await fetchJson(JUTSUS_API, {
    method: "POST",
    headers: { "Content-Type":"application/json" },
    body: JSON.stringify({ nombre, tipo, descripcion: "", chakra: chakra, danio })
  });
  alert("Jutsu creado.");
  el("#jutsuNombre").value = el("#jutsuTipo").value = el("#jutsudanio").value = el("#jutsuChakra").value = "";
  await listarJutsus(); await refrescarSelects();
}

async function listarJutsus() {
  try {
    const jutsus = await fetchJson(JUTSUS_API);
    const items = jutsus.map(j => {
      return `<div>
                <div><strong>ID:</strong> ${j.id} — <strong>${j.nombre}</strong> (${j.tipo})</div>
                <div class="muted">Daño: ${j.danio ?? j.chakra ?? "N/A"} · Chakra: ${j.chakra ?? "N/A"}</div>
              </div>`;
    });
    showList("#listaJutsus", items);
  } catch (e) {
    alert("Error listando jutsus: " + e.message);
  }
}

/* ---------- NINJAS ---------- */
async function crearNinja() {
  const nombre = el("#ninjaNombre").value.trim();
  const rango = el("#ninjaRango").value;
  const ataque = parseInt(el("#ninjaAtaque").value);
  const defensa = parseInt(el("#ninjaDefensa").value);
  const chakra = parseInt(el("#ninjaChakra").value);
  const aldeaId = el("#ninjaAldea").value || null;
  if (!nombre || isNaN(ataque) || isNaN(defensa) || isNaN(chakra)) { alert("Completa todos los campos del ninja."); return; }

  // crear ninja primero (sin jutsus)
  const body = { nombre, rango, ataque, defensa, chakra, aldea: aldeaId ? { id: parseInt(aldeaId) } : null };
  const created = await fetchJson(NINJAS_API, {
    method: "POST", headers: { "Content-Type":"application/json" }, body: JSON.stringify(body)
  });

  // si se seleccionaron jutsus, asignarlos (llamadas separadas)
  const selectedOptions = Array.from(el("#ninjaJutsus").selectedOptions).map(o => o.value);
  for (const jId of selectedOptions) {
    try {
      const res = await fetch(`${NINJAS_API}/${created.id}/asignar-jutsu/${jId}`, { method: "POST" });
      if (!res.ok) {
        const txt = await res.text();
        console.warn("No se pudo asignar jutsu:", txt);
      }
    } catch (e) { console.warn(e); }
  }

  alert("Ninja creado y jutsus asignados (si se seleccionaron).");
  el("#ninjaNombre").value = ""; el("#ninjaAtaque").value = ""; el("#ninjaDefensa").value = ""; el("#ninjaChakra").value = "";
  await listarNinjas(); await listarNinjasFull();
}

async function listarNinjas() {
  try {
    const ninjas = await fetchJson(NINJAS_API);
    const items = ninjas.map(n => {
      const aldea = n.aldea ? `${n.aldea.id}:${n.aldea.nombre}` : "Sin aldea";
      const jutsus = n.jutsus && n.jutsus.length ? n.jutsus.map(j => j.nombre).join(", ") : "Sin jutsus";
      return `<div>
                <div><strong>ID:</strong> ${n.id} — <strong>${n.nombre}</strong> (${n.rango})</div>
                <div class="muted">Aldea: ${aldea} · Chakra:${n.chakra} · Jutsus: ${jutsus}</div>
              </div>`;
    });
    showList("#listaNinjas", items);
  } catch (e) {
    alert("Error listando ninjas: " + e.message);
  }
}

async function listarNinjasFull() {
  // idéntico a listarNinjas, pero deja ver más info (se usa al crear por ejemplo)
  await listarNinjas();
}

/* ---------- MISIONES ---------- */
async function crearMision() {
  const nombre = el("#misionNombre").value.trim();
  const descripcion = el("#misionDesc").value.trim();
  const dificultad = el("#misionDificultad").value;
  const recompensa = parseInt(el("#misionRecompensa").value);
  if (!nombre || !descripcion || isNaN(recompensa)) { alert("Completa todos los campos de la misión."); return; }

  // rangoMinimo lo determina el servidor según la dificultad
  await fetchJson(MISIONES_API, {
    method:"POST", headers: { "Content-Type":"application/json" },
    body: JSON.stringify({ nombre, descripcion, dificultad, recompensa })
  });
  alert("Misión creada.");
  el("#misionNombre").value = ""; el("#misionDesc").value = ""; el("#misionRecompensa").value = "";
  await listarMisiones();
}

async function asignarNinjaAMision() {
  const mId = el("#asignarMisionId").value;
  const nId = el("#asignarNinjaId").value;
  if (!mId || !nId) { alert("Ingresa ambos IDs."); return; }
  const res = await fetch(`${MISIONES_API}/${mId}/asignar/${nId}`, { method: "POST" });
  if (!res.ok) {
    // si el backend devuelve JSON con message, intentar parsear
    try {
      const body = await res.json();
      alert("Resultado: " + (body.message || JSON.stringify(body)));
    } catch (e) {
      const t = await res.text();
      alert("Error: " + t);
    }
    return;
  }
  const data = await res.json(); // Map {success,msg}
  alert(data.message || (data.success ? "Asignado" : "No asignado"));
  await listarMisiones();
}

/* listar misiones */
async function listarMisiones() {
  try {
    const misiones = await fetchJson(MISIONES_API);
    const items = misiones.map(m => {
      const participantes = m.participantes && m.participantes.length ? m.participantes.map(p => `${p.id}:${p.nombre}`).join(", ") : "Sin participantes";
      return `<div>
                <div><strong>ID:</strong> ${m.id} — <strong>${m.nombre}</strong> [${m.dificultad}]</div>
                <div class="muted">Recompensa: ${m.recompensa} · RangoMin: ${m.rangoMinimo ? m.rangoMinimo : "N/A"}</div>
                <div>${m.descripcion}</div>
                <div><small><b>Participantes:</b> ${participantes}</small></div>
              </div>`;
    });
    showList("#listaMisiones", items);
  } catch (e) {
    alert("Error listando misiones: " + e.message);
  }
}

/* ---------- REPORTES ---------- */
async function exportarTxt() {
  const res = await fetch(`${REPORTES_API}/txt`);
  if (!res.ok) { alert("Error exportando TXT"); return; }
  const blob = await res.blob();
  downloadBlob(blob, "reporte_shinobi.txt", "text/plain");
}
async function exportarJson() {
  const res = await fetch(`${REPORTES_API}/json`);
  if (!res.ok) { alert("Error exportando JSON"); return; }
  const blob = await res.blob();
  downloadBlob(blob, "reporte_shinobi.json", "application/json");
}
async function exportarXml() {
  const res = await fetch(`${REPORTES_API}/xml`);
  if (!res.ok) { alert("Error exportando XML"); return; }
  const blob = await res.blob();
  downloadBlob(blob, "reporte_shinobi.xml", "application/xml");
}

/* ---------- UTIL: refrescar selects (aldeas, jutsus) ---------- */
async function refrescarSelects() {
  // aldeas
  try {
    const aldeas = await fetchJson(ALDEAS_API);
    const sel = el("#ninjaAldea");
    sel.innerHTML = '<option value="">-- seleccionar --</option>';
    aldeas.forEach(a => {
      const opt = document.createElement("option");
      opt.value = a.id; opt.textContent = `${a.nombre} (${a.nacion})`;
      sel.appendChild(opt);
    });
  } catch(e) { console.warn("No se cargaron aldeas:", e); }

  // jutsus
  try {
    const jutsus = await fetchJson(JUTSUS_API);
    const selJ = el("#ninjaJutsus");
    selJ.innerHTML = "";
    jutsus.forEach(j => {
      const opt = document.createElement("option");
      opt.value = j.id; opt.textContent = `${j.nombre} — ${j.tipo}`;
      selJ.appendChild(opt);
    });
  } catch(e) { console.warn("No se cargaron jutsus:", e); }
}

/* ---------- INIT: montar listeners ---------- */
function bind() {
  el("#btnCrearAldea").addEventListener("click", crearAldea);
  el("#btnListarAldeas").addEventListener("click", listarAldeas);

  el("#btnCrearJutsu").addEventListener("click", crearJutsu);
  el("#btnListarJutsus").addEventListener("click", listarJutsus);

  el("#btnCrearNinja").addEventListener("click", crearNinja);
  el("#btnListarNinjas").addEventListener("click", listarNinjas);
  el("#btnListarNinjasFull").addEventListener("click", listarNinjasFull);

  el("#btnCrearMision").addEventListener("click", crearMision);
  el("#btnListarMisiones").addEventListener("click", listarMisiones);
  el("#btnAsignarNinjaMision").addEventListener("click", asignarNinjaAMision);

  el("#btnExportTxt").addEventListener("click", exportarTxt);
  el("#btnExportJson").addEventListener("click", exportarJson);
  el("#btnExportXml").addEventListener("click", exportarXml);

  el("#btnRefrescarSelects").addEventListener("click", refrescarSelects);
}

/* ---------- START ---------- */
window.addEventListener("DOMContentLoaded", async () => {
  bind();
  await refrescarSelects();
  // pre-carga opcional de listas
  await listarAldeas();
  await listarJutsus();
  await listarNinjas();
  await listarMisiones();
});
