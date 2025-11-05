package com.retailpro.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PurchaseRequest {
    @NotNull
    private Long supplierId;

    @NotEmpty
    private List<PurchaseItemDTO> items;

    public PurchaseRequest() {}

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public List<PurchaseItemDTO> getItems() { return items; }
    public void setItems(List<PurchaseItemDTO> items) { this.items = items; }
}