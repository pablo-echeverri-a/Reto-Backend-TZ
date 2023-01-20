package co.com.sofka.talentzone.retobackend.usecases.item;

import co.com.sofka.talentzone.retobackend.model.ItemDTO;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface SaveItem {
    Mono<OrderDTO> apply(@Valid ItemDTO itemDTO);
}
