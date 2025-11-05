package com.retailpro.repository;

import com.retailpro.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    @Query("SELECT si FROM SaleItem si WHERE si.sale.id = :saleId")
    List<SaleItem> findBySaleId(Long saleId);

    @Query("SELECT si.product.name, SUM(si.quantity) as total " +
            "FROM SaleItem si GROUP BY si.product.name ORDER BY total DESC")
    List<Object[]> findTopSellingProducts();
}