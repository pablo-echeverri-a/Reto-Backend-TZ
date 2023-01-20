package co.com.sofka.talentzone.retobackend.usecases.product;

import co.com.sofka.talentzone.retobackend.mapper.MapperUtils;
import co.com.sofka.talentzone.retobackend.model.PageDTO;
import co.com.sofka.talentzone.retobackend.model.ProductDTO;
import co.com.sofka.talentzone.retobackend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
public class PaginationUseCase implements Function<PageDTO, Flux<ProductDTO>> {

    private final ProductRepository productRepository;
    private final MapperUtils mapperUtils;

    public PaginationUseCase(ProductRepository productRepository, MapperUtils mapperUtils) {
        this.productRepository = productRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<ProductDTO> apply(PageDTO pageDTO) {
        return productRepository.findAll()
                .skip(PageDTO.PAGESIZE * pageDTO.getPageNumber())
                .take(PageDTO.PAGESIZE)
                .map(mapperUtils.mapEntityToProduct());
    }
}
