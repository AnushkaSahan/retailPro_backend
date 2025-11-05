package com.retailpro.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PurchaseItemDTO {
    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal cost;

    public PurchaseItemDTO() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
}