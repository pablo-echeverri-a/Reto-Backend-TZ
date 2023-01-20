package co.com.sofka.talentzone.retobackend.usecases.item;

import co.com.sofka.talentzone.retobackend.document.Product;
import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.ItemDTO;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import co.com.sofka.talentzone.retobackend.repositories.ItemRepository;
import co.com.sofka.talentzone.retobackend.repositories.ProductRepository;
import co.com.sofka.talentzone.retobackend.usecases.order.FindOrderByIdUseCase;
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
    private final ProductRepository productRepository;

    public AddItemUseCase(ItemRepository itemRepository, MapperUtils mapperUtils, FindOrderByIdUseCase findOrderByIdUseCase, UpdateProductUseCase updateProductUseCase, ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.mapperUtils = mapperUtils;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.productRepository = productRepository;
    }

    @Override
    public Mono<OrderDTO> apply(ItemDTO itemDTO) {
        Objects.requireNonNull(itemDTO.getOrderId(), "Id of the answer is required");
        return findOrderByIdUseCase.apply(itemDTO.getOrderId()).flatMap(order ->
                itemRepository.save(mapperUtils.mapperToItem().apply(itemDTO))
                        .map(item -> {
                            Product product = productRepository.findById(itemDTO.getIdProduct()).block();
                            if((product.getInInventory() - itemDTO.getQuantity()) >= 0){

                                product.setInInventory(product.getInInventory() - itemDTO.getQuantity());

                                updateProductUseCase.apply(mapperUtils.mapEntityToProduct().apply(product));
                            } else {
                                return null;
                            }

                            order.getProducts().add(itemDTO);
                            return order;
                        })
        );
    }
}