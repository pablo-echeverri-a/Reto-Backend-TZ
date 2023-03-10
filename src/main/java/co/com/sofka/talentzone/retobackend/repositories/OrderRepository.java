package co.com.sofka.talentzone.retobackend.repositories;

import co.com.sofka.talentzone.retobackend.document.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, String> {
    Flux<Order> findByIdentification(String identification);
}
