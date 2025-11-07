package com.retailpro.service;

import com.retailpro.model.Product;
import com.retailpro.model.Sale;
import com.retailpro.repository.ProductRepository;
import com.retailpro.repository.SaleItemRepository;
import com.retailpro.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReportService(SaleRepository saleRepository,
                         SaleItemRepository saleItemRepository,
                         ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
    }

    public Map<String, Object> getDailySalesReport() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<Sale> todaySales = saleRepository.findBySaleDateBetween(startOfDay, endOfDay);

        BigDecimal totalRevenue = todaySales.stream()
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalSales = todaySales.size();
        BigDecimal avgSaleValue = totalSales > 0
                ? totalRevenue.divide(new BigDecimal(totalSales), 2, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;

        Map<String, Object> report = new HashMap<>();
        report.put("date", LocalDate.now());
        report.put("totalSales", totalSales);
        report.put("totalRevenue", totalRevenue);
        report.put("avgSaleValue", avgSaleValue);
        report.put("sales", todaySales);

        return report;
    }

    public Map<String, Object> getInventorySummary() {
        List<Product> allProducts = productRepository.findAll();
        List<Product> lowStockProducts = productRepository.findByStockQtyLessThan(10);

        Integer totalProducts = allProducts.size();
        Integer totalStock = allProducts.stream()
                .mapToInt(Product::getStockQty)
                .sum();

        BigDecimal totalValue = allProducts.stream()
                .map(p -> p.getUnitPrice().multiply(new BigDecimal(p.getStockQty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new HashMap<>();
        report.put("totalProducts", totalProducts);
        report.put("totalStock", totalStock);
        report.put("totalValue", totalValue);
        report.put("lowStockProducts", lowStockProducts);

        return report;
    }

    public List<Object[]> getTopSellingProducts() {
        return saleItemRepository.findTopSellingProducts();
    }
}