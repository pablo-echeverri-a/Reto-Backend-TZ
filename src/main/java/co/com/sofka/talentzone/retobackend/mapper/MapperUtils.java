package co.com.sofka.talentzone.retobackend.mapper;

import co.com.sofka.talentzone.retobackend.document.Item;
import co.com.sofka.talentzone.retobackend.document.Order;
import co.com.sofka.talentzone.retobackend.document.Product;
import co.com.sofka.talentzone.retobackend.model.ItemDTO;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import co.com.sofka.talentzone.retobackend.model.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {

    public Function<ProductDTO, Product> mapperToProduct() {
        return updateProduct -> {
            var product = new Product();
            product.setId(updateProduct.getId());
            product.setName(updateProduct.getName());
            product.setInInventory(updateProduct.getInInventory());
            product.setEnabled(updateProduct.isEnabled());
            product.setMin(updateProduct.getMin());
            product.setMax(updateProduct.getMax());
            return product;
        };
    }

    public Function<OrderDTO, Order> mapperToOrder() {
        return updateOrder -> {
            var order = new Order();
            order.setIdType(updateOrder.getIdType());
            order.setIdentification(updateOrder.getIdentification());
            order.setClientName(updateOrder.getClientName());
            return order;
        };
    }

    public Function<ItemDTO, Item> mapperToItem() {
        return updateItem -> {
            var item = new Item();
            item.setIdProduct(updateItem.getIdProduct());
            item.setQuantity(updateItem.getQuantity());
            item.setOrderId(updateItem.getOrderId());
            return item;
        };
    }

    public Function<Product, ProductDTO> mapEntityToProduct() {
        return entity -> new ProductDTO(
                entity.getId(),
                entity.getName(),
                entity.getInInventory(),
                entity.isEnabled(),
                entity.getMin(),
                entity.getMax()
        );
    }

    public Function<Order, OrderDTO> mapEntityToOrder() {
        return entity -> new OrderDTO(
                entity.getId(),
                entity.getIdType(),
                entity.getIdentification(),
                entity.getClientName()
        );
    }

    public Function<Item, ItemDTO> mapEntityToItem() {
        return entity -> new ItemDTO(
                entity.getIdProduct(),
                entity.getQuantity(),
                entity.getOrderId()
        );
    }
}
