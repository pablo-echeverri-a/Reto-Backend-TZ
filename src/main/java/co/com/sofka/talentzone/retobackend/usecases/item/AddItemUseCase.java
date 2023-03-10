package co.com.sofka.talentzone.retobackend.usecases.item;

import co.com.sofka.talentzone.retobackend.document.Item;
import co.com.sofka.talentzone.retobackend.document.Order;
import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.ItemDTO;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import co.com.sofka.talentzone.retobackend.model.ProductDTO;
import co.com.sofka.talentzone.retobackend.repositories.ItemRepository;
import co.com.sofka.talentzone.retobackend.repositories.ProductRepository;
import co.com.sofka.talentzone.retobackend.usecases.order.FindOrderByIdUseCase;
import co.com.sofka.talentzone.retobackend.usecases.order.UpdateOrderUseCase;
import co.com.sofka.talentzone.retobackend.usecases.product.UpdateProductUseCase;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class AddItemUseCase implements SaveItem {

    private final ItemRepository itemRepository;
    private final MapperUtils mapperUtils;
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final ProductRepository productRepository;

    public AddItemUseCase(ItemRepository itemRepository, MapperUtils mapperUtils, FindOrderByIdUseCase findOrderByIdUseCase, UpdateProductUseCase updateProductUseCase, UpdateOrderUseCase updateOrderUseCase, ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.mapperUtils = mapperUtils;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.productRepository = productRepository;
    }

    @Override
    public Mono<OrderDTO> apply(ItemDTO itemDTO) {
        Objects.requireNonNull(itemDTO.getOrderId(), "Id of the answer is required");
        return findOrderByIdUseCase.apply(itemDTO.getOrderId()).flatMap(order ->
                productRepository.findById(itemDTO.getIdProduct()).map(selecteProduct -> {
                    if ((selecteProduct.getInInventory() - itemDTO.getQuantity()) >= 0) {

                        itemRepository.save(mapperUtils.mapperToItem().apply(itemDTO)).subscribe();

                        order.getProducts().add(itemDTO);

                        updateOrderUseCase.apply(order).subscribe();

                        selecteProduct.setInInventory(selecteProduct.getInInventory() - itemDTO.getQuantity());

                        ProductDTO updatedProduct = mapperUtils.mapEntityToProduct().apply(selecteProduct);

                        productRepository.save(selecteProduct).subscribe();

                        return order;

                    } else {
                        return null;
                    }

                })
        );
    }
}
