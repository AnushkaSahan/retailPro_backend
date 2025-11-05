package com.retailpro.controller;

import com.retailpro.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/daily-sales")
    public ResponseEntity<Map<String, Object>> getDailySalesReport() {
        return ResponseEntity.ok(reportService.getDailySalesReport());
    }

    @GetMapping("/inventory-summary")
    public ResponseEntity<Map<String, Object>> getInventorySummary() {
        return ResponseEntity.ok(reportService.getInventorySummary());
    }

    @GetMapping("/top-selling")
    public ResponseEntity<List<Object[]>> getTopSellingProducts() {
        return ResponseEntity.ok(reportService.getTopSellingProducts());
    }
}