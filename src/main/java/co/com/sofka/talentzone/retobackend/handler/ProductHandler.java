package co.com.sofka.talentzone.retobackend.handler;

import co.com.sofka.talentzone.retobackend.model.PageDTO;
import co.com.sofka.talentzone.retobackend.model.ProductDTO;
import co.com.sofka.talentzone.retobackend.usecases.product.CountProductUseCase;
import co.com.sofka.talentzone.retobackend.usecases.product.PaginationUseCase;
import co.com.sofka.talentzone.retobackend.usecases.product.TotalPagesUseCase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {
    private final PaginationUseCase paginationUseCase;
    private final TotalPagesUseCase totalPagesUseCase;
    private final CountProductUseCase countProductUseCase;

    public ProductHandler(PaginationUseCase paginationUseCase, TotalPagesUseCase totalPagesUseCase, CountProductUseCase countProductUseCase) {
        this.paginationUseCase = paginationUseCase;
        this.totalPagesUseCase = totalPagesUseCase;
        this.countProductUseCase = countProductUseCase;
    }

    public Mono<ServerResponse> paginable(ServerRequest request){

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(paginationUseCase.apply(
                        new PageDTO(Integer.parseInt(request.pathVariable("page")))
                ), ProductDTO.class));
    }

    public Mono<ServerResponse> totalPages(ServerRequest request){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(totalPagesUseCase.get(),Long.class));
    }

    public Mono<ServerResponse> countProducts(ServerRequest request){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(countProductUseCase.get(),Long.class));
    }
}
