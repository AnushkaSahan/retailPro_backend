package com.retailpro.service;

import com.retailpro.dto.PurchaseItemDTO;
import com.retailpro.dto.PurchaseRequest;
import com.retailpro.model.*;
import com.retailpro.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PurchaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository,
                           ProductRepository productRepository,
                           SupplierRepository supplierRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public Purchase createPurchase(PurchaseRequest request) {
        logger.info("Creating purchase for supplier ID: {}", request.getSupplierId());

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Purchase purchase = new Purchase(supplier);
        BigDecimal totalCost = BigDecimal.ZERO;

        for (PurchaseItemDTO itemDTO : request.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            PurchaseItem purchaseItem = new PurchaseItem(product,
                    itemDTO.getQuantity(),
                    itemDTO.getCost());
            purchase.addItem(purchaseItem);

            // Update stock
            product.setStockQty(product.getStockQty() + itemDTO.getQuantity());
            productRepository.save(product);

            totalCost = totalCost.add(purchaseItem.getSubtotal());

            logger.info("Added item: {} x{} = ${}", product.getName(),
                    itemDTO.getQuantity(), purchaseItem.getSubtotal());
        }

        purchase.setTotalCost(totalCost);
        Purchase savedPurchase = purchaseRepository.save(purchase);

        logger.info("Purchase created: ID #{}, Total Cost: ${}",
                savedPurchase.getId(), totalCost);

        return savedPurchase;
    }
}