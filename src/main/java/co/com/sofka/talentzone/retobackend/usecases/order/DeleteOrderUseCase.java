package co.com.sofka.talentzone.retobackend.usecases.order;

import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.repositories.ItemRepository;
import co.com.sofka.talentzone.retobackend.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteOrderUseCase implements Function<String, Mono<Void>>  {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    public DeleteOrderUseCase(OrderRepository orderRepository, MapperUtils mapperUtils, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Order Id is required");
        return orderRepository.deleteById(id).switchIfEmpty(Mono.defer(() -> itemRepository.deleteByOrderId(id)));
    }
}
