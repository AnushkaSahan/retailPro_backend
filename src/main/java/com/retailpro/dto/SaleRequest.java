package com.retailpro.dto;

import com.retailpro.model.PaymentType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class SaleRequest {
    private Long customerId;

    @NotNull
    private PaymentType paymentType;

    @NotEmpty
    private List<SaleItemDTO> items;

    public SaleRequest() {}

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public List<SaleItemDTO> getItems() { return items; }
    public void setItems(List<SaleItemDTO> items) { this.items = items; }
}