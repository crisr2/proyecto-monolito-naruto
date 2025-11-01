package com.tomates.naruto.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tomates.naruto.entity.*;
import com.tomates.naruto.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final AldeaRepository aldeaRepo;
    private final NinjaRepository ninjaRepo;
    private final MisionRepository misionRepo;

    public ReportService(AldeaRepository aldeaRepo, NinjaRepository ninjaRepo, MisionRepository misionRepo) {
        this.aldeaRepo = aldeaRepo;
        this.ninjaRepo = ninjaRepo;
        this.misionRepo = misionRepo;
    }

    // ------------------- TXT ----------------------
    public String generarReporteTXT() {
        StringBuilder sb = new StringBuilder();
        sb.append("======== REPORTE DE NINJAS ========\n\n");
        sb.append("--- ALDEAS ---\n");
        for (Aldea a : aldeaRepo.findAll()) {
            sb.append("\n- ").append(a.getNombre()).append(" (Nación: ").append(a.getNacion()).append(")\n");
            String ninjas = a.getNinjas() == null || a.getNinjas().isEmpty()
                    ? "   • Sin ninjas registrados\n"
                    : a.getNinjas().stream()
                        .map(Ninja::getNombre)
                        .collect(Collectors.joining(", ", "   • Ninjas: ", "\n"));
            sb.append(ninjas);
        }

        sb.append("\n\n--- NINJAS ---\n");
        for (Ninja n : ninjaRepo.findAll()) {
            sb.append("\n- ").append(n.getNombre())
              .append("\n   • Rango: ").append(n.getRango())
              .append("\n   • Aldea: ").append(n.getAldea() != null ? n.getAldea().getNombre() : "Sin Aldea");

            String jutsus = n.getJutsus() == null || n.getJutsus().isEmpty()
                    ? "\n  • Sin jutsus\n"
                    : n.getJutsus().stream()
                        .map(j -> j.getNombre())
                        .collect(Collectors.joining(", ", "\n  • Jutsus: ", "\n"));
            sb.append(jutsus);
        }

        sb.append("\n\n--- MISIONES ---\n");
        for (Mision m : misionRepo.findAll()) {
            sb.append("\n- Misión: ").append(m.getNombre()).append("\n")
              .append("  • Rango: ").append(m.getRango()).append("\n")
              .append("  • Recompensa: ").append(m.getRecompensa()).append("\n");

            String participantes = m.getParticipantes() == null || m.getParticipantes().isEmpty()
                    ? "  • Sin participantes\n"
                    : m.getParticipantes().stream()
                        .map(Ninja::getNombre)
                        .collect(Collectors.joining(", ", "  • Participantes: ", "\n"));
            sb.append(participantes);
        }

        return sb.toString();
    }

    // ---------------------- JSON ---------------------
    public String generarReporteJSON() throws Exception {
            Map<String, Object> reporte = new LinkedHashMap<>();

            reporte.put("aldeas", aldeaRepo.findAll().stream()
                .map(a -> Map.of(
                        "id", a.getId(),
                        "nombre", a.getNombre(),
                        "nacion", a.getNacion(),
                        "ninjas", a.getNinjas().stream().map(Ninja::getNombre).toList()                                        
                )).toList()
            );

            reporte.put("ninjas", ninjaRepo.findAll().stream()
                .map(n -> Map.of(
                        "id", n.getId(),
                        "nombre", n.getNombre(),
                        "rango", n.getRango() != null ? n.getRango().name() : "N/A",
                        "aldea", n.getAldea() != null ? n.getAldea().getNombre() : "Sin Aldea",
                        "jutsus", n.getJutsus().stream().map(j -> j.getNombre()).toList()
                )).toList()
            );

            reporte.put("misiones", misionRepo.findAll().stream()
                .map(m -> Map.of(
                        "id", m.getId(),
                        "nombre", m.getNombre(),
                        "rango", m.getRango(),
                        "recompensa", m.getRecompensa(),
                        "participantes", m.getParticipantes().stream().map(Ninja::getNombre).toList()
                )).toList()
            );

            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(reporte);
        }

    // ---------------------- XML ---------------------
        public String generarReporteXML() throws Exception {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("aldeas", aldeaRepo.findAll().stream()
                .map(a -> Map.of(
                        "id", a.getId(),
                        "nombre", a.getNombre(),
                        "nacion", a.getNacion(),
                        "ninjas", a.getNinjas().stream().map(Ninja::getNombre).toList()
                )).toList()
            );
            data.put("ninjas", ninjaRepo.findAll().stream()
                .map(n -> Map.of(
                        "id", n.getId(),
                        "nombre", n.getNombre(),
                        "rango", n.getRango() != null ? n.getRango().name() : "N/A",
                        "aldea", n.getAldea() != null ? n.getAldea().getNombre() : "Sin Aldea",
                        "jutsus", n.getJutsus().stream().map(j -> j.getNombre()).toList()
                )).toList()
            );
            data.put("misiones", misionRepo.findAll().stream()
                .map(m -> Map.of(
                        "id", m.getId(),
                        "nombre", m.getNombre(),
                        "rango", m.getRango(),
                        "recompensa", m.getRecompensa(),
                        "participantes", m.getParticipantes().stream().map(Ninja::getNombre).toList()
                )).toList()
            );

            return new XmlMapper().writerWithDefaultPrettyPrinter().writeValueAsString(Map.of("reporte", data));
        }
}