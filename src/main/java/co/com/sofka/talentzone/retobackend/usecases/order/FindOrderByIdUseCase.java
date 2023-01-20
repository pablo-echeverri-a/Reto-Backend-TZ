package co.com.sofka.talentzone.retobackend.usecases.order;

import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import co.com.sofka.talentzone.retobackend.repositories.ItemRepository;
import co.com.sofka.talentzone.retobackend.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class FindOrderByIdUseCase implements Function<String, Mono<OrderDTO>> {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MapperUtils mapperUtils;

    public FindOrderByIdUseCase(OrderRepository orderRepository, ItemRepository itemRepository, MapperUtils mapperUtils) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<OrderDTO> apply(String id) {
        Objects.requireNonNull(id, "Order Id is required");
        return orderRepository.findById(id)
                .map(mapperUtils.mapEntityToOrder())
                .flatMap(mapOrderAggregate());
    }

    private Function<OrderDTO, Mono<OrderDTO>> mapOrderAggregate() {
        return orderDTO ->
                Mono.just(orderDTO).zipWith(
                        itemRepository.findAllByOrderId(orderDTO.getId())
                                .map(mapperUtils.mapEntityToItem())
                                .collectList(),
                        (order, items) -> {
                            order.setProducts(items);
                            return order;
                        }
                );
    }
}
