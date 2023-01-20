package co.com.sofka.talentzone.retobackend.repositories;

import co.com.sofka.talentzone.retobackend.document.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {
}
