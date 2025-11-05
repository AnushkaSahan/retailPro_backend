package com.retailpro.repository;

import com.retailpro.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Sale s WHERE s.cashier.id = :cashierId")
    List<Sale> findByCashierId(Long cashierId);

    @Query("SELECT COUNT(s) FROM Sale s WHERE CAST(s.saleDate AS date) = CURRENT_DATE")
    Long countTodaySales();
}