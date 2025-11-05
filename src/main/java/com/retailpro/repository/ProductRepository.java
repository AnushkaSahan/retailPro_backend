package com.retailpro.repository;

import com.retailpro.model.Product;
import com.retailpro.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByBarcode(String barcode);
    List<Product> findByStatus(Status status);
    List<Product> findByStockQtyLessThan(Integer minStock);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(Long categoryId);
}