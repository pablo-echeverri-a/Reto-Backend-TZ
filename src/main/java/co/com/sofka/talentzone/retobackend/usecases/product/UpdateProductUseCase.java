package co.com.sofka.talentzone.retobackend.usecases.product;

import co.com.sofka.talentzone.retobackend.document.Product;
import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.ProductDTO;
import co.com.sofka.talentzone.retobackend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateProductUseCase implements SaveProduct{

    private final ProductRepository productRepository;
    private final MapperUtils mapperUtils;

    public UpdateProductUseCase(ProductRepository productRepository, MapperUtils mapperUtils) {
        this.productRepository = productRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(ProductDTO productDTO) {
        Objects.requireNonNull(productDTO.getId(), "Id of the product is required");
        return productRepository.save(mapperUtils.mapperToProduct().apply(productDTO)).map(Product::getId);
    }
}
