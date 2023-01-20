package co.com.sofka.talentzone.retobackend.routers;

import co.com.sofka.talentzone.retobackend.model.ItemDTO;
import co.com.sofka.talentzone.retobackend.model.OrderDTO;
import co.com.sofka.talentzone.retobackend.usecases.item.AddItemUseCase;
import co.com.sofka.talentzone.retobackend.usecases.item.DeleteItemUseCase;
import co.com.sofka.talentzone.retobackend.usecases.order.*;
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
public class OrderRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllOrders(GetAllOrdersUseCase getAllOrdersUseCase) {
        return route(GET("/orders"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllOrdersUseCase.get(), OrderDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getOrderById(FindOrderByIdUseCase findOrderByIdUseCase) {
        return route(
                GET("/orders/getById/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(findOrderByIdUseCase.apply(
                                        request.pathVariable("id")),
                                OrderDTO.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createOrder(CreateOrderUseCase createOrderUseCase) {
        Function<OrderDTO, Mono<ServerResponse>> executor = orderDTO ->  createOrderUseCase.apply(orderDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/orders/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(OrderDTO.class).flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateOrder(UpdateOrderUseCase updateOrderUseCase) {
        Function<OrderDTO, Mono<ServerResponse>> executor = orderDTO ->  updateOrderUseCase.apply(orderDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/orders/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(OrderDTO.class).flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addItem(AddItemUseCase addItemUseCase) {
        return route(POST("/orders/add-item").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ItemDTO.class)
                        .flatMap(addItemDTO -> addItemUseCase.apply(addItemDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteOrder(DeleteOrderUseCase deleteOrderUseCase) {
        return route(
                DELETE("/orders/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteOrderUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteItem(DeleteItemUseCase deleteItemUseCase) {
        return route(
                DELETE("/orders/deleteItem/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteItemUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }
}
