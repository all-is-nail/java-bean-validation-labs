package org.example.model;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class OrderItem {

    @NotBlank
    private String sku;

    @Positive
    private int quantity;

    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal price;

    public OrderItem() {}

    public OrderItem(String sku, int quantity, BigDecimal price) {
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
