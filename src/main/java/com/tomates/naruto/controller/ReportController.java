package com.tomates.naruto.controller;

import com.tomates.naruto.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/txt")
    public ResponseEntity<byte[]> generarTXT() {
        byte[] data = reportService.generarReporteTXT();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_shinobi.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(data);
    }

    @GetMapping("/json")
    public ResponseEntity<byte[]> generarJSON() {
        byte[] data = reportService.generarReporteJSON();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_shinobi.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @GetMapping("/xml")
    public ResponseEntity<byte[]> generarXML() {
        byte[] data = reportService.generarReporteXML();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_shinobi.xml")
                .contentType(MediaType.APPLICATION_XML)
                .body(data);
    }
}