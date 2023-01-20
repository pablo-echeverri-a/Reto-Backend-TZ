package co.com.sofka.talentzone.retobackend.usecases.product;

import co.com.sofka.talentzone.retobackend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
public class TotalPagesUseCase implements Supplier<Mono<Long>> {

    private final ProductRepository productRepository;

    public TotalPagesUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Mono<Long> get() {
        return productRepository.count().map(data->{
            return (long)Math.ceil(((float)data/10)) ;
        });
    }
}
