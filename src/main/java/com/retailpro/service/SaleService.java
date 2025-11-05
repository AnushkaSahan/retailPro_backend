package com.retailpro.service;

import com.retailpro.dto.SaleItemDTO;
import com.retailpro.dto.SaleRequest;
import com.retailpro.model.*;
import com.retailpro.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SaleService {

    private static final Logger logger = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository,
                       UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    public Sale createSale(SaleRequest request, String cashierUsername) {
        logger.info("Creating sale for cashier: {}", cashierUsername);

        // Get cashier
        User cashier = userRepository.findByUsername(cashierUsername)
                .orElseThrow(() -> new RuntimeException("Cashier not found"));

        // Get customer if provided
        Customer customer = null;
        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        }

        // Generate invoice number
        String invoiceNumber = generateInvoiceNumber();

        // Create sale
        Sale sale = new Sale(invoiceNumber, cashier, customer,
                request.getPaymentType());

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Add items
        for (SaleItemDTO itemDTO : request.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check stock
            if (product.getStockQty() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " +
                        product.getName());
            }

            // Create sale item
            BigDecimal price = itemDTO.getPrice() != null ?
                    itemDTO.getPrice() : product.getUnitPrice();
            SaleItem saleItem = new SaleItem(product, itemDTO.getQuantity(), price);
            sale.addItem(saleItem);

            // Update stock
            product.setStockQty(product.getStockQty() - itemDTO.getQuantity());
            productRepository.save(product);

            totalAmount = totalAmount.add(saleItem.getSubtotal());

            logger.info("Added item: {} x{} = ${}", product.getName(),
                    itemDTO.getQuantity(), saleItem.getSubtotal());
        }

        sale.setTotalAmount(totalAmount);
        Sale savedSale = saleRepository.save(sale);

        logger.info("Sale created: Invoice #{}, Total: ${}",
                invoiceNumber, totalAmount);

        return savedSale;
    }

    private String generateInvoiceNumber() {
        Long count = saleRepository.count();
        return String.format("INV-%06d", count + 1);
    }

    public List<Sale> getSalesByDateRange(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findBySaleDateBetween(start, end);
    }

    public List<Sale> getTodaySales() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0)
                .withMinute(0)
                .withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23)
                .withMinute(59)
                .withSecond(59);
        return getSalesByDateRange(startOfDay, endOfDay);
    }
}