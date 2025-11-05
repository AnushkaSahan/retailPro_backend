package com.retailpro.controller;

import com.retailpro.repository.ProductRepository;
import com.retailpro.repository.SaleRepository;
import com.retailpro.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final ReportService reportService;

    @Autowired
    public DashboardController(SaleRepository saleRepository,
                               ProductRepository productRepository,
                               ReportService reportService) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.reportService = reportService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalProducts", productRepository.count());
        stats.put("totalSales", saleRepository.count());
        stats.put("todaySales", saleRepository.countTodaySales());
        stats.put("lowStockProducts",
                productRepository.findByStockQtyLessThan(10).size());

        Map<String, Object> dailyReport = reportService.getDailySalesReport();
        stats.put("todayRevenue", dailyReport.get("totalRevenue"));

        return ResponseEntity.ok(stats);
    }
}