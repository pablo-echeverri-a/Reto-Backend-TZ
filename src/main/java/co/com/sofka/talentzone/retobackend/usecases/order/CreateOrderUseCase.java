package co.com.sofka.talentzone.retobackend.usecases.order;

import co.com.sofka.talentzone.retobackend.document.Order;
import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import co.com.sofka.talentzone.retobackend.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateOrderUseCase implements SaveOrder{

    private final OrderRepository orderRepository;

    private final MapperUtils mapperUtils;

    public CreateOrderUseCase(OrderRepository orderRepository, MapperUtils mapperUtils) {
        this.orderRepository = orderRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(OrderDTO orderDTO) {
        return orderRepository
                .save(mapperUtils.mapperToOrder().apply(orderDTO))
                .map(Order::getId);
    }
}
