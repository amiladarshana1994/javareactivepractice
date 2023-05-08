package org.reactive;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class FluxAndMonoInit {
    public static void main(String[] args) {
        // 0 to n element
        Flux<Integer> numbersStream = Flux.just(1,2,3,4);

        // 0 to 1 element
        Mono<Integer> numberStream = Mono.just(1);

        // both are implementation of same Publisher interface
        Publisher<Integer> numberStreamPub = Mono.just(1);
        Publisher<Integer> numbersStreamPub = Flux.just(1,2,3,4);

        // subscribe to stream
        List<Integer> elements = new ArrayList<>();

        numbersStream
                .log()
                .subscribe(elements::add);
        elements.forEach(System.out::println);
        numbersStream
                .log()
                .map(i -> i*2)
                .subscribe(elements::add);
        elements.forEach(System.out::println);
    }
}
