package com.retailpro.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SaleItemDTO {
    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    private BigDecimal price;

    public SaleItemDTO() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}