// app.js - cliente mínimo funcional para aldeas, ninjas, misiones, exportes
const API = "/api";
const messages = document.getElementById("messages");

function showMsg(text, type="info", timeout=4500) {
  const div = document.createElement("div");
  div.className = type === "error" ? "error" : (type === "notice" ? "notice" : "notice");
  div.textContent = text;
  messages.appendChild(div);
  setTimeout(() => div.remove(), timeout);
}

/* ---------------- ALDEAS ---------------- */
document.getElementById("btnLoadAldeas").addEventListener("click", loadAldeas);
document.getElementById("btnOpenCrearAldea").addEventListener("click", () => {
  document.getElementById("crearAldeaBox").style.display = "block";
});
document.getElementById("btnCancelarCrearAldea").addEventListener("click", () => {
  document.getElementById("crearAldeaBox").style.display = "none";
});
document.getElementById("btnCrearAldea").addEventListener("click", async () => {
  const nombre = document.getElementById("aldeaNombre").value.trim();
  const pais = document.getElementById("aldeaPais").value.trim();
  const descripcion = document.getElementById("aldeaDescripcion").value.trim();
  if (!nombre) { showMsg("Nombre de la aldea requerido", "error"); return; }
  try {
    const res = await fetch(`${API}/aldeas`, {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify({nombre,pais,descripcion})
    });
    if (!res.ok) throw new Error("Error al crear aldea");
    showMsg("Aldea creada");
    document.getElementById("crearAldeaBox").style.display = "none";
    document.getElementById("aldeaNombre").value = "";
    document.getElementById("aldeaPais").value = "";
    document.getElementById("aldeaDescripcion").value = "";
    await loadAldeas();
    await loadAldeaSelects();
  } catch(e) { showMsg(e.message, "error"); }
});

async function loadAldeas() {
  const res = await fetch(`${API}/aldeas`);
  if (!res.ok) { showMsg("No se pudo cargar aldeas", "error"); return; }
  const aldeas = await res.json();
  const ul = document.getElementById("listaAldeas");
  ul.innerHTML = "";
  aldeas.forEach(a => {
    const li = document.createElement("li");
    li.className = "card";
    const ninjas = (a.ninjas && a.ninjas.length) ? a.ninjas.map(n=>`${n.nombre} (${n.rango})`).join(", ") : "Sin ninjas";
    li.innerHTML = `<strong>${a.nombre}</strong> <span class="muted">(${a.pais || ""})</span>
      <div class="muted" style="margin-top:6px">${a.descripcion || ""}</div>
      <div style="margin-top:8px;"><b>Ninjas:</b> ${ninjas}</div>`;
    ul.appendChild(li);
  });
}

/* ---------------- NINJAS ---------------- */
document.getElementById("btnAddJutsu").addEventListener("click", addJutsuField);
document.getElementById("btnClearJutsus").addEventListener("click", clearJutsuFields);
document.getElementById("btnCrearNinja").addEventListener("click", crearNinja);
document.getElementById("btnLoadNinjas").addEventListener("click", loadNinjas);

function addJutsuField() {
  const container = document.getElementById("jutsusContainer");
  const idx = container.children.length;
  const div = document.createElement("div");
  div.style.marginTop = "6px";
  div.innerHTML = `
    <input placeholder="Nombre jutsu" data-name />
    <input placeholder="Tipo" data-type />
    <input placeholder="Daño" type="number" data-dano style="width:80px" />
    <input placeholder="Chakra" type="number" data-chakra style="width:80px" />
    <button class="small" data-remove>Quitar</button>
  `;
  container.appendChild(div);
  div.querySelector("[data-remove]").addEventListener("click", () => div.remove());
}

function clearJutsuFields() {
  document.getElementById("jutsusContainer").innerHTML = "";
}

