package co.com.sofka.talentzone.retobackend.usecases.order;

import co.com.sofka.talentzone.retobackend.document.Order;
import co.com.sofka.talentzone.retobackend.document.Product;
import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import co.com.sofka.talentzone.retobackend.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateOrderUseCase implements SaveOrder {

    private final OrderRepository orderRepository;

    private final MapperUtils mapperUtils;

    public UpdateOrderUseCase(OrderRepository orderRepository, MapperUtils mapperUtils) {
        this.orderRepository = orderRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(OrderDTO orderDTO) {
        Objects.requireNonNull(orderDTO.getId(), "Order Id is required");
        return orderRepository.save(mapperUtils.mapperToOrder().apply(orderDTO)).map(Order::getId);
    }
}
