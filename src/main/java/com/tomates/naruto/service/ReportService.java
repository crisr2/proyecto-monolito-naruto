package com.tomates.naruto.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tomates.naruto.entity.*;
import com.tomates.naruto.repository.AldeaRepository;
import com.tomates.naruto.repository.MisionRepository;
import com.tomates.naruto.repository.NinjaRepository;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final AldeaRepository aldeaRepository;
    private final NinjaRepository ninjaRepository;
    private final MisionRepository misionRepository;

    public ReportService(AldeaRepository aldeaRepository, NinjaRepository ninjaRepository, MisionRepository misionRepository) {
        this.aldeaRepository = aldeaRepository;
        this.ninjaRepository = ninjaRepository;
        this.misionRepository = misionRepository;
    }

    // ======================= TXT =======================
    public byte[] generarReporteTXT() {
        StringBuilder sb = new StringBuilder();

        sb.append("===== REPORTE DEL MUNDO SHINOBI =====\n\n");

        // ---- ALDEAS ----
        sb.append("ALDEAS:\n");
        for (Aldea aldea : aldeaRepository.findAll()) {
            sb.append("- ").append(aldea.getNombre()).append(" (").append(aldea.getNacion()).append(")\n");
            if (aldea.getNinjas() != null && !aldea.getNinjas().isEmpty()) {
                for (Ninja n : aldea.getNinjas()) {
                    sb.append("   • ").append(n.getNombre()).append(" [").append(n.getRango()).append("]\n");
                }
            } else {
                sb.append("   • Sin ninjas registrados\n");
            }
            sb.append("\n");
        }

        // ---- NINJAS ----
        sb.append("\nNINJAS:\n");
        for (Ninja n : ninjaRepository.findAll()) {
            sb.append("- ").append(n.getNombre()).append(" (").append(n.getRango()).append(")\n");
            sb.append("   Aldea: ").append(n.getAldea() != null ? n.getAldea().getNombre() : "Sin aldea").append("\n");
            sb.append("   Ataque: ").append(n.getAtaque()).append(", Defensa: ").append(n.getDefensa())
                    .append(", Chakra: ").append(n.getChakra()).append("\n");

            if (n.getJutsus() != null && !n.getJutsus().isEmpty()) {
                sb.append("   Jutsus: ");
                sb.append(n.getJutsus().stream().map(Jutsu::getNombre).collect(Collectors.joining(", ")));
            } else {
                sb.append("   Jutsus: Ninguno");
            }
            sb.append("\n\n");
        }

        // ---- MISIONES ----
        sb.append("\nMISIONES:\n");
        for (Mision m : misionRepository.findAll()) {
            sb.append("- ").append(m.getNombre()).append(" [").append(m.getRango()).append("]\n");
            sb.append("   Descripción: ").append(m.getDescripcion()).append("\n");
            sb.append("   Recompensa: ").append(m.getRecompensa()).append("\n");

            if (m.getParticipantes() != null && !m.getParticipantes().isEmpty()) {
                sb.append("   Participantes: ");
                sb.append(m.getParticipantes().stream()
                        .map(Ninja::getNombre)
                        .collect(Collectors.joining(", ")));
            } else {
                sb.append("   Participantes: Ninguno");
            }
            sb.append("\n\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    // ======================= JSON =======================
    public byte[] generarReporteJSON() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("aldeas", aldeaRepository.findAll());
        data.put("ninjas", ninjaRepository.findAll());
        data.put("misiones", misionRepository.findAll());

        try {
            ObjectMapper mapper = new ObjectMapper();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, data);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte JSON", e);
        }
    }

    // ======================= XML =======================
    public byte[] generarReporteXML() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("aldeas", aldeaRepository.findAll());
        data.put("ninjas", ninjaRepository.findAll());
        data.put("misiones", misionRepository.findAll());

        try {
            XmlMapper xmlMapper = new XmlMapper();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(out, data);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte XML", e);
        }
    }
}