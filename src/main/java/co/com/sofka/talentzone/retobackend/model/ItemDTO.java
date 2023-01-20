package co.com.sofka.talentzone.retobackend.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class ItemDTO {

    @NotBlank(message = "idProduct cannot be null")
    String idProduct;
    @DecimalMin(value = "1", message = "item quantity cannot be less than 1")
    int quantity;

    @NotBlank(message = "orderId cannot be null")
    String orderId;

    public ItemDTO() {
    }

    public ItemDTO(String idProduct, int quantity, String orderId) {
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