async function crearNinja() {
  const nombre = document.getElementById("ninjaNombre").value.trim();
  const rango = document.getElementById("ninjaRango").value;
  const ataque = parseInt(document.getElementById("ninjaAtaque").value);
  const defensa = parseInt(document.getElementById("ninjaDefensa").value);
  const chakra = parseInt(document.getElementById("ninjaChakra").value);
  const aldeaId = document.getElementById("ninjaAldea").value || null;

  if (!nombre) { showMsg("Nombre requerido", "error"); return; }
  if (isNaN(ataque)||isNaN(defensa)||isNaN(chakra)) { showMsg("Valores numéricos inválidos", "error"); return; }

  // recoger jutsus
  const jutsuNodes = Array.from(document.querySelectorAll("#jutsusContainer > div"));
  const jutsus = jutsuNodes.map(div => {
    const nombreJ = div.querySelector("[data-name]").value.trim();
    const tipo = div.querySelector("[data-type]").value.trim();
    const dano = parseInt(div.querySelector("[data-dano]").value) || 0;
    const chakraJ = parseInt(div.querySelector("[data-chakra]").value) || 0;
    return { nombre: nombreJ, tipo, dano, chakra: chakraJ };
  }).filter(j => j.nombre);

  const payload = {
    nombre, rango, ataque, defensa, chakra,
    jutsus,
    aldea: aldeaId ? { id: Number(aldeaId) } : null
  };

  try {
    const res = await fetch(`${API}/ninjas`, {
      method: "POST",
      headers: {"Content-Type":"application/json"},
      body: JSON.stringify(payload)
    });
    if (!res.ok) {
      const text = await res.text();
      throw new Error(text || "Error al crear ninja");
    }
    showMsg("Ninja creado");
    clearNinjaForm();
    await loadNinjas();
    await loadAldeas();
    await loadAldeaSelects();
    await loadAssignSelects();
  } catch (e) { showMsg(e.message, "error"); }
}

function clearNinjaForm() {
  document.getElementById("ninjaNombre").value = "";
  document.getElementById("ninjaAtaque").value = 50;
  document.getElementById("ninjaDefensa").value = 50;
  document.getElementById("ninjaChakra").value = 50;
  clearJutsuFields();
}

async function loadNinjas() {
  const res = await fetch(`${API}/ninjas`);
  if (!res.ok) { showMsg("No se pudieron cargar ninjas", "error"); return; }
  const ninjas = await res.json();
  const ul = document.getElementById("listaNinjas");
  ul.innerHTML = "";
  ninjas.forEach(n => {
    const li = document.createElement("li");
    li.className = "card";
    const aldeaText = n.aldea ? `${n.aldea.nombre}` : "Sin aldea";
    const jutsus = n.jutsus && n.jutsus.length ? n.jutsus.map(j=>j.nombre).join(", ") : "Sin jutsus";
    li.innerHTML = `<div style="display:flex;justify-content:space-between;align-items:center">
      <div>
        <strong>${n.nombre}</strong> <span class="muted">(${n.rango})</span><br/>
        <span class="muted">A:${n.ataque} D:${n.defensa} C:${n.chakra} — Aldea: ${aldeaText}</span><br/>
        <small><b>Jutsus:</b> ${jutsus}</small>
      </div>
      <div style="text-align:right">
        <div>ID:${n.id}</div>
        <button onclick="eliminarNinja(${n.id})" class="danger small">Eliminar</button>
      </div>
    </div>`;
    ul.appendChild(li);
  });
}

async function eliminarNinja(id) {
  if (!confirm("Confirmar eliminar ninja #" + id)) return;
  const res = await fetch(`${API}/ninjas/${id}`, { method: "DELETE" });
  if (res.ok) {
    showMsg("Ninja eliminado");
    await loadNinjas();
    await loadAldeas();
    await loadAssignSelects();
  } else {
    showMsg("Error eliminando ninja", "error");
  }
}

/* ---------------- MISIONES ---------------- */
document.getElementById("btnCrearMision").addEventListener("click", crearMision);
document.getElementById("btnLoadMisiones").addEventListener("click", loadMisiones);

async function crearMision() {
  const nombre = document.getElementById("misionNombre").value.trim();
  const descripcion = document.getElementById("misionDesc").value.trim();
  const dificultad = document.getElementById("misionDificultad").value;
  const recompensa = parseInt(document.getElementById("misionRecompensa").value);
  if (!nombre) { showMsg("Nombre de misión requerido", "error"); return; }
  if (isNaN(recompensa)) { showMsg("Recompensa inválida", "error"); return; }

  const payload = { nombre, descripcion, dificultad, recompensa };
  const res = await fetch(`${API}/misiones`, {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify(payload)
  });
  if (!res.ok) {
    showMsg("Error creando misión", "error");
    return;
  }
  showMsg("Misión creada");
  document.getElementById("misionNombre").value = "";
  document.getElementById("misionDesc").value = "";
  document.getElementById("misionRecompensa").value = 100;
  await loadMisiones();
  await loadAssignSelects();
}

