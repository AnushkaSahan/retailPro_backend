package com.retailpro.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_items")
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    public PurchaseItem() {}

    public PurchaseItem(Product product, Integer quantity, BigDecimal cost) {
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
        this.subtotal = cost.multiply(new BigDecimal(quantity));
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Purchase getPurchase() { return purchase; }
    public void setPurchase(Purchase purchase) { this.purchase = purchase; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}