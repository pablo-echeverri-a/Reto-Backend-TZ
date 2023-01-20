package co.com.sofka.talentzone.retobackend.routers;

import co.com.sofka.talentzone.retobackend.handler.ProductHandler;
import co.com.sofka.talentzone.retobackend.model.ProductDTO;
import co.com.sofka.talentzone.retobackend.usecases.product.CreateProductUseCase;
import co.com.sofka.talentzone.retobackend.usecases.product.DeleteProductUseCase;
import co.com.sofka.talentzone.retobackend.usecases.product.GetAllProductsUseCase;
import co.com.sofka.talentzone.retobackend.usecases.product.UpdateProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> getAll(GetAllProductsUseCase getAllProductsUseCase) {
        return route(GET("/products"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllProductsUseCase.get(), ProductDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> create(CreateProductUseCase createProductUseCase) {
        Function<ProductDTO, Mono<ServerResponse>> executor = productDTO ->  createProductUseCase.apply(productDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/products/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductDTO.class).flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> update(UpdateProductUseCase updateProductUseCase) {
        Function<ProductDTO, Mono<ServerResponse>> executor = productDTO ->  updateProductUseCase.apply(productDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/products/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductDTO.class).flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> delete(DeleteProductUseCase deleteProductUseCase) {
        return route(
                DELETE("/products/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteProductUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> pagination(ProductHandler handler){
        return route(GET("/products/pagination/{page}").and(accept(MediaType.APPLICATION_JSON)), handler::paginable);
    }

    @Bean
    public RouterFunction<ServerResponse> totalPages(ProductHandler handler){
        return route(GET("/products/totalPages").and(accept(MediaType.APPLICATION_JSON)),handler::totalPages);
    }

    @Bean
    public RouterFunction<ServerResponse> countProducts(ProductHandler handler){
        return route(GET("/products/countProducts").and(accept(MediaType.APPLICATION_JSON)),handler::countProducts);
    }
}
