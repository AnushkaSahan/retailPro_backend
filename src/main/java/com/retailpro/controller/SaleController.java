package com.retailpro.controller;

import com.retailpro.dto.SaleRequest;
import com.retailpro.model.Sale;
import com.retailpro.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(
            @Valid @RequestBody SaleRequest saleRequest,
            Authentication authentication) {
        String cashierUsername = authentication.getName();
        Sale sale = saleService.createSale(saleRequest, cashierUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Sale>> getTodaySales() {
        return ResponseEntity.ok(saleService.getTodaySales());
    }
}