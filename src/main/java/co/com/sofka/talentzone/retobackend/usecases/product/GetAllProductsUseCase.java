package co.com.sofka.talentzone.retobackend.usecases.product;

import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.ProductDTO;
import co.com.sofka.talentzone.retobackend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllProductsUseCase implements Supplier<Flux<ProductDTO>> {

    private final ProductRepository productRepository;
    private final MapperUtils mapperUtils;

    public GetAllProductsUseCase(ProductRepository productRepository, MapperUtils mapperUtils) {
        this.productRepository = productRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<ProductDTO> get() {
        return productRepository.findAll().map(mapperUtils.mapEntityToProduct());
    }
}
