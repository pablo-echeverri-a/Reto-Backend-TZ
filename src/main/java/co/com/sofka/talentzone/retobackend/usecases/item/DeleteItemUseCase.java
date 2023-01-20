package co.com.sofka.talentzone.retobackend.usecases.item;

import co.com.sofka.talentzone.retobackend.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteItemUseCase implements Function<String, Mono<Void>> {

    private final ItemRepository itemRepository;

    public DeleteItemUseCase(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Item Id is required");
        return itemRepository.deleteById(id);
    }
}
