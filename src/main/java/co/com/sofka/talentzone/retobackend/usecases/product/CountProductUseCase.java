package co.com.sofka.talentzone.retobackend.usecases.product;

import co.com.sofka.talentzone.retobackend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
public class CountProductUseCase implements Supplier<Mono<Long>> {

    private final ProductRepository productRepository;

    public CountProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Mono<Long> get() {
        return productRepository.count().map(data->data);
    }
}
