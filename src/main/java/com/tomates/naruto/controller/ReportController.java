package com.tomates.naruto.controller;
import com.tomates.naruto.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    private String fechaActual() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
    }

    @GetMapping("/txt")
    public ResponseEntity<String> exportarTxt() {
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=" + "reporte_" + fechaActual() + ".txt")
            .body(reportService.generarReporteTXT());
    }

    @GetMapping("/json")
    public ResponseEntity<String> exportarJson() throws Exception {
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=" + "reporte_" + fechaActual() + ".json")
            .body(reportService.generarReporteJSON());
    }

    @GetMapping("/xml")
    public ResponseEntity<String> exportarXml() throws Exception {
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=" + "reporte_" + fechaActual() + ".xml")
            .body(reportService.generarReporteXML());
    }
}