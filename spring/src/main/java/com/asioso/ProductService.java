package com.asioso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final WebClient webClient;

    @Autowired
    public ProductService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> findAllProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> { // Handle network or other errors
                    return Mono.just(throwable.getMessage());
                });
    }

    public Mono<String> findProduct(int id) {
        return webClient.get()
                .uri("/products/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    return Mono.just(throwable.getMessage());
                });
    }
}
