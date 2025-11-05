package com.retailpro.service;

import com.retailpro.dto.ProductDTO;
import com.retailpro.model.Category;
import com.retailpro.model.Product;
import com.retailpro.repository.CategoryRepository;
import com.retailpro.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(ProductDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setBarcode(dto.getBarcode());
        product.setCategory(category);
        product.setUnitPrice(dto.getUnitPrice());
        product.setStockQty(dto.getStockQty());
        product.setImageUrl(dto.getImageUrl());

        logger.info("Creating product: {}", product.getName());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO dto) {
        Product product = getProductById(id);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getBarcode() != null) product.setBarcode(dto.getBarcode());
        if (dto.getUnitPrice() != null) product.setUnitPrice(dto.getUnitPrice());
        if (dto.getStockQty() != null) product.setStockQty(dto.getStockQty());
        if (dto.getImageUrl() != null) product.setImageUrl(dto.getImageUrl());

        logger.info("Updating product: {}", product.getName());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
    }

    public void updateStock(Long productId, Integer quantity) {
        Product product = getProductById(productId);
        product.setStockQty(product.getStockQty() + quantity);
        productRepository.save(product);
    }

    public List<Product> getLowStockProducts(Integer threshold) {
        return productRepository.findByStockQtyLessThan(threshold);
    }
}