async function loadMisiones() {
  const res = await fetch(`${API}/misiones`);
  if (!res.ok) { showMsg("No se pudieron cargar misiones", "error"); return; }
  const misiones = await res.json();
  const ul = document.getElementById("listaMisiones");
  ul.innerHTML = "";
  misiones.forEach(m => {
    const participantes = m.participantes && m.participantes.length ? m.participantes.map(p=>`${p.nombre} (${p.rango})`).join(", ") : "Sin participantes";
    const li = document.createElement("li");
    li.className = "card";
    li.innerHTML = `<div><strong>${m.nombre}</strong> <span class="muted">[${m.dificultad}]</span><br/>
      <div class="muted">Recompensa: ${m.recompensa} — Rango mínimo: ${m.rangoMinimo ? m.rangoMinimo : "N/A"}</div>
      <div style="margin-top:8px;"><b>Participantes:</b> ${participantes}</div>
      <div style="margin-top:8px;">
        <label>Asignar ninja:</label>
        <select id="assign_select_${m.id}"><option value="">-- seleccionar ninja --</option></select>
        <button onclick="assignFromList(${m.id})" class="small">Asignar</button>
      </div>
    </div>`;
    ul.appendChild(li);
    // fill select with current ninjas
    fillAssignSelectForMission(m.id);
  });
}

/* ---------------- ASIGNACION ---------------- */
async function loadAssignSelects() {
  // cargar misiones y ninjas into selects
  const [r1, r2] = await Promise.all([fetch(`${API}/misiones`), fetch(`${API}/ninjas`)]);
  if (!r1.ok || !r2.ok) return;
  const misiones = await r1.json();
  const ninjas = await r2.json();

  const selM = document.getElementById("selectAsignarMision");
  const selN = document.getElementById("selectAsignarNinja");
  selM.innerHTML = ""; selN.innerHTML = "";
  misiones.forEach(m => selM.appendChild(opt(m.id, `${m.id} - ${m.nombre} [${m.dificultad}]`)));
  ninjas.forEach(n => selN.appendChild(opt(n.id, `${n.id} - ${n.nombre} (${n.rango})`)));
}

async function fillAssignSelectForMission(misionId) {
  const res = await fetch(`${API}/ninjas`);
  if (!res.ok) return;
  const ninjas = await res.json();
  const sel = document.getElementById(`assign_select_${misionId}`);
  if (!sel) return;
  sel.innerHTML = `<option value="">-- seleccionar ninja --</option>`;
  ninjas.forEach(n => sel.appendChild(opt(n.id, `${n.id} - ${n.nombre} (${n.rango})`)));
}

function opt(val, text) { const o = document.createElement("option"); o.value = val; o.textContent = text; return o; }

document.getElementById("btnAsignar").addEventListener("click", async () => {
  const mId = document.getElementById("selectAsignarMision").value;
  const nId = document.getElementById("selectAsignarNinja").value;
  if (!mId || !nId) { showMsg("Selecciona misión y ninja", "error"); return; }
  await asignar(mId, nId, true);
});

async function assignFromList(misionId) {
  const sel = document.getElementById(`assign_select_${misionId}`);
  if (!sel) return;
  const ninjaId = sel.value;
  if (!ninjaId) { showMsg("Selecciona un ninja", "error"); return; }
  await asignar(misionId, ninjaId, false);
}

async function asignar(misionId, ninjaId, mostrarPanel) {
  try {
    const res = await fetch(`${API}/misiones/${misionId}/asignar/${ninjaId}`, { method: "POST" });
    const data = await res.json();
    // backend retorna { success: boolean, message: "..." }
    if (data.success) {
      showMsg(data.message || "Asignado correctamente", "notice");
    } else {
      showMsg(data.message || "No se pudo asignar (reglas)", "error");
    }
    document.getElementById("assignResult").innerText = data.message || "";
    await loadMisiones();
    await loadNinjas();
    await loadAldeas();
    await loadAssignSelects();
  } catch (e) {
    showMsg("Error al asignar: " + e.message, "error");
  }
}

/* ---------------- EXPORTES ---------------- */
async function exportar(formato) {
  try {
    const res = await fetch(`${API}/export/${formato}`);
    if (!res.ok) { showMsg("Error generando reporte", "error"); return; }
    const blob = await res.blob();
    const filename = `reporte.${formato}`;
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url; a.download = filename; document.body.appendChild(a); a.click(); a.remove();
    URL.revokeObjectURL(url);
    showMsg("Reporte descargado: " + filename, "notice");
  } catch (e) {
    showMsg("Error exportando: " + e.message, "error");
  }
}

/* ---------------- HELPERS / INICIALIZACION ---------------- */
async function loadAldeaSelects() {
  // load aldeas into ninjaAldea select
  const res = await fetch(`${API}/aldeas`);
  if (!res.ok) return;
  const aldeas = await res.json();
  const sel = document.getElementById("ninjaAldea");
  sel.innerHTML = `<option value="">-- Selecciona aldea --</option>`;
  aldeas.forEach(a => sel.appendChild(opt(a.id, a.nombre)));
}

async function initialLoad() {
  await loadAldeas();
  await loadAldeaSelects();
  await loadNinjas();
  await loadMisiones();
  await loadAssignSelects();
}

initialLoad();