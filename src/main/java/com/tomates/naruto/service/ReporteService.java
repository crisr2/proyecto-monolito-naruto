package com.tomates.naruto.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tomates.naruto.entity.Aldea;
import com.tomates.naruto.entity.Mision;
import com.tomates.naruto.repository.AldeaRepository;
import com.tomates.naruto.repository.MisionRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;

@Service
public class ReporteService {

    private final AldeaRepository aldeaRepository;
    private final MisionRepository misionRepository;

    public ReporteService(AldeaRepository aldeaRepository, MisionRepository misionRepository) {
        this.aldeaRepository = aldeaRepository;
        this.misionRepository = misionRepository;
    }

    public Resource generarReporte(String formato) throws IOException {
        List<Aldea> aldeas = aldeaRepository.findAll();
        List<Mision> misiones = misionRepository.findAll();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File exportDir = new File("export");
        if (!exportDir.exists()) exportDir.mkdirs();

        File archivo = new File(exportDir, "Reporte_" + timestamp + "." + formato.toLowerCase());

        switch (formato.toLowerCase()) {
            case "json" -> generarJson(archivo, aldeas, misiones);
            case "xml" -> generarXml(archivo, aldeas, misiones);
            case "txt" -> generarTxt(archivo, aldeas, misiones);
            default -> throw new IllegalArgumentException("Formato no soportado: " + formato);
        }

        return new FileSystemResource(archivo);
    }

    private void generarJson(File archivo, List<Aldea> aldeas, List<Mision> misiones) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(archivo, new ReporteData(aldeas, misiones));
    }

    private void generarXml(File archivo, List<Aldea> aldeas, List<Mision> misiones) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(archivo, new ReporteData(aldeas, misiones));
    }

    private void generarTxt(File archivo, List<Aldea> aldeas, List<Mision> misiones) throws IOException {
        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write("===== ALDEAS Y NINJAS =====\n");
            for (Aldea a : aldeas) {
                fw.write("Aldea: " + a.getNombre() + " (" + a.getNacion() + ")\n");
                if (a.getNinjas() != null) {
                    for (var n : a.getNinjas()) {
                        fw.write("  - " + n.getNombre() + " [" + n.getRango() + "]\n");
                    }
                }
                fw.write("\n");
            }

            fw.write("===== MISIONES =====\n");
            for (Mision m : misiones) {
                fw.write(m.getNombre() + " [" + m.getDificultad() + "] - Recompensa: " + m.getRecompensa() + "\n");
                if (m.getParticipantes() != null) {
                    for (var p : m.getParticipantes()) {
                        fw.write("  • " + p.getNombre() + " (" + p.getRango() + ")\n");
                    }
                }
                fw.write("\n");
            }
        }
    }

    // Clase auxiliar para serialización JSON/XML
    record ReporteData(List<Aldea> aldeas, List<Mision> misiones) {}
}
