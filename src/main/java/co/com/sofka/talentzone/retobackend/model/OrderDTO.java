package co.com.sofka.talentzone.retobackend.model;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

public class OrderDTO {

    String id;
    Date date;
    @NotBlank(message = "idType cannot be null")
    String idType;
    @NotBlank(message = "identification cannot be null")
    String identification;
    @NotBlank(message = "clientName cannot be null")
    String clientName;
    List<ItemDTO> products;

    public OrderDTO() {
    }

    public OrderDTO(@NotBlank String idType, @NotBlank String identification, @NotBlank String clientName) {
        this.date = new Date();
        this.idType = idType;
        this.identification = identification;
        this.clientName = clientName;
    }

    public OrderDTO(String id, Date date, String idType, String identification, String clientName, List<ItemDTO> products) {
        this.id = id;
        this.date = date;
        this.idType = idType;
        this.identification = identification;
        this.clientName = clientName;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<ItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ItemDTO> products) {
        this.products = products;
    }
}
