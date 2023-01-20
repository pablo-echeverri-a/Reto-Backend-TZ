package co.com.sofka.talentzone.retobackend.usecases.order;

import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface SaveOrder {
    Mono<String> apply(@Valid OrderDTO orderDTO);
}
