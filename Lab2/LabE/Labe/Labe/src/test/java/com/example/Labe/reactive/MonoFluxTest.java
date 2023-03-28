package com.example.Labe.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoFluxTest {

    @Test
    public void givenMonoPublisher_whenSubscribeThenReturnSingleValue(){
        Mono<String> helloMono = Mono.just("Hola");
        StepVerifier.create(helloMono)
                .expectNext("Hola").expectComplete().verify();
    }

    @Test
    public void givenFluxPublisher_whenSubscribeThenReturnMultipleValue(){
        Flux<String> helloFlux = Flux.just("Hola", "Spring Reactive");
        StepVerifier.create(helloFlux)
                .expectNext("Hola")
                .expectNext("Spring Reactive")
                .expectComplete().verify();
    }

    @Test
    public void givenFluxPublisher_whenSubscribeThenReturnMultipleValuesWithError(){
        Flux<String> stringFlux = Flux.just("Hola", "Spring Reactive", "Error")
                .map(str -> {
                    if (str.equals("Error"))
                        throw new RuntimeException("Throwing Error");
                    return str; });

        StepVerifier
                .create(stringFlux)
                .expectNext("Hola")
                .expectNext("Spring Reactive")
                .expectError()
                .verify();
    }
}
