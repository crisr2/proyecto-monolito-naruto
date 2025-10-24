package com.tomates.naruto.controller;
import com.tomates.naruto.service.ReporteService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/export")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/json")
    public ResponseEntity<Resource> exportarJson() throws IOException {
        Resource archivo = reporteService.generarReporte("json");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archivo.getFilename())
                .body(archivo);
    }

    @GetMapping("/xml")
    public ResponseEntity<Resource> exportarXml() throws IOException {
        Resource archivo = reporteService.generarReporte("xml");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archivo.getFilename())
                .body(archivo);
    }

    @GetMapping("/txt")
    public ResponseEntity<Resource> exportarTxt() throws IOException {
        Resource archivo = reporteService.generarReporte("txt");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archivo.getFilename())
                .body(archivo);
    }
}
