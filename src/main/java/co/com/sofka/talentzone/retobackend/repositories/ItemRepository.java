package co.com.sofka.talentzone.retobackend.repositories;

import co.com.sofka.talentzone.retobackend.document.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<Item, String> {
    Flux<Item> findAllByOrderId(String orderId);

    Mono<Void> deleteByOrderId(String orderId);
}
