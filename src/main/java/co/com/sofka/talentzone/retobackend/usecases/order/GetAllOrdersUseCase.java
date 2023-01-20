package co.com.sofka.talentzone.retobackend.usecases.order;

import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import co.com.sofka.talentzone.retobackend.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllOrdersUseCase implements Supplier<Flux<OrderDTO>> {

    private final OrderRepository orderRepository;

    private final MapperUtils mapperUtils;

    public GetAllOrdersUseCase(OrderRepository orderRepository, MapperUtils mapperUtils) {
        this.orderRepository = orderRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<OrderDTO> get() {
        return orderRepository.findAll().map(mapperUtils.mapEntityToOrder());
    }
}
